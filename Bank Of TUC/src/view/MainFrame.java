package view;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private StartView startView;
    private LoginView loginView;
    private CustomerDashboardView customerDashboardView;
    private BillsView billsView;
    private TransferView transferView;
    private StandingOrdersView standingOrdersView;
    private CreateStandingOrderView createStandingOrderView;
    private BusinessDashboardView businessDashboardView;
    private BusinessBillsView businessBillsView;
    private IssueBillView issueBillView;
    private AdminDashboardView adminDashboardView;




    public MainFrame() {
        setTitle("Bank Of TUC");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        startView = new StartView();
        loginView = new LoginView();
        customerDashboardView = new CustomerDashboardView();
        billsView = new BillsView();
        transferView = new TransferView();
        standingOrdersView = new StandingOrdersView();
        createStandingOrderView = new CreateStandingOrderView();
        businessDashboardView = new BusinessDashboardView();
        businessBillsView = new BusinessBillsView();
        issueBillView = new IssueBillView();
        adminDashboardView = new AdminDashboardView();


        mainPanel.add(startView, "START");
        mainPanel.add(loginView, "LOGIN");
        mainPanel.add(customerDashboardView, "CUSTOMER_DASHBOARD");
        mainPanel.add(billsView, "BILLS_VIEW");
        mainPanel.add(transferView, "TRANSFER_VIEW");
        mainPanel.add(standingOrdersView, "STANDING_ORDERS_VIEW");
        mainPanel.add(createStandingOrderView, "CREATE_STANDING_ORDER_VIEW");
        mainPanel.add(businessDashboardView, "BUSINESS_DASHBOARD_VIEW");
        mainPanel.add(businessBillsView, "BUSINESS_BILLS_VIEW");
        mainPanel.add(issueBillView, "ISSUE_BILL");
        mainPanel.add(adminDashboardView, "ADMIN_DASHBOARD");

        add(mainPanel);
    }
    
    public void showStartView() {
        cardLayout.show(mainPanel, "START");
    }
    
    public void showLoginView() {
    	loginView.clearFields();
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showCustomerDashboard() {
        cardLayout.show(mainPanel, "CUSTOMER_DASHBOARD");
    }

    public StartView getStartView() {
        return startView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public CustomerDashboardView getCustomerDashboardView() {
        return customerDashboardView;
    }
    
    public BillsView getBillsView() {
        return billsView;
    }

    public void showBillsView() {
        cardLayout.show(mainPanel, "BILLS_VIEW");
    }
    
    public void showTransferView() {
        cardLayout.show(mainPanel, "TRANSFER_VIEW");
    }

    public TransferView getTransferView() {
        return transferView;
    }
    
    public void showStandingOrdersView() {
        cardLayout.show(mainPanel, "STANDING_ORDERS_VIEW");
    }

    public StandingOrdersView getStandingOrdersView() {
        return standingOrdersView;
    }
    
    public void showCreateStandingOrderView() {
        cardLayout.show(mainPanel, "CREATE_STANDING_ORDER_VIEW");
    }

    public CreateStandingOrderView getCreateStandingOrderView() {
        return createStandingOrderView;
    }
    
    public void showBusinessDashboardView() {
        cardLayout.show(mainPanel, "BUSINESS_DASHBOARD_VIEW");
    }

    public BusinessDashboardView getBusinessDashboardView() {
        return businessDashboardView;
    }
    
    public BusinessBillsView getBusinessBillsView() {
        return businessBillsView;
    }

    public void showBusinessBillsView() {
        cardLayout.show(mainPanel, "BUSINESS_BILLS_VIEW");
    }

	public IssueBillView getIssueBillView() {
		return issueBillView;
	}
    
	public void showIssueBillView() {
	    cardLayout.show(mainPanel, "ISSUE_BILL");
	}

	public AdminDashboardView getAdminDashboardView() {
		return adminDashboardView;
	}
	
	public void showAdminDashboardView() {
	    cardLayout.show(mainPanel, "ADMIN_DASHBOARD");
	}

}



