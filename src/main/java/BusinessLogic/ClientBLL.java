package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;

import java.util.List;
/**
 * Business Logic Layer for Client operations.
 * Validates data and delegates database actions to ClientDAO.
 */

public class ClientBLL {
    private final ClientDAO clientDAO = new ClientDAO();

    /**
     * Inserts a new client after validating the client's name is not empty.
     *
     * @param client the Client to insert.
     * @throws IllegalArgumentException if client name is null or empty.
     */
    public void insertClient(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty");
        }
        clientDAO.insert(client);
    }

    /**
     * Updates an existing client in the database.
     *
     * @param client the Client with updated information.
     */
    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    /**
     * Deletes a client by its ID.
     *
     * @param id the ID of the client to delete.
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * Finds a client by its ID.
     *
     * @param id the client ID.
     * @return the Client if found, otherwise null.
     */

    public Client findClientById(int id) {
        return clientDAO.findById(id);
    }

    /**
     * Retrieves all clients.
     *
     * @return a List of all Clients.
     */

    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Resets the auto-increment counter for the clients table.
     */

    public void resetClientAutoIncrement() {
        clientDAO.resetAutoIncrement();
    }
}
