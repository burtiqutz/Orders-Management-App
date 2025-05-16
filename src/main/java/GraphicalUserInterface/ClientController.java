package GraphicalUserInterface;

import BusinessLogic.ClientBLL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.Client;
import Util.TableViewBuilder;

/**
 * Controller class for Client UI.
 * Handles user interactions and updates the client table view.
 */

public class ClientController {

    @FXML private TextField clientNameField;
    @FXML private TextField clientAddressField;
    @FXML private TextField clientEmailField;
    @FXML private TableView<Client> clientTable;

    private final ClientBLL clientBLL = new ClientBLL();

    /**
     * Initializes the controller by loading the client data into the table.
     */
    @FXML
    public void initialize() {
        refreshClientTable();
    }

    /**
     * Handles adding a new client from input fields to the database and refreshes the table.
     */
    @FXML
    private void handleAddClient() {
        String name = clientNameField.getText();
        String address = clientAddressField.getText();
        String email = clientEmailField.getText();

        Client client = new Client(name, address, email);
        clientBLL.insertClient(client);
        refreshClientTable();
    }

    /**
     * Deletes the selected client from the database and refreshes the table.
     */
    @FXML
    private void handleDeleteClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            clientBLL.deleteClient(selectedClient.getId());
            refreshClientTable();
        }
    }

    /**
     * Updates the selected client with new data from input fields and refreshes the table.
     */
    @FXML
    private void handleUpdateClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            selectedClient.setName(clientNameField.getText());
            selectedClient.setAddress(clientAddressField.getText());
            selectedClient.setEmail(clientEmailField.getText());

            clientBLL.updateClient(selectedClient);
            refreshClientTable();
        }
    }


    /**
     * Refreshes the client table with the latest data from the database.
     */
    private void refreshClientTable() {
        TableViewBuilder.setupTable(clientTable, clientBLL.findAllClients(), Client.class);
    }

    /**
     * Handles the refresh button action to reload client data in the table.
     *
     * @param actionEvent the event triggered by user action.
     */
    public void handleRefreshClient(ActionEvent actionEvent) {
        refreshClientTable();
    }
}
