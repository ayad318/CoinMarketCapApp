package major_project.view;

import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.model.Crypto;
import major_project.model.input.InputEngine;
import major_project.model.output.OutputEngine;

public class ConvertBox {
    private final InputEngine model;
    private final OutputEngine outputmodel;
    private final VBox box = new VBox();
    private final Label result = new Label();
    private final Hyperlink hyperlink = new Hyperlink();
    private final CryptoBoxView box1;
    private final CryptoBoxView box2;
    private final HostServices hostServices;
    private final TextField input;

    public ConvertBox(InputEngine model, OutputEngine outputmodel, HostServices hostServices) {
        this.outputmodel = outputmodel;
        this.model = model;
        this.hostServices = hostServices;
        box1 = new CryptoBoxView(model);
        box2 = new CryptoBoxView(model);
        input = new TextField();
        input.setPromptText("add value");
        buildBox();
        buildKeyListeners();

    }

    public void buildBox() {
        HBox convertLine = new HBox();
        convertLine.getChildren().addAll(input, box1.getBox(), new Label("------------>"), box2.getBox(),
                result);
        convertLine.setPadding(new Insets(30, 10, 5, 10));
        convertLine.setSpacing(40);
        convertLine.setAlignment(Pos.CENTER);
        HBox buttonsLine = new HBox();
        Button convertBtn = new Button("Convert");
        convertBtn.setOnAction((event) -> ConvertAction());
        Button reportBtn = new Button("Output Report");
        reportBtn.setOnAction((event) -> reportAction());
        buttonsLine.getChildren().addAll(convertBtn, reportBtn);
        buttonsLine.setAlignment(Pos.CENTER);
        buttonsLine.setPadding(new Insets(5, 10, 30, 10));
        buttonsLine.setSpacing(20);
        hyperlink.setPadding(new Insets(10, 10, 20, 10));
        box.getChildren().addAll(convertLine, buttonsLine, hyperlink);
        box.setAlignment(Pos.CENTER);

    }

    private void buildKeyListeners() {
        // This allows keyboard input. Note that the scene is used, so any time
        // the window is in focus the keyboard input will be registered.
        // More often, keyboard input is more closely linked to a specific
        // node that must have focus, i.e. the Enter key in a text input to submit
        // a form.

        box.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.X) {
                ConvertAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.R) {
                reportAction();
            }
        });
    }

    private void reportAction() {
        if (result.getText().length() == 0) {
            Alert alert = new Alert(AlertType.ERROR, "please convert currencies to output the QR code");
            alert.showAndWait();
        } else {

            String link = outputmodel.PostImage(
                    result.getText());
            hyperlink.setText(link);
            hyperlink.setOnAction((event) -> openLink(hyperlink.getText()));
        }
    }

    private void ConvertAction() {

        String in = input.getText();
        Crypto x = box1.getSelectedBox();
        Crypto y = box2.getSelectedBox();
        if (!isPositiveNumeric(in)) {
            Alert alert = new Alert(AlertType.ERROR, "Please Intput a Positive Number!!");
            alert.showAndWait();
        } else if (x == null || y == null) {
            Alert alert = new Alert(AlertType.ERROR, "Please select two Cryptocurrencies!!");
            alert.showAndWait();
        } else if (!model.isConversionRateValid(x, y)) {
            Alert alert = new Alert(AlertType.ERROR, "Large difference in currency values");
            alert.showAndWait();
        } else {
            float conversionRate = model.getConversionrate(x, y);
            float finishingValue = model.getFinishingvalue(Float.parseFloat(in), x, y);

            result.setText(x.toString()
                    + " | " + y.toString() + "\n" +
                    String.format("Conversion rate: %.4f\nStartingValue: %.4f\nFinishing Value: %.4f\n",
                            conversionRate,
                            Float.parseFloat(in),
                            finishingValue));

        }
    }

    private void openLink(String uri) {
        hostServices.showDocument(uri);
    }

    /**
     * @return the box
     */
    public VBox getBox() {
        return box;
    }

    // https://www.baeldung.com/java-check-string-number
    public static boolean isPositiveNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
            if (d < 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}