package com.banksimulation;

import com.banksimulation.repository.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.banksimulation.entity.User;


public class App {


	private static final Logger log=LogManager.getLogger(App.class);
	public static void main(String[] args) {
		UserRepo userRepo=new UserRepo();
		User user1=new User("harish","harish@gmail.com",20,"Male","Hyderabad");
		boolean status=userRepo.insertUser(user1);
		if (status) log.info("Successfully entered the new user details");
		else log.info("Could not enter the details");
	}
}

