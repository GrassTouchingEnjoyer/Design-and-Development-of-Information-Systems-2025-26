package model.system_data.loader;

import static utils.utils.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.actors.Administrator;
import model.actors.BankStaff;
import model.actors.BusinessClient;
import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.Bill;
import model.capitalTransfer.Transaction;

import model.system_data.persistence.FileDataStore;


public class FileReloader {
	

    public void save(FileDataStore s_data) throws IOException {

        savePersonalClient(s_data.getPersonalClients());
        
        saveBusinessClient(s_data.getBusinessClients());
        
        saveAdministrator(s_data.getAdmins());

        saveBankStaff(s_data.getBankStaff());
        
        savePersonalAccount(s_data.getPersonalAccounts());
        
        saveBill(s_data.getBills());
        
        saveTransaction(s_data.getTransactions());
		}      
    
    //⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿ STORE STUFF ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿  
    
    public void savePersonalClient(List<PersonalClient> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/personal_cli.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " personal clients");
        }
    }

    public void saveBusinessClient(List<BusinessClient> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/business_cli.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " business clients");
        }
    }

    public void saveAdministrator(List<Administrator> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/administrator.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " administrators");
        }
    }

    public void saveBankStaff(List<BankStaff> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/bank_staff.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " bank staff");
        }
    }

    public void savePersonalAccount(List<PersonalAccount> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/personal_account.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " personal accounts");
        }
    }

    public void saveBill(List<Bill> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/bill.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " bills");
        }
    }

    public void saveTransaction(List<Transaction> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys_stored_data/transaction.dat"))) {
            oos.writeObject(list);
            
            print("Saved " + list.size() + " transactions");
        }
    }
    
    //⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿ LOAD STUFF ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
    
    @SuppressWarnings("unchecked")
	public List<PersonalClient> loadPersonalClient() throws IOException, ClassNotFoundException {	
        File file = new File("sys_stored_data/personal_cli.dat");    
        if (!file.exists()) {    	
        	      	
        	print("No saved personal clients found.");   
            
            return new ArrayList<>();         
        }
        System.out.println(file.getAbsolutePath());
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<PersonalClient> list = (List<PersonalClient>) ois.readObject();
            
            print("Loaded " + list.size() + " personal clients");
            
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<BusinessClient> loadBusinessClient() throws IOException, ClassNotFoundException {
        File file = new File("sys_stored_data/business_cli.dat");
        if (!file.exists()) {
            
        	print("No saved business clients found.");
            
        	return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<BusinessClient> list = (List<BusinessClient>) ois.readObject();
            
            print("Loaded " + list.size() + " business clients");
            
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Administrator> loadAdministrator() throws IOException, ClassNotFoundException {
        File file = new File("sys_stored_data/administrator.dat");
        if (!file.exists()) {
            
        	print("No saved administrators found.");
            
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Administrator> list = (List<Administrator>) ois.readObject();
            
            print("Loaded " + list.size() + " administrators");
            
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<BankStaff> loadBankStaff() throws IOException, ClassNotFoundException {
        File file = new File("sys_stored_data/bank_staff.dat");
        if (!file.exists()) {
            
        	print("No saved bank staff found.");
            
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<BankStaff> list = (List<BankStaff>) ois.readObject();
            
            print("Loaded " + list.size() + " bank staff members");
            
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<PersonalAccount> loadPersonalAccount() throws IOException, ClassNotFoundException {
        File file = new File("sys_stored_data/personal_account.dat");
        if (!file.exists()) {
            
        	print("No saved personal accounts found.");
            
        	return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<PersonalAccount> list = (List<PersonalAccount>) ois.readObject();
            
            print("Loaded " + list.size() + " personal accounts");
            
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Bill> loadBill() throws IOException, ClassNotFoundException {
        File file = new File("sys_stored_data/bill.dat");
        if (!file.exists()) {
            
        	print("No saved bills found.");
            
        	return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Bill> list = (List<Bill>) ois.readObject();
            
            print("Loaded " + list.size() + " bills");
            
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> loadTransaction() throws IOException, ClassNotFoundException {
        File file = new File("sys_stored_data/transaction.dat");
        if (!file.exists()) {
            
        	print("No saved transactions found.");
            
        	return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Transaction> list = (List<Transaction>) ois.readObject();
            
            print("Loaded " + list.size() + " transactions");
            
            return list;
        }
    }   
    
    
    
}
