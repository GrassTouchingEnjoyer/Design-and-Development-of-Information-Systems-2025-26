package model.transfer;

import model.actors.PersonalAccount;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class SEPATransferExecution extends ExternalTransferExecution {

    private final String bic;

    public SEPATransferExecution(double fee, String bic) {
        super(fee);
        this.bic = bic;
    }

    @Override
    protected TransferResult sendExternal(PersonalAccount from, String toIban, double amount) {
        try {
            URL url = new URL("http://147.27.70.44:3020/transfer/sepa");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // φτιάχνουμε JSON με StringBuilder
            StringBuilder json = new StringBuilder();
            json.append("{")
                .append("\"amount\":").append(amount).append(",")
                .append("\"creditor\":{")
                    .append("\"name\":\"").append(from.getOwner().getFullName()).append("\",")
                    .append("\"iban\":\"").append(toIban).append("\"")
                .append("},")
                .append("\"creditorBank\":{")
                    .append("\"bic\":\"").append(bic).append("\",")
                    .append("\"name\":\"External Bank\"")
                .append("},")
                .append("\"execution\":{")
                    .append("\"requestedDate\":\"").append(LocalDate.now()).append("\",")
                    .append("\"charges\":\"").append("SHA").append("\"")
                .append("}")
            .append("}");

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes());
            }

            InputStream is = con.getResponseCode() < 400 ? con.getInputStream() : con.getErrorStream();
            String response = new String(is.readAllBytes());

            // απλή αναζήτηση status στο response
            if (response.contains("\"status\":\"success\"")) {
                return TransferResult.success("SEPA transfer completed successfully");
            } else {
                return TransferResult.failure("SEPA transfer failed ");
            }

        } catch (Exception e) {
            return TransferResult.failure("SEPA transfer exception: " + e.getMessage());
        }
    }
}




