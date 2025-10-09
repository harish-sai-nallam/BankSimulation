package com.banksimulation;

import com.banksimulation.config.TomcatServer;
import com.banksimulation.repository.AccountRepo;
import com.banksimulation.repository.CustomerRepo;
import com.banksimulation.repository.TransactionRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	private static final Logger log = LogManager.getLogger(App.class);

	public static void main(String[] args) throws Exception {
//		CustomerRepo.createTableCustomer();
//		AccountRepo.createAccountTable();
		//TransactionRepo.createTransactionsTable();
		TomcatServer.startServer();





		



	}
}