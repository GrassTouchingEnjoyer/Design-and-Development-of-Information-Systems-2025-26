package controller;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.actors.Administrator;
import model.actors.BankAccount;
import model.actors.BankStaff;
import model.actors.BusinessAccount;
import model.actors.BusinessClient;
import model.actors.Client;
import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.actors.User;
import model.capitalTransfer.Bill;
import model.capitalTransfer.Bill.BillStatus;
import model.capitalTransfer.StandingOrder;
import model.capitalTransfer.Transaction;
import model.command.CommandResult;
import model.system.BankingSystemService;
import view.AdminDashboardView;
import view.BillsView;
import view.BusinessBillsView;
import view.BusinessDashboardView;
import view.CreateStandingOrderView;
import view.CustomerDashboardView;
import view.IssueBillView;
import view.LoginView;
import view.MainFrame;
import view.StandingOrdersView;
import view.StartView;
import view.TransferView;

public class MainController {

    private MainFrame mainFrame;
    private StartView startView;
    private LoginView loginView;
    private String selectedRole;
    private final BankingSystemService service;
    private User user;
    private PersonalAccount pAccount;
    private BusinessAccount bAccount;
    
    //prosorina
    private double balance = 5250.00;
  

    public MainController(MainFrame mainFrame,
                          StartView startView,
                          LoginView loginView) {
    	service = BankingSystemService.getInstance();
        this.mainFrame = mainFrame;
        this.startView = startView;
        this.loginView = loginView;

        initController();
    }
    


    
    private void initController() {
        handleStartView();
        handleLoginView();
        handleCustomerDashboard();
        handleBillsView();
        handleTransferView();
        handleStandingOrdersView();
        handleCreateStandingOrderView();
        handleBusinessDashboard();
        handleBusinessBillsView();
        handleIssueBillView();
        handleAdminDashboard();

    }


    // ---------------- Start View ----------------
    private void handleStartView() {
    	service.applyMonthlyInterest();
    	service.applyMonthlyFee();
    	
        startView.getContinueButton().addActionListener(e -> {
            selectedRole = startView.getRoleList().getSelectedValue();

            if (selectedRole == null) {
                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Παρακαλώ επιλέξτε τύπο χρήστη",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            System.out.println("Επιλεγμένος ρόλος: " + selectedRole);

            mainFrame.showLoginView();
        });
    }

    // ---------------- Login View ----------------
    private void handleLoginView() {
        loginView.getLoginButton().addActionListener(e -> {
            String username = loginView.getUsernameField().getText();
            String password = new String(loginView.getPasswordField().getPassword());
            user = service.logIn(username, password, selectedRole);
            
            if ("Πελάτης - Φυσικό Πρόσωπο".equals(selectedRole) && user instanceof PersonalClient pc) {
            	pAccount=pc.getAccounts().get(0);
            	updateDashboardView();
            	executeStandingOrders();
            } else if (selectedRole.equals("Πελάτης - Επιχείρηση") && user instanceof BusinessClient bc) {
            	bAccount=bc.getAccount();
            	updateBusinessDashboardView();
            } else if (selectedRole.equals("Administrator")&& user instanceof Administrator a) {
            	AdminDashboardView adminDashboardView = mainFrame.getAdminDashboardView();
                adminDashboardView.getAdminNameLabel().setText("Admin: " + a.getFullName());
                adminDashboardView.clearAllFields();
                adminDashboardView.showAdminFeatures(true); // εμφανίζουμε όλα τα admin components
                mainFrame.showAdminDashboardView();
            } else if (selectedRole.equals("Προσωπικό Τράπεζας")&& user instanceof BankStaff b) {
            	AdminDashboardView adminDashboardView = mainFrame.getAdminDashboardView();
                adminDashboardView.getAdminNameLabel().setText("Staff: " + b.getFullName());
                adminDashboardView.clearAllFields();
                adminDashboardView.showAdminFeatures(false); // κρύβουμε διαγραφή + tab προσωπικό
                mainFrame.showAdminDashboardView();
            }
            else {
                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Λάθος στοιχεία σύνδεσης",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    
    private void handleCustomerDashboard() {

        CustomerDashboardView dashboard = mainFrame.getCustomerDashboardView();
        
        //----------- change Acount -----------
        dashboard.getChangeAccountButton().addActionListener(e -> showChangeAccountDialog());
        
        // ----------- Bills -----------
        dashboard.getBillsButton().addActionListener(e -> showBills());
        
        // ----------- Transfer -----------
        dashboard.getTransferButton().addActionListener(e -> {
        	mainFrame.getTransferView().clearFields();
            mainFrame.showTransferView();
        });
        // ----------- StandingOrder -----------
        dashboard.getStandingOrderButton().addActionListener(e -> showStandingOrders());
        
        // ----------- ShareAccount -----------
        dashboard.getShareAccountButton().addActionListener(e -> showShareAccountDialog());
        
        // ----------- log out -----------
        dashboard.getLogoutButton().addActionListener(e -> logout());

        //------------ block Account----------------
        dashboard.getBlockAccountButton().addActionListener(e ->{
        service.changeStatus(pAccount);
        JOptionPane.showMessageDialog(
                mainFrame,
                "Ο λογαριασμός έγινε :" + pAccount.getStatus(),
                "Φραγή Λογαριασμού",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        // ----------- Create Account -----------
        dashboard.getCreateAccountButton().addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Θέλετε να δημιουργήσετε νέο τραπεζικό λογαριασμό;",
                    "Δημιουργία Λογαριασμού",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                System.out.println("Αίτημα δημιουργίας νέου λογαριασμού");
                service.createPersonalAccount((PersonalClient)user);
                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Το αίτημα καταχωρήθηκε επιτυχώς.",
                        "Επιτυχία",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });


        // ----------- Κατάθεση -----------
        dashboard.getDepositButton().addActionListener(e -> {
            String input = JOptionPane.showInputDialog(
                    mainFrame,
                    "Εισάγετε ποσό κατάθεσης:",
                    "Κατάθεση",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (input == null) return;

            try {
                double amount = Double.parseDouble(input);

                if (amount <= 0) {
                    throw new NumberFormatException();
                }
                result(service.deposit(pAccount, amount));
                updateDashboardView();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Μη έγκυρο ποσό",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // ----------- Ανάληψη -----------
        dashboard.getWithdrawButton().addActionListener(e -> {
            String input = JOptionPane.showInputDialog(
                    mainFrame,
                    "Εισάγετε ποσό ανάληψης:",
                    "Ανάληψη",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (input == null) return;

            try {
                double amount = Double.parseDouble(input);

                if (amount <= 0) {
                    throw new NumberFormatException();
                }

                result(service.withdraw(pAccount, amount));
                updateDashboardView();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Μη έγκυρο ποσό",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        dashboard.getInfoButton().addActionListener(e -> {
        	String message = service.getInfoForPersonalAccount(pAccount);
        	JOptionPane.showMessageDialog(
                    mainFrame,
                    message,
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
    
    private void updateBalanceLabel() {
        mainFrame.getCustomerDashboardView()
                .getBalanceLabel()
                .setText(String.format("Υπόλοιπο: %.2f €", balance));
    }
    
    private void updateNameLabel() {
        mainFrame.getCustomerDashboardView().setCustomerName(user.getFullName());
    }
    
    private void updateBusinessBalanceLabel() {
        mainFrame.getBusinessDashboardView()
                .getBalanceLabel()
                .setText(String.format("Υπόλοιπο: %.2f €", balance));
    }
    
    private void updateBusinessNameLabel() {
        mainFrame.getBusinessDashboardView().setCustomerName(user.getFullName());
    }
    
    
    private void showBills() {
        BillsView billsView = mainFrame.getBillsView();
        billsView.clearBills();
        PersonalClient p = (PersonalClient)user;
        for (Bill bill : p.getBillsByStatus(BillStatus.PENDING)) {
            billsView.addBill(bill.getIssuer().getFullName(), bill.getAmount(),
            		bill.getBillCode(),bill.getDueDate().toString(), () -> {
            			result(service.payBill(pAccount, bill));
            			showBills();
            		});
        }

        mainFrame.showBillsView();
    }
    
    private void handleBillsView() {
        BillsView billsView = mainFrame.getBillsView();

        billsView.getBackButton().addActionListener(e -> {
        	updateDashboardView();
        });
    }
    
    
    private void showBusinessBills() {
        BusinessBillsView billsView = mainFrame.getBusinessBillsView();
        billsView.clearBills();
        
        for (Bill bill : bAccount.getOwner().getBills()) {
            billsView.addBill(bill.toString());
        }

        mainFrame.showBusinessBillsView();
    }
    
    private void handleBusinessBillsView() {
        BusinessBillsView billsView = mainFrame.getBusinessBillsView();
        IssueBillView issueBillView = mainFrame.getIssueBillView();

        billsView.getBackButton().addActionListener(e -> {
            mainFrame.showBusinessDashboardView();
        });
        billsView.getIssueButton().addActionListener(e -> {
            issueBillView.clearFields();
            mainFrame.showIssueBillView();
        });

    }
    
    private void showChangeAccountDialog() {
    	PersonalClient pc = (PersonalClient) user;
    	ArrayList<PersonalAccount> ps =  pc.getAccounts();
        JList<PersonalAccount> ibanList = new JList<PersonalAccount>(ps.toArray(new PersonalAccount[0]));
        ibanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ibanList.setSelectedValue(pAccount, true);
        ibanList.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(ibanList);
        scrollPane.setPreferredSize(new Dimension(450, 150));

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                scrollPane,
                "Επιλογή Λογαριασμού (IBAN)",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION && ibanList.getSelectedValue() != null) {
        	pAccount = ibanList.getSelectedValue();
        	updateDashboardView();
        	executeStandingOrders();
        }
    }
    
    private void handleBusinessDashboard() {
        BusinessDashboardView view = mainFrame.getBusinessDashboardView();

        view.getBlockAccountButton().addActionListener(e ->{
            service.changeStatus(bAccount);
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Ο λογαριασμός έγινε :" + bAccount.getStatus(),
                    "Φραγή Λογαριασμού",
                    JOptionPane.INFORMATION_MESSAGE);
            });
        
        // ----------- Logout -----------
        view.getLogoutButton().addActionListener(e -> logout());

        // ----------- Bills -----------
        view.getBillsButton().addActionListener(e -> showBusinessBills());
        
        view.getAccountInfoButton().addActionListener(e -> {
        	String message = service.getInfoForBusinessAccount(bAccount);
        	JOptionPane.showMessageDialog(
                    mainFrame,
                    message,
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    
    private void handleTransferView() {
        TransferView transferView = mainFrame.getTransferView();

        transferView.getBackButton().addActionListener(e -> {
        	updateDashboardView();
        });

        transferView.getTransferButton().addActionListener(e -> {
            handleTransferAction();
        });
    }
    
    private void handleTransferAction() {
        TransferView v = mainFrame.getTransferView();
        String choice = (String)v.getTransferTypeCombo().getSelectedItem();
        double amount;
        try {
            amount = Double.parseDouble(v.getAmountField().getText().trim());
        } catch (NumberFormatException e) {
            showError("Please Enter a valid amount number");
            return;
        }
        switch(choice) {
        case "Ενδοτραπεζική συναλλαγή":
        	result(service.transferInternal(pAccount, v.getIbanField().getText().trim(),amount));
        	break;
        case "SEPA" :
        	result(service.transferSEPA(pAccount, v.getIbanField().getText().trim(),
        			amount,v.getExtraField().getText().trim()));
        	break;
        case "SWIFT" :
        	result(service.transferSWIFT(pAccount, v.getIbanField().getText().trim(),
        			amount,v.getExtraField().getText().trim()));
        	break;
        }
        
        updateDashboardView();
    }
    
    private void handleStandingOrdersView() {
        StandingOrdersView view = mainFrame.getStandingOrdersView();

        view.getBackButton().addActionListener(e ->
                mainFrame.showCustomerDashboard()
        );

        view.getCreateButton().addActionListener(e ->{
        	mainFrame.getCreateStandingOrderView().clearFields();
        	mainFrame.showCreateStandingOrderView();
        });
    }

    
    private void showStandingOrders() {
        StandingOrdersView view = mainFrame.getStandingOrdersView();
        view.clearOrders();
        

        for (StandingOrder order : pAccount.getStandingOrders()) {
            view.addStandingOrder(order.toString(), () -> {
                service.removeStandigOrder(order);
                showStandingOrders();
            });
        }

        mainFrame.showStandingOrdersView();
    }
    
    private void handleCreateStandingOrderView() {
        CreateStandingOrderView view = mainFrame.getCreateStandingOrderView();

        view.getBackButton().addActionListener(e ->
                mainFrame.showStandingOrdersView()
        );

        view.getCreateButton().addActionListener(e -> {

            String type = (String) view.getTypeCombo().getSelectedItem();
            double amount;
            double amount2;
            int frequency;
            int exeDate;
           
            
            if ("Μεταφορά".equals(type)) {
            	LocalDate dueDate = parseDate(view.getTransferActiveUntilField().getText());
                if(dueDate==null) {
                	return;
                }
            	try {
            		amount = Double.parseDouble(view.getTransferAmountField().getText());
                } catch (NumberFormatException ex) {
                    showError("Please Enter a valid amount number");
                    return;
                }
            	
            	try {
            		frequency = Integer.parseInt(view.getTransferFrequencyField().getText().trim());
            	} catch (NumberFormatException exc) {
                    showError("Please Enter a valid frequency number");
                    return;
                }if(frequency<0) showError("Please Enter a valid frequency number");
            	try {
            		exeDate = Integer.parseInt(view.getTransferExecutionDayField().getText().trim());
            	} catch (NumberFormatException exc) {
                    showError("Please Enter a valid execution Date number");
                    return;
                }if(exeDate<0 || exeDate>28) showError("The execution Date number must be between 0-28");
            	
            	
            	String type2 = (String) view.getTransferTypeCombo().getSelectedItem();
            	if(type2.equals("SEPA")) {
            		
            		if(view.getTransferBicSwiftField().getText().trim().isBlank()) {
            			showError("Enter a Bic");
            			return;
            		}
            		result(service.createStandingOrderTransferSEPA(view.getTransferDescriptionArea().getText().trim()
            				, view.getTransferNameField().getText(), amount, 
            				pAccount, dueDate, frequency, exeDate, view.getTransferIbanField().getText().trim()
            				, view.getTransferBicSwiftField().getText().trim()));
            	}else if (type2.equals("SWIFT")) {
            		
            		if(view.getTransferBicSwiftField().getText().trim().isBlank()) {
            			showError("Enter a Swift Code");
            			return;
            		}
            		result(service.createStandingOrderTransferSWIFT(view.getTransferDescriptionArea().getText().trim()
            				, view.getTransferNameField().getText(), amount, 
            				pAccount, dueDate, frequency, exeDate, view.getTransferIbanField().getText().trim()
            				, view.getTransferBicSwiftField().getText().trim()));
            	}else {
            		result(service.createStandingOrderTransferInternal(view.getTransferDescriptionArea().getText().trim()
            				, view.getTransferNameField().getText(), amount, 
            				pAccount, dueDate, frequency, exeDate, view.getTransferIbanField().getText().trim()));
            	}

            } else if ("Πληρωμή".equals(type)) {
            	
            	LocalDate dueDate2 = parseDate(view.getPaymentActiveUntilField().getText());
                if(dueDate2==null) {
                	return;
                }
            	try {
                	amount2 = Double.parseDouble(view.getPaymentMaxAmountField().getText());
                } catch (NumberFormatException ex) {
                    showError("Please Enter a valid amount number");
                    return;
                }
            	
            	result(service.createStandingOrderBill(view.getPaymentDescriptionArea().getText().trim()
        				, view.getPaymentNameField().getText(), amount2, 
        				pAccount, dueDate2, view.getPaymentCodeField().getText().trim()));
                
            }
            
            showStandingOrders();
        });

    }
    
    private void showShareAccountDialog() {

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                panel,
                "Μοίρασμα Λογαριασμού",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim().toLowerCase();

            if (username.isEmpty()) {
                showError("Το username δεν μπορεί να είναι κενό.");
                return;
            }
            PersonalClient coOwner = service.findPersonalByUsername(username);

            if (coOwner==null) {
                showError("Ο χρήστης δεν βρέθηκε.");
                return;
            }
            service.addCoOwnerToPersonalAccount(coOwner, pAccount);
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Ο λογαριασμός μοιράστηκε επιτυχώς με τον χρήστη: " + username,
                    "Επιτυχία",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    private void loadBusinessTransactions(BusinessAccount b) {
        BusinessDashboardView view = mainFrame.getBusinessDashboardView();

        view.clearTransactions();

        for(Transaction t: b.getTransactions()) {
        	view.addTransaction(t.toString());
        }
    }
    
    private void loadCustomerTransactions(PersonalAccount p) {
        CustomerDashboardView view = mainFrame.getCustomerDashboardView();
        
        view.clearTransactions();
        for(Transaction t : p.getTransactions()) {
        	view.addTransaction(t.toString());
        }
    }
    
    private void handleIssueBillView() {
    	IssueBillView issueBillView = mainFrame.getIssueBillView();
        issueBillView.getBackButton().addActionListener(e ->
                mainFrame.showBusinessBillsView()
        );

        issueBillView.getIssueButton().addActionListener(e -> {
        	
        	PersonalClient payer = service.findPersonalByFullName(issueBillView.getCustomer());
        	if(payer==null) {
        		showError("Το όνομα δεν αντιστοιχεί σε Όνομα πελάτη");
        		return;
        	}
        	double amount=0;
        	try {amount = Double.parseDouble(issueBillView.getAmount());

                if (amount <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Μη έγκυρο ποσό",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        	LocalDate dueDate = parseDate(issueBillView.getDeadline());
            if(dueDate==null) {
            	return;
            }
        	result(service.issueBill(bAccount.getOwner(), payer, issueBillView.getPaymentCode(), 
        			amount, dueDate, issueBillView.getName()));

            issueBillView.clearFields();
            showBusinessBills();
        });
    }
    
    private void handleAdminDashboard() {

        AdminDashboardView view = mainFrame.getAdminDashboardView();
        
        view.getDeleteUserButton().addActionListener(e -> showDeleteUserDialog());

        view.getLogoutButton().addActionListener(e -> {
            mainFrame.showStartView();
        });

        view.getClientCreateButton().addActionListener(e -> {
        	result(service.createPersonalClient(view.getClientUsernameField().getText(), 
        			view.getClientPasswordField().getText(), 
        			view.getClientNameField().getText()));
        	
            view.getClientUsernameField().setText("");
            view.getClientNameField().setText("");
            view.getClientPasswordField().setText("");
        });

        view.getBusinessCreateButton().addActionListener(e -> {
        	result(service.createBusinessClient(view.getBusinessUsernameField().getText(), 
        			view.getBusinessPasswordField().getText(), 
        			view.getBusinessNameField().getText()));
        	
            view.getBusinessUsernameField().setText("");
            view.getBusinessNameField().setText("");
            view.getBusinessPasswordField().setText("");
        });

        view.getStaffCreateButton().addActionListener(e -> {
        	result(service.createBankStaffUser(view.getStaffUsernameField().getText(), 
        			view.getStaffPasswordField().getText(), 
        			view.getStaffNameField().getText()));
        	
            view.getStaffUsernameField().setText("");
            view.getStaffNameField().setText("");
            view.getStaffPasswordField().setText("");
        });
    }
    
    private void showDeleteUserDialog() {
    	ArrayList<User> users = service.getAllUsers();

    	// Μετατροπή ArrayList σε πίνακα
    	User[] userArray = users.toArray(new User[0]);

    	User selectedUser = (User) JOptionPane.showInputDialog(
    	        mainFrame,
    	        "Επιλέξτε χρήστη για διαγραφή:",
    	        "Διαγραφή χρήστη",
    	        JOptionPane.PLAIN_MESSAGE,
    	        null,
    	        userArray,
    	        userArray[0]
    	);


        if (selectedUser != null) {
            System.out.println("Ο χρήστης προς διαγραφή: " + selectedUser);
            service.deleteUser(selectedUser);
        }
    }
    
    public void updateDashboardView() {
    	loadCustomerTransactions(pAccount);
    	balance = pAccount.getBalance();
    	updateBalanceLabel();
    	updateNameLabel();
    	mainFrame.showCustomerDashboard();
    }
    
    public void updateBusinessDashboardView() {
	    loadBusinessTransactions(bAccount);
		balance = bAccount.getBalance();
		updateBusinessBalanceLabel();
		updateBusinessNameLabel();
	    mainFrame.showBusinessDashboardView();
    }
    
    public void executeStandingOrders(){
    	for(StandingOrder so:pAccount.getStandingOrders()) {
    		result(service.executeStandingOrder(so));
    		loadCustomerTransactions(pAccount);
    	}
    }
    


    private LocalDate parseDate(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            return LocalDate.parse(text, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Η ημερομηνία πρέπει να είναι της μορφής dd/MM/yyyy",
                    "Λάθος ημερομηνία",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    
    private void logout() {
        pAccount = null;
        balance = 0;

        mainFrame.showStartView();
    }

    private void result(CommandResult res) {
    	
    	if(res==null) return;
    	
    	if(res.isSuccess()) {
    		showSuccess(res.getMessage());
    	}else {
    		showError(res.getMessage());
    	}
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
                mainFrame,
                message,
                "Επιτυχία",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                mainFrame,
                message,
                "Σφάλμα",
                JOptionPane.ERROR_MESSAGE
        );
    }







}

