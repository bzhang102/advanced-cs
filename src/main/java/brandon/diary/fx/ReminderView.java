package brandon.diary.fx;

import brandon.diary.model.Reminder;
import brandon.diary.service.ReminderCsvFileService;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ReminderView extends VBox {
    public ReminderView() throws IOException, ParseException {
        ReminderCsvFileService service = new ReminderCsvFileService();

        // get data from csv
        ObservableList<Reminder> data = FXCollections.observableArrayList(service.readCsvFile());

        // create all the columns
        TableColumn eventCol = new TableColumn("Event");
        eventCol.setPrefWidth(300);
        eventCol.setCellValueFactory(new PropertyValueFactory<Reminder, String>("event"));
        eventCol.setCellFactory(TextFieldTableCell.forTableColumn());
        eventCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Reminder, String>>() {
                @Override
                public void handle(CellEditEvent<Reminder, String> t) {
                    ((Reminder) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                    ).setEvent(t.getNewValue());
                }
            }
        );

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setPrefWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<Reminder, LocalDate>("date"));
        dateCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                DatePickerCell datePick = new DatePickerCell(data, "date");
                return datePick;
            }
        });

        TableColumn startCol = new TableColumn("Start");
        startCol.setPrefWidth(75);
        startCol.setCellValueFactory(new PropertyValueFactory<Reminder, String>("start"));
        startCol.setCellFactory(TextFieldTableCell.forTableColumn());
        startCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Reminder, String>>() {
                    @Override
                    public void handle(CellEditEvent<Reminder, String> t) {
                        ((Reminder) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setStart(t.getNewValue());
                    }
                }
        );
//
//        TableColumn endCol = new TableColumn("");
//        startCol.setPrefWidth(150);
//        startCol.setCellValueFactory(new PropertyValueFactory<Reminder, LocalDate>("due"));
//        startCol.setCellFactory(new Callback<TableColumn, TableCell>() {
//            @Override
//            public TableCell call(TableColumn tableColumn) {
//                DatePickerCell datePick = new DatePickerCell(data, "Due");
//                return datePick;
//            }
//        });

        TableColumn completedCol = new TableColumn("Completed");
        completedCol.setCellValueFactory(new PropertyValueFactory<Reminder, Boolean>("completed"));
        completedCol.setCellFactory(CheckBoxTableCell.forTableColumn(completedCol));

        // create tableview and add data
        TableView<Reminder> table = new TableView<Reminder>();
        table.setEditable(true);
        table.setItems(data);

        // add columns to table
        table.getColumns().addAll(eventCol, dateCol, startCol, completedCol);

        table.setRowFactory(tv -> new TableRow<Reminder>() {
            @Override
            public void updateItem(Reminder item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (!item.isCompleted() && !item.getDate().isAfter(LocalDate.now())) {
                    setStyle("-fx-background-color: tomato;");
                } else {
                    setStyle("");
                }
            }
        });

        // create filter
        final Label filterLabel = new Label("Search");
        //filterLabel.setFont(new Font("Arial", 12));
        final TextField filterField = new TextField();
        ObservableList items = table.getItems();
        filterField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(items);
            }
            String value = newValue.toLowerCase();
            ObservableList<Reminder> subEntries = FXCollections.observableArrayList();

            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subEntries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
            table.setItems(subEntries);
        });

        // show all data
        final Button allButton = new Button("All");
        allButton.setOnAction(e -> {
            filterField.clear();
            table.setItems(data);
        });

        // add reminder
        final TextField addEvent = new TextField();
        addEvent.setPromptText("Description");
        addEvent.setPrefWidth(eventCol.getPrefWidth());
        addEvent.setMaxWidth(eventCol.getPrefWidth());
        final DatePicker addDate = new DatePicker();
        addDate.setPrefWidth(dateCol.getPrefWidth());
        addDate.setMaxWidth(dateCol.getPrefWidth());
        addDate.setPromptText("Date");
        final TextField addStart = new TextField();
        addStart.setPrefWidth(startCol.getPrefWidth());
        addStart.setMaxWidth(startCol.getPrefWidth());
        addStart.setPromptText("Start");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            Reminder reminder = new Reminder();
            reminder.setEvent(addEvent.getText());
            if (addDate.getValue() == null) {
                reminder.setDate(LocalDate.of(2000, 1, 1));
            } else {
                try {
                    reminder.setDate(addDate.getValue());
                    System.out.println(reminder.getDate());
                } catch (Exception ee) {
                    System.out.println("setDate() to default 1900");
                    reminder.setDate(LocalDate.of(1900, 1, 1));
                }
            }
            reminder.setCompleted(false);

            data.add(reminder);
            table.setItems(data);

            addEvent.clear();
            addDate.getEditor().clear();
        });

        // delete reminder
        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Reminder selectedItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedItem);
            data.remove(selectedItem);
        });

        // save to CSV
        final Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                service.writeCsvFile(table.getItems());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });

        // refresh
        final Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            table.refresh();
        });

        final HBox hb1 = new HBox();
        hb1.getChildren().addAll(filterLabel, filterField, allButton);
        hb1.setSpacing(20);

        // put everything on screen
        final HBox hb2 = new HBox();
        hb2.getChildren().addAll(addEvent, addDate, addStart,
            addButton, deleteButton, saveButton, refreshButton);
        hb2.setSpacing(3);

        this.setPrefWidth(800);
        this.setSpacing(5);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.getChildren().addAll(hb1, table, hb2);
    }
}
