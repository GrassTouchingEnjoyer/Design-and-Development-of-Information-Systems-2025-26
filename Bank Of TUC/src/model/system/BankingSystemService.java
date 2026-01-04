package model.system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.actors.BankAccount;
import model.actors.BankAccount.AccountStatus;
import model.actors.BankStaff;
import model.actors.BusinessAccount;
import model.actors.BusinessClient;
import model.actors.Client;
import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.actors.User;
import model.builder.StandingOrderTransferSEPABuilder;
import model.builder.StandingOrderTransferSWIFTBuilder;
import model.capitalTransfer.Bill;
import model.capitalTransfer.StandingOrder;
import model.capitalTransfer.StandingOrderBill;
import model.capitalTransfer.StandingOrderTransferInternal;
import model.capitalTransfer.StandingOrderTransferSEPA;
import model.capitalTransfer.StandingOrderTransferSWIFT;
import model.command.*;
import model.factory.ClientFactory;
import model.factory.InterestPolicyFactory;
import model.system_data.dao.*;
import model.system_data.persistence.FileDataStore;
import model.transfer.InternalTransferExecution;
import model.transfer.SEPATransferExecution;
import model.transfer.SWIFTTransferExecution;
import model.transfer.TransferExecution;

public class BankingSystemService {
	private static final Random RANDOM = new Random();
    private static BankingSystemService instance;

    // DAOs
    private final PersonalClientDAO personalClientDAO;
    private final BusinessClientDAO businessClientDAO;
    private final PersonalAccountDAO personalAccountDAO;
    private final BankStaffDAO bankStaffDAO;
    private final AdministratorDAO administratorDAO;
    private final TransactionDAO transactionDAO;
    private final BillDAO billDAO;
    private final StandingOrderDAO standingOrderDAO;
    private final CommandInvoker invoker;

    private BankingSystemService() {
        this.personalClientDAO = new FilePersonalClientDAO();
        this.businessClientDAO = new FileBusinessClientDAO();
        this.personalAccountDAO = new FilePersonalAccountDAO();
        this.bankStaffDAO = new FileBankStaffDAO();
        this.administratorDAO = new FileAdministratorDAO();
        this.transactionDAO = new FileTransactionDAO();
        this.billDAO = new FileBillDAO();
        this.standingOrderDAO = new FileStandingOrderDAO();
        this.invoker = new CommandInvoker();
    }

    public static BankingSystemService getInstance() {
        if (instance == null) {
            instance = new BankingSystemService(); //singleton
        }
        return instance;
    }
    
    public void persist() {
        FileDataStore.getInstance().saveFile();
    }
    
    // ----------Create Users and Accounts----------
    
    public CommandResult createPersonalClient(String username, String password, String fullName) {
    	if(personalClientDAO.findByFullName(fullName)==null 
    			&& personalClientDAO.findByUsername(username)==null) {
    		PersonalClient client = new PersonalClient(username, password, fullName
            		, generateRandomAfm(), generateRandomPhone());
            personalClientDAO.save(client);
            createPersonalAccount(client);
            return new CommandResult(true,"Succefully created Client :" + fullName);
    	}else return new CommandResult(false,"Error : Username or Full Name already exist");
        
    }
    
    public void createPersonalAccount(PersonalClient owner) {
    	PersonalAccount account = new PersonalAccount(generateUniqueIban(),owner,0);
    	personalAccountDAO.add(account);
    	persist();
    }
    
    public CommandResult createBusinessClient(String username, String password, String fullName) {
    	if(businessClientDAO.findByFullName(fullName)==null 
    			&& businessClientDAO.findByUsername(username)==null) {
        BusinessClient client = new BusinessClient(username, password, fullName, 
        		generateRandomAfm(), generateRandomPhone());
        client.setAccount(createBusinessAccount(client));
        businessClientDAO.add(client);
        persist();
        return new CommandResult(true,"Succefully created BusinessClient :" + fullName);
    	}else return new CommandResult(false,"Error : Username or Full Name already exist");
    }
    
    public BusinessAccount createBusinessAccount(BusinessClient owner) {
    	persist();
    	return new BusinessAccount(generateUniqueIban(),owner,0);
    }
    
    public CommandResult createBankStaffUser(String username, String password, String fullName) {
    	if(bankStaffDAO.findByFullName(fullName)==null 
    			&& bankStaffDAO.findByUsername(username)==null) {
    	BankStaff staff = new BankStaff(username, password, fullName, 
    			generateRandomAfm(), generateRandomPhone());
        bankStaffDAO.add(staff);
        persist();
        return new CommandResult(true,"Succefully created BankStaff :" + fullName);
    	}else return new CommandResult(false,"Error : Username or Full Name already exist");
    }
    
    
    //--------------delete User-------------
    
    public void deleteUser(User user) {
    	if(user instanceof PersonalClient p) {
    		personalClientDAO.delete(p);
    		persist();
    	}else if(user instanceof BusinessClient b) {
    		businessClientDAO.delete(b);
    		for(Bill bill :billDAO.findByIssuer(b)) {
    			bill.getPayer().removeBill(bill);
    			billDAO.delete(bill);
    		}
    		persist();
    	}else if(user instanceof BankStaff ba) {
    		bankStaffDAO.delete(ba);
    		persist();
    	}
    }
    
    //---------------- Commands---------------
 
    public CommandResult deposit(PersonalAccount account, double amount) {
        DepositCommand cmd = new DepositCommand(account, amount, transactionDAO);
        invoker.setCommand(cmd);
        CommandResult res= invoker.execute();
        if(res.isSuccess()) persist();
        return res;
    }

    public CommandResult withdraw(PersonalAccount account, double amount) {
        WithdrawCommand cmd = new WithdrawCommand(account, amount, transactionDAO);
        invoker.setCommand(cmd);
        CommandResult res= invoker.execute();
        if(res.isSuccess()) persist();
        return res;
    }

    private CommandResult transfer(PersonalAccount from, String toIban, double amount
    		, TransferExecution method) {
        TransferCommand cmd = new TransferCommand(from, toIban, amount, method
        		, transactionDAO, personalAccountDAO, businessClientDAO);
        invoker.setCommand(cmd);
        CommandResult res= invoker.execute();
        if(res.isSuccess()) persist();
        return res;
    }

    public CommandResult payBill(PersonalAccount account, Bill bill) {
        PayBillCommand cmd = new PayBillCommand(account, bill,FeeType.BILL.getFee(), transactionDAO);
        invoker.setCommand(cmd);
        CommandResult res= invoker.execute();
        if(res.isSuccess()) persist();
        return res;
    }

    
    public CommandResult issueBill(BusinessClient issuer,
			PersonalClient payer,
            String billCode,
            double amount,
            LocalDate dueDate,
            String description) {
        IssueBillCommand cmd = new IssueBillCommand(issuer,generateRandomBillId(issuer,billDAO),
        		payer, billCode,amount,dueDate,description, billDAO);
        invoker.setCommand(cmd);
        CommandResult res= invoker.execute();
        if(res.isSuccess()) persist();
        return res;
    }

    public CommandResult executeStandingOrder(StandingOrder standingOrder) {
        StandingOrderCommand cmd = new StandingOrderCommand(
                standingOrder,
                transactionDAO,
                personalAccountDAO,
                businessClientDAO
        );
        invoker.setCommand(cmd);
        CommandResult res= invoker.execute();
        if(res.getMessage().equals("false")) {
        	return null;
        }else if(res.isSuccess()) persist();
        return res;
    }
    
    //-----------------Transfers------------------
    
    
    public CommandResult transferInternal(PersonalAccount from, String toIban,
    		double amount) {
    	TransferExecution method = new InternalTransferExecution(personalAccountDAO,
                businessClientDAO,FeeType.INTERNAL.getFee());
    	CommandResult res = transfer(from,toIban,amount,method);
    	if(res.isSuccess()) {
    		persist();
    	}
    	return res;
    }
    
    public CommandResult transferSEPA(PersonalAccount from, String toIban, double amount,
    		String bic) {
    	TransferExecution method = new SEPATransferExecution(FeeType.SEPA.getFee(),bic);
    	CommandResult res = transfer(from,toIban,amount,method);
    	if(res.isSuccess()) {
    		persist();
    	}
    	return res;
    }
    
    public CommandResult transferSWIFT(PersonalAccount from, String toIban, double amount,
    		String swiftCode) {
    	TransferExecution method = new SWIFTTransferExecution(FeeType.SWIFT.getFee(),swiftCode);
    	CommandResult res = transfer(from,toIban,amount,method);
    	if(res.isSuccess()) {
    		persist();
    	}
    	return res;
    }
    
    
    //----------------Standing Orders-------------------
    
    public CommandResult createStandingOrderBill(String description, String name,
    		double amount,PersonalAccount account,
    		LocalDate dueDate,String billCode) { //fees
    	StandingOrder so = new StandingOrderBill(description,name,account.getOwner(),amount,
    			account,LocalDate.now(),dueDate,FeeType.BILL.getFee(),billCode);
    	standingOrderDAO.save(so);
    	persist();
    	return new CommandResult(true,"Η πάγια εντολή πληρωμής λογαριασμού δημιουργήθηκε");
    }
    
    public CommandResult createStandingOrderTransferInternal(String description, String name, 
    		double amount,PersonalAccount account, LocalDate dueDate,
    		int frequency, int exeDate,String reciever) {
    	if(personalAccountDAO.findByIban(reciever)==null && 
    			businessClientDAO.findByIban(reciever)==null) {
    		return new CommandResult(false,"Το iban δεν αντιστοιχεί σε λογαριασμό της τράπεζας");
    	}
    	StandingOrder so = new StandingOrderTransferInternal(description,name,account.getOwner(),amount,account,
    			LocalDate.now(),dueDate,FeeType.INTERNAL.getFee(),frequency,exeDate,reciever);
    	standingOrderDAO.save(so);
    	persist();
    	return new CommandResult(true,"Η πάγια εντολή εσωτερικής μεταφοράς δημιουργήθηκε");
    }
    
    public CommandResult createStandingOrderTransferSEPA(
            String description, String name, double amount,
            PersonalAccount account, LocalDate dueDate,
            int frequency, int exeDate, String reciever, String bic) {

        StandingOrderTransferSEPA so = new StandingOrderTransferSEPABuilder()
                .description(description)
                .name(name)
                .owner(account.getOwner())
                .amount(amount)
                .account(account)
                .issueDate(LocalDate.now())
                .dueDate(dueDate)
                .fees(FeeType.SEPA.getFee())
                .frequency(frequency)
                .exeDate(exeDate)
                .reciever(reciever)
                .bic(bic)
                .build();

        standingOrderDAO.save(so);
        persist();
        return new CommandResult(true, "Η πάγια εντολή SEPA μεταφοράς δημιουργήθηκε");
    }
    
    
    public CommandResult createStandingOrderTransferSWIFT(
            String description, String name, double amount,
            PersonalAccount account, LocalDate dueDate,
            int frequency, int exeDate, String reciever, String swiftCode) {

        StandingOrderTransferSWIFT so = new StandingOrderTransferSWIFTBuilder()
                .description(description)
                .name(name)
                .owner(account.getOwner())
                .amount(amount)
                .account(account)
                .issueDate(LocalDate.now())
                .dueDate(dueDate)
                .fees(FeeType.SWIFT.getFee())
                .frequency(frequency)
                .exeDate(exeDate)
                .reciever(reciever)
                .swiftCode(swiftCode)
                .build();

        standingOrderDAO.save(so);
        persist();
        return new CommandResult(true, "Η πάγια εντολή SWIFT μεταφοράς δημιουργήθηκε");
    }

    
    //----------------log in---------------------
    
    public User logIn(String username,String password,String type) {
    	User user;
    	switch(type) {
    	case "Πελάτης - Φυσικό Πρόσωπο":
    		user = personalClientDAO.findByUsername(username);
    		if(user!=null && user.getPassword().equals(password)) {
    			return user;
    		}else return null;
    	case "Πελάτης - Επιχείρηση":
    		user = businessClientDAO.findByUsername(username);
    		if(user!=null && user.getPassword().equals(password)) {
    			return user;
    		}else return null;
    	case "Προσωπικό Τράπεζας":
    		user = bankStaffDAO.findByUsername(username);
    		if(user!=null && user.getPassword().equals(password)) {
    			return user;
    		}else return null;
    	case "Administrator":
    		user = administratorDAO.findByUsername(username);
    		if(user!=null && user.getPassword().equals(password)) {
    			return user;
    		}else return null;
    	default : return null;
    	}
    }
    
    //------------remove Standing Order------------
    
    public void removeStandigOrder(StandingOrder s) {
    	standingOrderDAO.delete(s);
    	persist();
    }
    
    //-----------find methods--------------
    
    
    public String getInfoForPersonalAccount(PersonalAccount b) {
    	return b.getIban() + "\nInterest Rate:"+b.getInterestRate() 
    	+ "\nOwner:"+b.getOwner().getFullName() + "\nPhone:" + b.getOwner().getPhone();
    }
    
    public String getInfoForBusinessAccount(BusinessAccount b) {
    	return b.getIban() + "\nInterest Rate:"+b.getInterestRate() 
    	+ "\nOwner:"+b.getOwner().getFullName() + "\nPhone:" + b.getOwner().getPhone();
    }
    
    public ArrayList<User> getAllUsers(){
    	ArrayList<User> users = new ArrayList<User>();
    	for(PersonalClient p : personalClientDAO.findAll()) {
    		users.add(p);
    	}
    	for(BusinessClient p : businessClientDAO.findAll()) {
    		users.add(p);
    	}
    	for(BankStaff p : bankStaffDAO.findAll()) {
    		users.add(p);
    	}
    	return users;
    }
    
    public PersonalAccount findPersonalByIban(String iban) {
    	return personalAccountDAO.findByIban(iban);
    }
    
    public PersonalClient findPersonalByUsername(String username) {
    	return personalClientDAO.findByUsername(username);
    }
    
    public PersonalClient findPersonalByFullName(String fullName) {
    	return personalClientDAO.findByFullName(fullName);
    }
    
    //--------------change Status-----------------
    public void changeStatus(BankAccount b) {
    	if(b.getStatus().equals(AccountStatus.ACTIVE)) {
    		b.setStatus(AccountStatus.BLOCKED);
    	}else b.setStatus(AccountStatus.ACTIVE);
    	persist();
    }
    
    
    //---------------add methods--------------------
    
    public void addCoOwnerToPersonalAccount(PersonalClient c, PersonalAccount a) {
    	a.addCoOwner(c);
    	c.addPersonalAccount(a);
    	persist();
    }
    
    //---------------Monthly Fee----------------------
    
    public void applyMonthlyFee() {
    	for (BusinessClient client : businessClientDAO.findAll()) {
            applyFee(client);
        }
    	persist();
    }
    
    private void applyFee(BusinessClient client) {
    	double monthlyFee = BusinessFee.calculateMonthlyFee(client);
    	client.getAccount().setmonthlyUpkeepFee(monthlyFee);
    	
    	client.getAccount().applyFee();
    }
    
    //---------------Interest Generation-----------------
    
    public void applyMonthlyInterest() {

        for (PersonalAccount acc : personalAccountDAO.findAll()) {
            applyInterest(acc);
        }

        for (BusinessAccount acc : businessClientDAO.getAllBusinessAccounts()) {
            applyInterest(acc);
        }
        
        persist();
    }

    public void applyInterest(BankAccount account) {
        InterestPolicy policy = InterestPolicyFactory.forAccount(account);
        float rate = policy.calculateInterest(account);
        
        account.setInterestRate(rate);
        account.applyInterest();
    }
    
    //-----------------Bill Id generation----------------------------
    
    public static String generateRandomBillId(BusinessClient issuer, BillDAO billDAO) {
        String prefix = issuer.getFullName().substring(0, Math.min(2, issuer.getFullName().length())).toUpperCase();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);
        
        String billId;
        do {
            int randomDigits = RANDOM.nextInt(1000); // 0..999
            String randomPart = String.format("%03d", randomDigits);
            billId = prefix + datePart + randomPart;
        } while (billDAO.findByBillId(billId) != null);

        return billId;
    }
    
    
    //----------------IBAN generation------------------
    
    public String generateUniqueIban() {
        Set<String> existingIbans = collectAllIbans();
        String iban;

        do {
            iban = generateRandomGreekIban();
        } while (existingIbans.contains(iban));

        return iban;
    }

    private Set<String> collectAllIbans() {
        Set<String> ibans = new HashSet<>();

        for (PersonalAccount acc : personalAccountDAO.findAll()) {
            ibans.add(acc.getIban());
        }

        for (BusinessAccount acc : businessClientDAO.getAllBusinessAccounts()) {
            ibans.add(acc.getIban());
        }

        return ibans;
    }

    private String generateRandomGreekIban() {
        StringBuilder sb = new StringBuilder("GR");

        for (int i = 0; i < 18; i++) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }
    
    //----------generate Random Phone and afm------------
    
    public static String generateRandomPhone() {
        StringBuilder phone = new StringBuilder("69");

        for (int i = 0; i < 8; i++) {
            phone.append((int) (Math.random() * 10));
        }

        return phone.toString();
    }
    
    public static String generateRandomAfm() {
        StringBuilder afm = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            afm.append((int) (Math.random() * 10));
        }

        return afm.toString();
    }

    
    //-------------------Fee Type--------------------
    
    public enum FeeType {
        INTERNAL(0.10),
        SEPA(1.20),
        SWIFT(4.00),
        BILL(0.30);

        private final double fee;

        FeeType(double fee) {
            this.fee = fee;
        }

        public double getFee() {
            return fee;
        }
    }

    
}
