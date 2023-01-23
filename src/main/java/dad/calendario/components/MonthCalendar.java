package dad.calendario.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MonthCalendar extends GridPane implements Initializable {

    private static final String[] MONTH_NAMES = {
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
    };

    @FXML
    private Label monthNameLabel;

    @FXML
    private GridPane root;

    private final IntegerProperty monthProperty = new SimpleIntegerProperty();
    private final IntegerProperty yearProperty = new SimpleIntegerProperty();

    public MonthCalendar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CalendarLayout.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        yearProperty.addListener(this::onYearChanged);
        monthProperty.addListener(this::onMonthChanged);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    private void onYearChanged(ObservableValue<? extends Number> o, Number ov, Number nv) {
        if(nv != null) {
            changeMonth(nv.intValue(), monthProperty.get());
        }
    }

    private void onMonthChanged(ObservableValue<? extends Number> o, Number ov, Number nv) {
        if(nv != null) {
            monthNameLabel.setText(MONTH_NAMES[nv.intValue()]);
            changeMonth(yearProperty.get(), nv.intValue());
        }
    }

    private void changeMonth(int year, int month) {
        LocalDate initial = LocalDate.of(year, month + 1, 1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));

        int currentDay = 1;
        for (int i = 0; i < 35; i++) {
            Label dayLabel = (Label) root.getChildren().get(i + 8);

            if (i < initial.getDayOfWeek().getValue() || currentDay > end.getDayOfMonth()) {
                dayLabel.setText("");
                continue;
            }

            dayLabel.setText("" + currentDay);
            currentDay++;
        }
    }

    public final IntegerProperty monthPropertyProperty() {
        return this.monthProperty;
    }

    public final int getMonthProperty() {
        return this.monthPropertyProperty().get();
    }

    public final void setMonthProperty(final int monthProperty) {
        this.monthPropertyProperty().set(monthProperty);
    }

    public final IntegerProperty yearPropertyProperty() {
        return this.yearProperty;
    }

}
