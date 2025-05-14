package graphicalUserInterface;

import businessLogic.OrdersBLL;
import businessLogic.BillBLL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Bill;
import util.TableViewBuilder;

public class LogController {

    @FXML private TableView<Bill> logTable;

    private final BillBLL billBLL = new BillBLL();

    @FXML
    public void initialize() {
        refreshLogTable();
    }

    private void refreshLogTable() {
        TableViewBuilder.setupTable(logTable, billBLL.findAllBills(), Bill.class);
    }
    public void handleRefreshLog(ActionEvent event) {
        refreshLogTable();
    }
}
