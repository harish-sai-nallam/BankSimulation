package com.banksimulation.repository;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    private static final Logger logger= LogManager.getLogger(CustomerRepo.class);
    public Connection connection;
    public CustomerRepo(){
        try{connection=JdbcConnectionSetup.getConnection();}
        catch (Exception e){logger.info("Error while establishing a JDBC connection");}
    }
    public CustomerRepo(Connection connection){this.connection=connection;}

    private void ensureConnection(){
        try{
            if(connection==null || connection.isClosed()){
                connection=JdbcConnectionSetup.getConnection();
            }
        } catch (ClassNotFoundException| SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public int addCustomer(Customer customer) {
        ensureConnection();
        if(customer.getCustomerId()<0) return 0;
        String insertSql = "INSERT INTO customer(customerId,customerName,aadharNumber,permanentAddress,"
                + "state,country,city,email,phoneNumber,maritalStatus,dob,age,createdOn,modifiedOn,gender,fatherName,motherName) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getAadharNumber());
            ps.setString(4, customer.getPermanentAddress());
            ps.setString(5, customer.getState());
            ps.setString(6, customer.getCountry());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getEmail());
            ps.setString(9, customer.getPhoneNumber());
            ps.setString(10, customer.getMaritalStatus());
            ps.setDate(11, Date.valueOf(customer.getDob()));
            ps.setInt(12, customer.getAge());
            ps.setDate(13, Date.valueOf(customer.getCreatedOn()));
            if (customer.getModifiedOn() != null) {
                ps.setDate(14, Date.valueOf(customer.getModifiedOn()));
            } else {
                ps.setNull(14, Types.DATE);
            }
            ps.setString(15, customer.getGender());
            ps.setString(16, customer.getFatherName());
            ps.setString(17, customer.getMotherName());
            if (ps.executeUpdate() == 0) {
                logger.error("Error while adding the new details");
                return 0;
            }else{
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting customer with ID " + customer.getCustomerId(), e);
        }
    }

    public Customer getCustomer(int customerId){
        String get="SELECT * FROM customer WHERE customerId=?";
        try(PreparedStatement statement= connection.prepareStatement(get)){
            statement.setInt(1,customerId);
            try(ResultSet rs = statement.executeQuery()){
                if(!rs.next()) {
                    return null;
                }
                Customer customer=new Customer();
                customer.setCustomerId(customerId);
                customer.setCustomerName(rs.getString("customerName"));
                customer.setAadharNumber(rs.getString("aadharNumber"));
                customer.setPermanentAddress(rs.getString("permanentAddress"));
                customer.setState(rs.getString("state"));
                customer.setCountry(rs.getString("country"));
                customer.setCity(rs.getString("city"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setMaritalStatus(rs.getString("maritalStatus"));
                customer.setDob(rs.getDate("dob").toLocalDate());
                customer.setAge(rs.getInt("age"));
                customer.setCreatedOn(rs.getDate("createdOn").toLocalDate());

                Date modDate = rs.getDate("modifiedOn");
                customer.setModifiedOn(modDate != null ? modDate.toLocalDate() : null);

                customer.setGender(rs.getString("gender"));
                customer.setFatherName(rs.getString("fatherName"));
                customer.setMotherName(rs.getString("motherName"));
                return customer;
            }
        }catch (SQLException e){
            logger.error("Cannot get the Customer details due to internal Issues", e);
            return null;
        }
    }
    public List<Customer> getAllCustomers(){
        String getAll="SELECT * FROM customer";
        List<Customer> customers=new ArrayList<>();
        try(PreparedStatement statement= connection.prepareStatement(getAll);
            ResultSet rs = statement.executeQuery()){

            while(rs.next()){
                Customer customer=new Customer();
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setAadharNumber(rs.getString("aadharNumber"));
                customer.setPermanentAddress(rs.getString("permanentAddress"));
                customer.setState(rs.getString("state"));
                customer.setCountry(rs.getString("country"));
                customer.setCity(rs.getString("city"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setMaritalStatus(rs.getString("maritalStatus"));
                customer.setDob(rs.getDate("dob").toLocalDate());
                customer.setAge(rs.getInt("age"));
                customer.setCreatedOn(rs.getDate("createdOn").toLocalDate());

                Date modDate = rs.getDate("modifiedOn");
                customer.setModifiedOn(modDate != null ? modDate.toLocalDate() : null);

                customer.setGender(rs.getString("gender"));
                customer.setFatherName(rs.getString("fatherName"));
                customer.setMotherName(rs.getString("motherName"));
                customers.add(customer);
            }
        }catch (SQLException e){
            logger.error("Database error while fetching customers", e);
        }
        return customers;
    }

    public int updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET "
                + " customerName = ?, aadharNumber = ?, permanentAddress = ?, state = ?, "
                + "country = ?, city = ?, email = ?, phoneNumber = ?, maritalStatus = ?, dob = ?, age = ?, "
                + "createdOn = ?, modifiedOn = ?, gender = ?, fatherName = ?, motherName = ? "
                + "WHERE customerId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAadharNumber());
            ps.setString(3, customer.getPermanentAddress());
            ps.setString(4, customer.getState());
            ps.setString(5, customer.getCountry());
            ps.setString(6, customer.getCity());
            ps.setString(7, customer.getEmail());
            ps.setString(8, customer.getPhoneNumber());
            ps.setString(9, customer.getMaritalStatus());
            ps.setDate(10, Date.valueOf(customer.getDob()));
            ps.setInt(11, customer.getAge());
            ps.setDate(12, Date.valueOf(customer.getCreatedOn()));
            if (customer.getModifiedOn() != null) {
                ps.setDate(13, Date.valueOf(customer.getModifiedOn()));
            } else {
                ps.setNull(13, Types.DATE);
            }
            ps.setString(14, customer.getGender());
            ps.setString(15, customer.getFatherName());
            ps.setString(16, customer.getMotherName());
            ps.setInt(17, customer.getCustomerId());
            if(ps.executeUpdate()>0){
                return 1;
            }
            else return 0;
        } catch (SQLException e) {
            logger.error("Error updating customer with ID " + customer.getCustomerId(), e);
            return 0;
        }
    }
    public int deleteCustomer(int customerId){
        String delete="delete from customer where customerId=?";
        try(PreparedStatement statement= connection.prepareStatement(delete)){
            statement.setInt(1,customerId);
            return statement.executeUpdate();
        }catch (SQLException e){logger.error("Cannot delete the Customer may be Customer not found");
        return 0;}
    }



    public static void createTableCustomer(){
        try{
            Connection connection1=JdbcConnectionSetup.getConnection();
            String createTable= "CREATE TABLE IF NOT EXISTS customer ( customerId INT PRIMARY KEY AUTO_INCREMENT, "
                    + "customerName VARCHAR(50), "
                    + "aadharNumber CHAR(12), "
                    + "permanentAddress VARCHAR(255), "
                    + "state VARCHAR(50), "
                    + "country VARCHAR(50), "
                    + "city VARCHAR(50), "
                    + "email VARCHAR(100), "
                    + "phoneNumber CHAR(10), "
                    + "maritalStatus VARCHAR(10), "
                    + "dob DATE, "
                    + "age INT, "
                    + "createdOn DATE, "
                    + "modifiedOn DATE, gender CHAR(1),  fatherName VARCHAR(50), motherName VARCHAR(50) )";
            PreparedStatement statement=connection1.prepareStatement(createTable);
            statement.executeUpdate();
        }catch (Exception e){
            logger.error("Customer table creation was not successful! ");
        }
    }
    public String getCustomerEmail(String accountNumber){
        ensureConnection();
        AccountRepo accountRepo=new AccountRepo(connection);
        int customerId;
        String email = "";
        try {
            customerId=accountRepo.getCustomerIdUsingNumber(accountNumber);
            System.out.println("Obtained the Customer Id:"+customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query="select email from customer where customerId=? ";
        try {
            if (connection.isClosed() || connection == null) {
                connection = JdbcConnectionSetup.getConnection();
            }
        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }
        try(
                PreparedStatement statement=connection.prepareStatement(query)){
            statement.setInt(1,customerId);
            ResultSet rs=statement.executeQuery();
            if(rs.next()) {
                email = rs.getString("email");
            }else{
                System.out.println("No customer email was found with id "+customerId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (email == null || email.trim().isEmpty()) {
            System.out.println("No valid email for customer " + customerId);
            return null;
        }
        return email;
    }

}
