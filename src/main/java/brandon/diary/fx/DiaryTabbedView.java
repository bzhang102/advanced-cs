package brandon.diary.fx;

import java.io.IOException;
import java.text.ParseException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiaryTabbedView extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(100);

        Tab tab1 = new Tab("To Do", new ToDoView());
        Tab tab2 = new Tab("Reminders", new ReminderView());
        Tab tab3 = new Tab("Contacts", new ContactView());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.setTitle("My Digital Dairy");
        stage.setWidth(810);
        stage.show();
    }
    public static void main(String[] args)
    {
        Application.launch(args);
    }

}
