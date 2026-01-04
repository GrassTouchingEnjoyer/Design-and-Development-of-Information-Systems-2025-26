package model.system_data.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.actors.*;
import model.capitalTransfer.*;
import model.factory.AccountFactory;
import model.factory.ClientFactory;

public class FileLoader {

    private final String FILE_CLIENTS       = "data/database.csv";
    private final String FILE_ACCOUNTS      = "data/trlogariasmoi.csv";
    private final String FILE_STANDING      = "data/pagiesentoles.csv";
    private final String FILE_BILLS         = "data/logariasmoiplhrwmwn.csv";

    public void load(List<PersonalClient> personalClients,
                     List<BusinessClient> businessClients,
                     List<Administrator> admins,
                     List<PersonalAccount> personalAccounts,
                     List<Bill> bills) {

        loadClients(personalClients, businessClients, admins);
        loadAccounts(personalClients, businessClients, personalAccounts);
        loadStandingOrders(personalClients, personalAccounts);
        loadBills(personalClients, businessClients, bills);
    }

    // ----------------- Clients -----------------
    private void loadClients(List<PersonalClient> personalClients,
                             List<BusinessClient> businessClients,
                             List<Administrator> admins) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CLIENTS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (row.length < 1) continue;

                switch (row[0].trim()) {
                    case "ΔΙΑΧΕΙΡΙΣΤΗΣ":
                        admins.add(new Administrator(
                                row[2].trim(), "123", row[1].trim(), "0", "0"));
                        break;

                    case "Φ.ΠΡΟΣΩΠΟ":
                    	Client pclient = ClientFactory.createClient("Φ.ΠΡΟΣΩΠΟ",
                    			row[2].trim(), "123", row[1].trim(), row[3].trim(),"");
                        personalClients.add((PersonalClient) pclient);
                        break;

                    case "Ν.ΠΡΟΣΩΠΟ":
                    	Client bclient = ClientFactory.createClient("Ν.ΠΡΟΣΩΠΟ",
                    			row[2].trim(), "123", row[1].trim(), row[3].trim(),"");
                        businessClients.add((BusinessClient) bclient);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------- Accounts -----------------
    private void loadAccounts(List<PersonalClient> personalClients,
                              List<BusinessClient> businessClients,
                              List<PersonalAccount> personalAccounts) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ACCOUNTS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (row.length < 2) continue;

                // Personal Accounts
                for (PersonalClient pc : personalClients) {
                    if (pc.getFullName().equals(row[1].trim())) {
                        float interestRate = Float.parseFloat(row[2].replace("%",""));
                        double balance = Double.parseDouble(
                        	    row[3].replace("\"","")  
                        	          .replace("€","")
                        	          .replace(",","")
                        	          .trim()
                        	);
                        BankAccount ac = AccountFactory.createAccount("PERSONAL", pc,
                        		row[0].trim(), balance);
                        ac.setInterestRate(interestRate);
                        PersonalAccount acc = (PersonalAccount) ac;
                        pc.addPersonalAccount(acc);
                        personalAccounts.add(acc);

                        // Co-owners
                        if (row.length > 5 && !row[5].trim().isEmpty()) {
                            String[] coOwners = row[5].replace("\"","").split(",");
                            for (String name : coOwners) {
                                for (PersonalClient co : personalClients) {
                                    if (co.getFullName().equals(name.trim())) {
                                        acc.addCoOwner(co);
                                        co.addPersonalAccount(acc);
                                    }
                                }
                            }
                        }
                    }
                }

                // Business Accounts
                for (BusinessClient bc : businessClients) {
                    if (bc.getFullName().equals(row[1].trim())) {
                        float interestRate = Float.parseFloat(row[2].replace("%",""));
                        double balance = Double.parseDouble(
                        	    row[3].replace("\"","")  
                        	          .replace("€","")
                        	          .replace(",","")
                        	          .trim()
                        	);
                        float fee = Float.parseFloat(row[4].replace("€","").replace(",", ""));
                        BankAccount ac = AccountFactory.createAccount("BUSINESS", bc,
                        		row[0].trim(), balance);
                        ac.setInterestRate(interestRate);
                        BusinessAccount acc = (BusinessAccount)ac;
                        acc.setmonthlyUpkeepFee(fee);
                        bc.setAccount(acc);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ----------------- Standing Orders -----------------
    private void loadStandingOrders(List<PersonalClient> personalClients,
                                    List<PersonalAccount> personalAccounts) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_STANDING))) {
            String line;
            boolean readingBills = false;
            boolean readingTransfers = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (line.contains("ΠΑΓΙΕΣ ΕΝΤΟΛΕΣ ΠΛΗΡΩΜΗΣ")) {
                    readingBills = true; readingTransfers = false; continue;
                }
                if (line.contains("ΠΑΓΙΕΣ ΕΝΤΟΛΕΣ ΜΕΤΑΦΟΡΑΣ")) {
                    readingBills = false; readingTransfers = true; continue;
                }

                if (readingBills && row.length >= 10) {
                    String ownerName = row[4].trim();
                    for (PersonalClient pc : personalClients) {
                        if (!pc.getFullName().equals(ownerName)) continue;
                        for (PersonalAccount acc : pc.getAccounts()) {
                            if (!acc.getIban().equals(row[9].trim())) continue;
                            StandingOrderBill bill = new StandingOrderBill(
                                    row[3].trim(), row[2].trim(), pc,
                                    Double.parseDouble(row[5].replace("€","").replace(",", "")),
                                    acc,
                                    LocalDate.parse(row[6].trim()),
                                    LocalDate.parse(row[7].trim()),
                                    Double.parseDouble(row[8].trim()),
                                    row[1].trim()
                            );
                            acc.addStandingOrder(bill);
                        }
                    }
                }

                if (readingTransfers && row.length >= 13) {
                    String ownerName = row[3].trim();
                    for (PersonalClient pc : personalClients) {
                        if (!pc.getFullName().equals(ownerName)) continue;
                        for (PersonalAccount acc : pc.getAccounts()) {
                            if (!acc.getIban().equals(row[8].trim())) continue;
                            StandingOrderTransfer transfer=null;
                            if(row[12].trim().equals("INTERNAL")){
                            	transfer = new StandingOrderTransferInternal(
                                        row[2].trim(), row[1].trim(), pc,
                                        Double.parseDouble(row[4].replace("€","").replace(",", "")),
                                        acc,
                                        LocalDate.parse(row[5].trim()),
                                        LocalDate.parse(row[6].trim()),
                                        Double.parseDouble(row[7].trim()),
                                        Integer.parseInt(row[10].trim()),
                                        Integer.parseInt(row[11].trim()),
                                        row[9].trim()
                                );
                            }else if(row[12].trim().equals("SEPA")) {
                            	transfer = new StandingOrderTransferSEPA(
                                        row[2].trim(), row[1].trim(), pc,
                                        Double.parseDouble(row[4].replace("€","").replace(",", "")),
                                        acc,
                                        LocalDate.parse(row[5].trim()),
                                        LocalDate.parse(row[6].trim()),
                                        Double.parseDouble(row[7].trim()),
                                        Integer.parseInt(row[10].trim()),
                                        Integer.parseInt(row[11].trim()),
                                        row[9].trim(),
                                        "code"
                                );
                            }else if(row[12].trim().equals("SWIFT")) {
                            	transfer = new StandingOrderTransferSWIFT(
                                        row[2].trim(), row[1].trim(), pc,
                                        Double.parseDouble(row[4].replace("€","").replace(",", "")),
                                        acc,
                                        LocalDate.parse(row[5].trim()),
                                        LocalDate.parse(row[6].trim()),
                                        Double.parseDouble(row[7].trim()),
                                        Integer.parseInt(row[10].trim()),
                                        Integer.parseInt(row[11].trim()),
                                        row[9].trim(),
                                        "code"
                                );
                            }
                            acc.addStandingOrder(transfer);
                        }
                    }
                }
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    // ----------------- Bills -----------------
    private void loadBills(List<PersonalClient> personalClients,
                           List<BusinessClient> businessClients,
                           List<Bill> bills) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_BILLS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("ΕΚΔΟΤΗΣ")) continue;

                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (row.length < 7) continue;

                BusinessClient issuer = businessClients.stream()
                        .filter(bc -> bc.getFullName().equals(row[0].trim()))
                        .findFirst().orElse(null);

                PersonalClient payer = personalClients.stream()
                        .filter(pc -> pc.getFullName().equals(row[3].trim()))
                        .findFirst().orElse(null);

                if (issuer == null || payer == null) continue;

                Bill bill = new Bill(
                        issuer,
                        row[2].trim(),
                        row[1].trim(),
                        payer,
                        Double.parseDouble(row[4].replace("€","").replace(",", "")),
                        LocalDate.parse(row[5].trim()),
                        LocalDate.parse(row[6].trim()),
                        "Utility Bill"
                );

                bills.add(bill);
                issuer.addBill(bill);
                payer.addBill(bill);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    


}
