package Util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Stream;
/**
 * Utility class to build and populate JavaFX TableView dynamically
 * based on the fields of a given model class.
 */
public class TableViewBuilder {
    /**
     * Sets up the TableView columns and populates it with data.
     * Columns are created for each declared field of the given type.
     *
     * @param tableView the TableView to setup
     * @param data      the list of data items to display
     * @param type      the class type of the data items
     * @param <T>       the type parameter of the data
     */
    public static <T> void setupTable(TableView<T> tableView, List<T> data, Class<T> type) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        Stream.of(type.getDeclaredFields())
                .forEach(field -> {
                    TableColumn<T, Object> column = new TableColumn<>(field.getName());
                    column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                    column.setPrefWidth(120);
                    tableView.getColumns().add(column);
                });

        tableView.getItems().addAll(data);
    }
}
