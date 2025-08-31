package com.banksimulation;

import com.banksimulation.repository.AccountRepo;
import com.banksimulation.repository.CustomerRepo;
import com.banksimulation.repository.TranscationRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App {


	private static final Logger log=LogManager.getLogger(App.class);
	public static void main(String[] args) {
		AccountRepo.createAccountTable();
		CustomerRepo.createTableCustomer();
		TranscationRepo.createTransactionTable();
	}
}

