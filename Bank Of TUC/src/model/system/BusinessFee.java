package model.system;

import model.actors.BusinessClient;

public final class BusinessFee {

    public static double calculateMonthlyFee(BusinessClient client) {
        long billsLastMonth = client.getBills().stream()
            .filter(b -> b.isFromLastMonth())
            .count();

        if (billsLastMonth <= 10) return 5.00;
        if (billsLastMonth <= 50) return 10.00;
        return 20.00;
    }
}
