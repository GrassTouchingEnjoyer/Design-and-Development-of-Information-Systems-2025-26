package model.system_data.persistence;

import java.io.File;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static utils.utils.print;

import model.actors.Administrator;
import model.actors.BankStaff;
import model.actors.BusinessClient;
import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.Bill;
import model.capitalTransfer.Transaction;

import model.system_data.loader.FileLoader;
import model.system_data.loader.FileReloader;

public class FileDataStore {

    private static FileDataStore instance;

    private List<PersonalClient> personalClients = new ArrayList<>();
    private List<BusinessClient> businessClients = new ArrayList<>();
    private List<Administrator> admins = new ArrayList<>();
    private List<BankStaff> staff = new ArrayList<>();
    private List<PersonalAccount> personalAccounts = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    

    private FileDataStore() {
       
    	if (isSerializedDataPresent()) {loadFromSerialization();} 
        
        else {loadFromOriginalFiles();}
    }
    

    public static FileDataStore getInstance() {
    	
        if (instance == null) {instance = new FileDataStore();}
        return instance; //singleton
    }

    private boolean isSerializedDataPresent() {
        
    	String[] keyFiles = 
    		{"sys_stored_data/personal_cli.dat",
            "sys_stored_data/business_cli.dat"};

        for (String path : keyFiles) {
        	
            File file = new File(path);
            
            if (file.exists() && file.length() > 0) {return true;}
        
        }return false;
    }

    private void loadFromSerialization() {
    	
        FileReloader reloader = new FileReloader();
        
        try {
            personalClients = reloader.loadPersonalClient();
            businessClients = reloader.loadBusinessClient();
            admins 			= reloader.loadAdministrator();
            staff		    = reloader.loadBankStaff();
            bills			= reloader.loadBill();
            transactions 	= reloader.loadTransaction();
            
            print("Data loaded");
            
        } catch (Exception e) {
        	
        	print("Failed to load data");
        	
            loadFromOriginalFiles();
        }
    }

    private void loadFromOriginalFiles() {
    	
        FileLoader loader = new FileLoader();
        loader.load(personalClients, businessClients, admins, personalAccounts, bills);
        
        print("Data initialized from original source files.");
    }
    
    public void saveFile() {
    	FileReloader reloader = new FileReloader();
    	
    	try {
    		reloader.save(FileDataStore.getInstance());
    	}catch (Exception e) {
        	
        	print("Failed to save data :" + e.getCause() +e.toString());
        	
        }
    }

    public List<PersonalClient>  getPersonalClients() { return personalClients; }
    public List<BusinessClient>  getBusinessClients() { return businessClients; }
    public List<Administrator>   getAdmins() { return admins; }
    public List<BankStaff> 		 getBankStaff() { return staff; }
    public List<PersonalAccount> getPersonalAccounts() { return personalAccounts; }
    public List<Bill> 			 getBills() { return bills; }
    public List<Transaction> 	 getTransactions() { return transactions; }
}