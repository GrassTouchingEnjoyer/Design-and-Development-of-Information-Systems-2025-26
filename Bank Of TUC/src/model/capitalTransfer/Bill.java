package model.capitalTransfer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import model.actors.*;

public class Bill implements Serializable{
	
	private BusinessClient issuer;
	private final String billId;
	private final String billCode;
	private PersonalClient payer; 
	private final double amount;
	private final LocalDate issueDate;
	private final LocalDate dueDate;
	private final String description;
	private BillStatus status=BillStatus.PENDING;
	
	
	public Bill(BusinessClient issuer, String billId, String billCode, PersonalClient payer, double amount,
			LocalDate issueDate, LocalDate dueDate, String description) {
		this.issuer = issuer;
		this.billId = billId;
		this.billCode = billCode;
		this.payer = payer;
		this.amount = amount;
		this.issueDate = issueDate;
		this.dueDate = dueDate;
		this.description = description;
	}

	
	
	public boolean isOverDue() {
	    if (dueDate == null || status == BillStatus.PAYED || status == BillStatus.CANCELED) {
	        return false;
	    }
	    boolean res = LocalDate.now().isAfter(dueDate);
	    if(res) {
	    	status=BillStatus.OVERDUE;
	    	return res;
	    }else return false;
	    
	}
	
	public void markAsPaid() {
		status=BillStatus.PAYED;
	}
	
	public void markAsCanceled() {
		status=BillStatus.CANCELED;
	}
	
	
	
	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}
	
	public boolean isFromLastMonth() {
		 YearMonth billMonth = YearMonth.from(issueDate);
	     YearMonth lastMonth = YearMonth.now().minusMonths(1);

	     return billMonth.equals(lastMonth);
	}
	
	@Override
	public String toString() {

	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	    StringBuilder sb = new StringBuilder();

	    sb.append("Bill ID: ").append(billId);
	    sb.append("  Bill Code: ").append(billCode);
	    sb.append("  Status: ").append(status != null ? status : "PENDING");
	    sb.append("  Amount: ").append(String.format("%.2f €", amount));
	    sb.append("  Payer: ")
	      .append(payer != null ? payer.getFullName() : "N/A");
	    sb.append("  Issue Date: ")
	      .append(issueDate != null ? issueDate.format(fmt) : "N/A");

	    sb.append("  Due Date: ")
	      .append(dueDate != null ? dueDate.format(fmt) : "N/A");

	    sb.append("  Description: ")
	      .append(description != null && !description.isEmpty() ? description : "-");

	    return sb.toString();
	}

	public BusinessClient getIssuer() {
		return issuer;
	}

	public String getBillId() {
		return billId;
	}

	public String getBillCode() {
		return billCode;
	}

	public PersonalClient getPayer() {
		return payer;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setIssuer(BusinessClient issuer) {
		this.issuer = issuer;
	}

	public void setPayer(PersonalClient payer) {
		this.payer = payer;
	}


	public enum BillStatus{
		PENDING,
		PAYED,
		OVERDUE,
		CANCELED
	}
	
	
	
}
