package dad.calendario;

import dad.calendario.components.MonthCalendar;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @FXML
    GridPane root;

    @FXML
    Label yearLabel;

    @FXML
    Button previousButton;

    @FXML
    Button nextButton;

    private final IntegerProperty yearProperty = new SimpleIntegerProperty();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Calendario.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.load();
        root.getStylesheets().add(getClass().getResource("/css/calendario.css").toExternalForm());
        Scene scene = new Scene(root);
        stage.setTitle("Calendario");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();

        yearProperty.set(2022);

        for (int i = 0; i < 12; i++) {
            MonthCalendar monthCalendar = (MonthCalendar) root.getChildren().get(i);

            monthCalendar.setMonthProperty(i);
            monthCalendar.yearPropertyProperty().bind(yearProperty);
        }

        yearLabel.textProperty().bind(yearProperty.asString());

        previousButton.setOnAction(this::onPreviousButtonClick);
        nextButton.setOnAction(this::onNextButtonClick);
    }

    private void onPreviousButtonClick(ActionEvent event) {
        yearProperty.set(yearProperty.get() - 1);
    }

    private void onNextButtonClick(ActionEvent event) {
        yearProperty.set(yearProperty.get() + 1);
    }

    public static void main(String[] args) {
        launch();
    }

}