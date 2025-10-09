package com.banksimulation.controller;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.exception.AccountNotFoundException;
import com.banksimulation.model.Account;
import com.banksimulation.repository.AccountRepo;
import com.banksimulation.service.AccountService;
import com.banksimulation.service.serviceimpl.AccountServiceImpl;
import com.google.protobuf.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.List;



@Path("/account")
public class AccountController {
    private static final Logger log= LogManager.getLogger(AccountController.class);
   private AccountService service;
   private Connection connection;

   public AccountController(){
       try{
           connection= JdbcConnectionSetup.getConnection();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       this.service=new AccountServiceImpl(connection);
   }
    public AccountController(Connection connection){
        try{
            this.connection= connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.service=new AccountServiceImpl(connection);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAccount(@Valid Account account) {
        try {
            log.info("Initialised the Post request");
            boolean success = service.add(account);
            if (success) {
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Account cannot be created").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            log.error("Error creating account", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Server error creating account").build();
        }
    }
    @GET
    @Path("/{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("accountNumber") String accountNumber) {
       AccountRepo repo=new AccountRepo();
        try {
            int accountId=repo.getAccountIdUsingNumber(accountNumber);
            Account account = service.get(accountNumber);
            return Response.ok(account).build();
        } catch (AccountNotFoundException | NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error fetching account", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{accountNumber}")
    public Response deleteAccount(@PathParam("accountNumber") String accountNumber) {
        log.info("Initiated the DELETE HTTP request for Account ID: " + accountNumber);
        try {
            service.delete(accountNumber);
            return Response.noContent().build();
        }catch (AccountNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting account", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Server error deleting account").build();
        }
    }

    @PUT
    @Path("/{accountNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@PathParam("accountNumber") String accountNumber, @Valid Account account) {
       log.info("PUT update accountId={}", accountNumber);
        try {
            service.update(accountNumber, account);
            return Response.ok().entity("Account updated successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating account", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Server error updating account").build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        try {

            List<Account> accounts = service.getAccounts();
            if (accounts != null && !accounts.isEmpty()) return Response.ok(accounts).build();
            else {
                return Response.status(Response.Status.NOT_FOUND).entity("No Accounts data were found")
                        .build();
            }
        } catch (Exception e) {
            log.error("Could not find the Acounts");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Issue!!!").build();
        }
    }


    @Path("/{accountNumber}/transactions")
    public TransactionController getTransactionController(@PathParam("accountNumber") String accountNumber) {
        return new TransactionController(accountNumber);
    }
}