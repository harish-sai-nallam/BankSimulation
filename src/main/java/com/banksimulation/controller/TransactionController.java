package com.banksimulation.controller;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.model.Account;
import com.banksimulation.model.Transaction;
import com.banksimulation.repository.AccountRepo;
import com.banksimulation.repository.TransactionRepo;
import com.banksimulation.service.TransactionService;
import com.banksimulation.service.serviceimpl.TransactionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;


@Path("/transactions")
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);
    private TransactionService service;
    private String accountNumber;
    private Connection connection;

    public TransactionController(String accountNumber){
       service=new TransactionServiceImpl();
       this.accountNumber=accountNumber;
       try { this.connection= JdbcConnectionSetup.getConnection();}
       catch (Exception e){}
    }
    public TransactionController(String accountNumber,Connection connection){
        this.accountNumber=accountNumber;
        this.connection=connection;
        service=new TransactionServiceImpl(connection);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(@Valid Transaction transaction) {
            logger.info("Starting transfer from " + transaction.getSenderAccountNumber());

            try {
                service.addTransaction(transaction);

                logger.info("Transfer completed successfully");

                return Response.status(Response.Status.CREATED)
                        .entity("{  \" message\": \"Transfer successful\" }")
                        .build();

            } catch (IllegalArgumentException e) {
                logger.error("Transfer validation error: " + e.getMessage(), e);
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{ error : " + e.getMessage() + " }")
                        .build();
            } catch (Exception e) {
                logger.error("Unexpected error during transfer", e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"An unexpected error occurred\"}")
                        .build();
            }
    }

    @GET
    @Produces("text/csv")
    public Response getTransactions() {
        try {
            String csv = service.getTransactions(accountNumber);
            if (csv.isEmpty() || csv==null || csv=="") {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No transactions found")
                        .build();
            }
            logger.info("Initiated downloading the Transactions done by the "+accountNumber);
            String filename = "transactions_" + accountNumber + ".csv";

            return Response.ok(csv)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .build();


        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generating CSV")
                    .build();
        }
    }



}
