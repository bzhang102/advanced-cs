package brandon.dl.fx;

import brandon.dl.model.DriverLicense;
import brandon.dl.service.CsvFileService;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

public class DriverLicenseTableView extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        CsvFileService service = new CsvFileService();

        // get data from csv
        ObservableList<DriverLicense> data = FXCollections.observableArrayList(service.readCsvFile());

        // create scene
        Scene scene = new Scene(new Group());
        stage.setTitle("Driver License Management System");
        stage.setWidth(2000);
        stage.setHeight(1000);

        final Label label = new Label("Driver License Management System");
        label.setFont(new Font("Arial", 20));

        // create all the columns
        TableColumn dlNumberCol = new TableColumn("DL Number");
        dlNumberCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("dlNumber"));
        dlNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dlNumberCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDlNumber(t.getNewValue());
                    }
                }
        );


        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    }
                }
        );

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    }
                }
        );

        TableColumn middleNameCol = new TableColumn("Middle Name");
        middleNameCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("middleName"));
        middleNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        middleNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMiddleName(t.getNewValue());
                    }
                }
        );

        TableColumn genderCol = new TableColumn("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("gender"));
        genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genderCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setGender(t.getNewValue());
                    }
                }
        );

        TableColumn addressCol = new TableColumn("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAddress(t.getNewValue());
                    }
                }
        );

        TableColumn visionCol = new TableColumn("Vision");
        visionCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("Vision"));
        visionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        visionCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVision(t.getNewValue());
                    }
                }
        );

        TableColumn dobCol = new TableColumn("DOB");
        dobCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, LocalDate>("dob"));
        dobCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                DatePickerCell datePick = new DatePickerCell(data, "dob");

                return datePick;
            }
        });

        TableColumn expirationCol = new TableColumn("Expiration");
        expirationCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, LocalDate>("expiration"));
        expirationCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                DatePickerCell datePick = new DatePickerCell(data, "expiration");

                return datePick;
            }
        });

        TableColumn pointCol = new TableColumn("Points");
        pointCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, Integer>("Points"));
        pointCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        pointCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, Integer> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPoints(t.getNewValue());

                        t.getTableView().refresh();
                    }
                }
        );

        TableColumn suspendedCol = new TableColumn("Suspended");
        suspendedCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, Boolean>("suspended"));
        suspendedCol.setCellFactory(CheckBoxTableCell.forTableColumn(suspendedCol));
        suspendedCol.setEditable(false);

        TableColumn underAgeCol = new TableColumn("Under Age");
        underAgeCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, Boolean>("underAge"));
        underAgeCol.setCellFactory(CheckBoxTableCell.forTableColumn(underAgeCol));
        underAgeCol.setEditable(false);

        TableColumn expiredCol = new TableColumn("Expired");
        expiredCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, Boolean>("expired"));
        expiredCol.setCellFactory(CheckBoxTableCell.forTableColumn(expiredCol));
        expiredCol.setEditable(false);

        TableColumn donorCol = new TableColumn("Donor");
        donorCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, Boolean>("donor"));
        donorCol.setCellFactory(CheckBoxTableCell.forTableColumn(donorCol));

        TableColumn pictureCol = new TableColumn("Picture Location");
        pictureCol.setCellValueFactory(new PropertyValueFactory<DriverLicense, String>("pictureLocation"));
        pictureCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pictureCol.setOnEditCommit(
                new EventHandler<CellEditEvent<DriverLicense, String>>() {
                    @Override
                    public void handle(CellEditEvent<DriverLicense, String> t) {
                        ((DriverLicense) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPictureLocation(t.getNewValue());
                    }
                }
        );

        // create tableview and add data
        TableView<DriverLicense> table = new TableView<DriverLicense>();
        table.setEditable(true);
        table.setItems(data);

        // add columns to table
        table.getColumns().addAll(dlNumberCol, firstNameCol, lastNameCol, middleNameCol, genderCol, addressCol,
                visionCol, dobCol, expirationCol, pointCol, suspendedCol, underAgeCol, expiredCol, donorCol, pictureCol);

        // create filter
        final Label filterLabel = new Label("Filter");
        filterLabel.setFont(new Font("Arial", 18));
        final TextField filterField = new TextField();
        ObservableList items =  table.getItems();
        filterField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(items);
            }
            String value = newValue.toLowerCase();
            ObservableList<DriverLicense> subentries = FXCollections.observableArrayList();

            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
            table.setItems(subentries);
        });

        // show expired only
        final Button expiredOnlyButton = new Button("Expired Only");
        expiredOnlyButton.setOnAction(e -> {
            ObservableList<DriverLicense> subentries = FXCollections.observableArrayList();

            for (DriverLicense dl : table.getItems()) {
                if (dl.isExpired()) {
                    subentries.add(dl);
                }
            }

            table.setItems(subentries);
        });

        // show suspended only
        final Button suspendedOnlyButton = new Button("Suspended Only");
        suspendedOnlyButton.setOnAction(e -> {
            ObservableList<DriverLicense> subentries = FXCollections.observableArrayList();

            for (DriverLicense dl : table.getItems()) {
                if (dl.isSuspended()) {
                    subentries.add(dl);
                }
            }

            table.setItems(subentries);
        });

        // show all data
        final Button allButton = new Button("All");
        allButton.setOnAction(e -> {
            filterField.clear();
            table.setItems(data);
        });

        // add DL
        final TextField addDlNumber = new TextField();
        addDlNumber.setPromptText("Dl Number");
        addDlNumber.setMaxWidth(dlNumberCol.getPrefWidth());
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setPromptText("Last Name");
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        final TextField addMiddleName = new TextField();
        addMiddleName.setPromptText("Mid Name");
        addMiddleName.setMaxWidth(middleNameCol.getPrefWidth());
        final TextField addGender = new TextField();
        addGender.setPromptText("Gender");
        addGender.setMaxWidth(genderCol.getPrefWidth());
        final TextField addAddress = new TextField();
        addAddress.setPromptText("Address");
        addAddress.setMaxWidth(addressCol.getPrefWidth());
        final TextField addVision = new TextField();
        addVision.setPromptText("Vision");
        addVision.setMaxWidth(visionCol.getPrefWidth());
        final DatePicker addDob = new DatePicker();
        addDob.setPromptText("DOB");
        final DatePicker addExpiration = new DatePicker();
        addExpiration.setPromptText("Expiration");
        final TextField addPoints = new TextField();
        addPoints.setPromptText("Points");
        addPoints.setMaxWidth(pointCol.getPrefWidth());
        final TextField addDonor = new TextField();
        addDonor.setPromptText("Donor Y/N");
        addDonor.setMaxWidth(donorCol.getPrefWidth());
        final TextField addPicture = new TextField();
        addPicture.setPromptText("Picture Location");
        addPicture.setMaxWidth(pictureCol.getPrefWidth() + 50);

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
                DriverLicense dl = new DriverLicense();
                dl.setDlNumber(addDlNumber.getText());
                dl.setFirstName(addFirstName.getText());
                dl.setLastName(addLastName.getText());
                dl.setMiddleName(addMiddleName.getText());
                dl.setGender(addGender.getText());
                dl.setAddress(addAddress.getText());
                dl.setVision(addVision.getText());

                try {
                    dl.setDob(addDob.getValue());
                } catch (Exception ee) {
                    dl.setDob(LocalDate.of(1900, 1, 1));
                }

                try {
                    dl.setExpiration(addExpiration.getValue());
                } catch (Exception ee) {
                    dl.setExpiration(LocalDate.of(1900, 1, 1));
                }

                try {
                    dl.setPoints(Integer.parseInt(addPoints.getText()));
                } catch (Exception ee) {
                    dl.setPoints(-1);
                }
                dl.setDonor("Y".equalsIgnoreCase(addDonor.getText()));
                dl.setPictureLocation(addPicture.getText());

                data.add(dl);
                table.setItems(data);

                addDlNumber.clear();
                addFirstName.clear();
                addLastName.clear();
                addMiddleName.clear();
                addGender.clear();
                addAddress.clear();
                addVision.clear();
                addPoints.clear();
                addDonor.clear();
                addPicture.clear();
                addDob.getEditor().clear();
                addExpiration.getEditor().clear();
        });

        // delete DL
        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            DriverLicense selectedItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedItem);
            data.remove(selectedItem);
        });

        // generate DL
        final Button generateButton = new Button("Generate");
        generateButton.setOnAction(e -> {
            DriverLicense dl = table.getSelectionModel().getSelectedItem();

            if (dl == null) return;

            try {
                // picture
                Image image = new Image(new FileInputStream(dl.getPictureLocation()));
                final ImageView dlPicture = new ImageView();
                dlPicture.setFitHeight(200);
                dlPicture.setFitWidth(150);
                dlPicture.setImage(image);
                final VBox imageVbox = new VBox();
                imageVbox.getChildren().addAll(dlPicture);

                // data
                final Label dlLable = new Label("DL: " + dl.getDlNumber());
                final Label expLabel = new Label("EXP: " + dl.getExpiration());
                final Label lnLabel = new Label("LN: " + dl.getLastName());
                final Label fnLabel = new Label("LN: " + dl.getFirstName());
                final Label addLabel = new Label(dl.getAddress());
                final Label dobLabel = new Label("DOB: " + dl.getDob());
                final VBox dataVbox = new VBox();
                dataVbox.setPrefWidth(300);
                dataVbox.setSpacing(10);
                dataVbox.setPadding(new Insets(10, 0, 0, 10));
                dataVbox.getChildren().addAll(dlLable, expLabel, lnLabel, fnLabel, addLabel, dobLabel);

                // everything all together
                HBox hbox = new HBox();
                hbox.getChildren().addAll(imageVbox, dataVbox);
                hbox.setSpacing(30);

                Stage dlStage = new Stage();
                dlStage.setTitle("Driver License");
                Scene dlScene = new Scene(new Group());
                ((Group) dlScene.getRoot()).getChildren().addAll(hbox);
                dlStage.setWidth(450);
                dlStage.setHeight(240);
                dlStage.setScene(dlScene);
                dlStage.show();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
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

        final HBox hb1 = new HBox();
        hb1.getChildren().addAll(filterLabel, filterField, expiredOnlyButton, suspendedOnlyButton, allButton);
        hb1.setSpacing(20);

        // put everything on screen
        final HBox hb2 = new HBox();
        hb2.getChildren().addAll(addDlNumber, addFirstName, addLastName, addMiddleName, addGender,
                addAddress, addVision, addDob, addExpiration, addPoints, addDonor, addPicture,
                addButton, deleteButton, generateButton, saveButton);
        hb2.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setPrefWidth(1600);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, hb1, table, hb2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}

