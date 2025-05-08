package graphicalUserInterface;

import dataAccess.ClientDAO;
import model.Client;

public class Test {
    public static void main(String[] args) {
        ClientDAO dao = new ClientDAO();
        Client existing = dao.findById(6);
        existing.setAddress("cluj-napoca observatorului");
        dao.update(existing);

        for (Client c : dao.findAll()) {
            System.out.println(c);
        }
    }
}
