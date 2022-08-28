package major_project.view;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.model.input.InputEngine;

public class ThresholdWindow {
    private final InputEngine model;

    private final VBox box = new VBox();
    private final Label text = new Label("enter a threshold value between 0.1 and 1.0");
    private final TextField input = new TextField();
    private final BorderPane pane;
    private final VBox sideButtonBar;
    private final CryptoListView list;

    public ThresholdWindow(InputEngine model, BorderPane pane, VBox sideButtonBar, CryptoListView list) {
        this.model = model;
        this.pane = pane;
        this.sideButtonBar = sideButtonBar;
        this.list = list;
        input.setPromptText("Enter Value between 0.1 and 1.0");
        buildbox();
    }

    void buildbox() {
        HBox numberBox = new HBox();
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction((event) -> ConfirmAction(input.getText()));
        numberBox.getChildren().addAll(input, confirmBtn);
        numberBox.setAlignment(Pos.CENTER);
        numberBox.setSpacing(40);
        box.getChildren().addAll(text, numberBox);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);

    }

    void ConfirmAction(String x) {
        if (isValid(x)) {
            model.setThreshold(Float.parseFloat(x));
            Label t = new Label("Threshold:  " + x);

            sideButtonBar.getChildren().add(t);
            pane.setLeft(sideButtonBar);
            pane.setCenter(list.getList());
        } else {
            Alert alert = new Alert(AlertType.ERROR,
                    "input: " + x + "\nplease Enter a valid numeric number between 0.1 and 1.0");
            alert.showAndWait();
        }
    }

    public static boolean isValid(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
            if (d < 0.1 || d > 1.0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * @return the box
     */
    public VBox getBox() {
        return box;
    }
}
