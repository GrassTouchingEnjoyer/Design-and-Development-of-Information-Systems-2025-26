package model.transfer;

import model.actors.PersonalAccount;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SWIFTTransferExecution extends ExternalTransferExecution {

    private final String swiftCode;
    

    public SWIFTTransferExecution(double fee, String swiftCode) {
        super(fee);
        this.swiftCode = swiftCode;
        
    }

    @Override
    protected TransferResult sendExternal(PersonalAccount from, String toIban, double amount) {
        try {
            URL url = new URL("http://147.27.70.44:3020/transfer/swift");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            StringBuilder json = new StringBuilder();
            json.append("{")
                .append("\"currency\":\"").append("EUR").append("\",")
                .append("\"amount\":").append(amount).append(",")
                .append("\"beneficiary\":{")
                    .append("\"name\":\"").append("External Beneficiary").append("\",")
                    .append("\"address\":\"Unknown\",")
                    .append("\"account\":\"").append(toIban).append("\"")
                .append("},")
                .append("\"beneficiaryBank\":{")
                    .append("\"name\":\"External Bank\",")
                    .append("\"swiftCode\":\"").append(swiftCode).append("\",")
                    .append("\"country\":\"UNKNOWN\"")
                .append("},")
                .append("\"fees\":{")
                    .append("\"chargingModel\":\"").append("SHA").append("\"")
                .append("},")
                .append("\"correspondentBank\":{")
                    .append("\"required\": false")
                .append("}")
            .append("}");

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes());
            }

            InputStream is = con.getResponseCode() < 400 ? con.getInputStream() : con.getErrorStream();
            String response = new String(is.readAllBytes());

            if (response.contains("\"status\":\"success\"")) {
                return TransferResult.success("SWIFT transfer completed successfully");
            } else {
                return TransferResult.failure("SWIFT transfer failed ");
            }

        } catch (Exception e) {
            return TransferResult.failure("SWIFT transfer exception: " + e.getMessage());
        }
    }
}

