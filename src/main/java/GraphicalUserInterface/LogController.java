package GraphicalUserInterface;

import BusinessLogic.BillBLL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import Model.Bill;
import Util.TableViewBuilder;

/**
 * Controller class for displaying and refreshing the Bill log table in the UI.
 */

public class LogController {

    @FXML private TableView<Bill> logTable;

    private final BillBLL billBLL = new BillBLL();

    /**
     * Initializes the controller by loading bill data into the log table.
     */
    @FXML
    public void initialize() {
        refreshLogTable();
    }

    /**
     * Refreshes the log table with the latest bills from the database.
     */
    private void refreshLogTable() {
        TableViewBuilder.setupTable(logTable, billBLL.findAllBills(), Bill.class);
    }

    /**
     * Handles the refresh button action to reload the bill log table.
     *
     * @param event the action event triggered by the user.
     */
    @FXML
    public void handleRefreshLog(ActionEvent event) {
        refreshLogTable();
    }
}
