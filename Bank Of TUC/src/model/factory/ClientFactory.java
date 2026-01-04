package model.factory;

import model.actors.BusinessClient;
import model.actors.Client;
import model.actors.PersonalClient;

public class ClientFactory {

    public static Client createClient(String type, String username, String password, 
                                          String fullName, String afm, String phone) {
        switch (type.toUpperCase()) {
            case "PERSONAL":
            case "Φ.ΠΡΟΣΩΠΟ":
                return new PersonalClient(username, password, fullName, afm, phone);
            case "BUSINESS":
            case "Ν.ΠΡΟΣΩΠΟ":
                return new BusinessClient(username, password, fullName, afm, phone);
            default:
                throw new IllegalArgumentException("Unknown client type: " + type);
        }
    }
}

