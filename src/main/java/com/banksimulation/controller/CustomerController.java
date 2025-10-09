package com.banksimulation.controller;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.model.Customer;
import com.banksimulation.service.CustomerService;
import com.banksimulation.service.serviceimpl.CustomerServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/customer")
public class CustomerController {
    private CustomerService service;
    private Connection connection;
    public CustomerController(){
        try {
            this.connection=JdbcConnectionSetup.getConnection();
        }catch (Exception e){}
        this.service=new CustomerServiceImpl();
    }
    public CustomerController(Connection connection){
        this.connection=connection;
        this.service=new CustomerServiceImpl(connection);
    }

    public static final Logger logger= LogManager.getLogger(CustomerController.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(@Valid Customer customer){
        try{
        if(service.add(customer)==1) return Response.status(Response.Status.CREATED).entity("Successfully added the new Customer").build();
        else return Response.status(Response.Status.CONFLICT).entity("Could not update").build();
        }catch (Exception e){ return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Cannot add new details").build();}
    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("customerId") int customerId) {
        try {
           Customer customer=service.get(customerId);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(customer).build();
        } catch (Exception e) {
            logger.error("Database error fetching customer", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PUT
    @Path("/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("customerId")int customerId, Customer customer){
        try{
            customer.setCustomerId(customerId);
            int res=service.update(customer);
            if(res==0) return Response.status(Response.Status.BAD_REQUEST).build();
            else return Response.ok().entity("Successfully updated the given customer details").build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DELETE
    @Path("/{customerId}")
    public Response deleteCustomer(@PathParam("customerId") int customerId){
        try{
            int res=service.delete(customerId);
            if(res==0) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.status(Response.Status.NO_CONTENT).entity("successfully deleted the customer").build();
        }catch (Exception e){return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();}
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers(){
        try(Connection connection=JdbcConnectionSetup.getConnection()){
            List<Customer> customers=service.getAllCustomers();
            if(customers.isEmpty()) return Response.status(Response.Status.NO_CONTENT).build();
            return Response.ok(customers).build();
        }catch (SQLException | ClassNotFoundException e){return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();}
    }

}
