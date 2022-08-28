package major_project.view;

// import java.nio.file.Paths;
import java.util.Arrays;

import javafx.application.HostServices;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;
// import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import major_project.model.Observer;
import major_project.model.ToAddCurrency;
import major_project.model.ToAddData;
import major_project.model.input.InputEngine;
import major_project.model.output.OutputEngine;

public class MainView implements Observer {
    private final InputEngine model;
    private final OutputEngine outputmodel;
    private final BorderPane pane = new BorderPane();;
    private final Scene scene = new Scene(pane, 1280, 720);
    private final VBox sideButtonBar = new VBox();
    private final CryptoListView cryptoListView;
    private final HostServices hostServices;
    private final ThresholdWindow thresholdWindow;
    // private final MediaPlayer mediaPlayer;
    private ConvertBox convertBox;

    public MainView(InputEngine model, OutputEngine outputmodel, HostServices hostServices) {
        this.outputmodel = outputmodel;
        this.model = model;
        this.hostServices = hostServices;
        model.addObserver(this);
        cryptoListView = new CryptoListView(model, hostServices);

        // String s = "src/main/resources/music.wav";
        // Media h = new Media(Paths.get(s).toUri().toString());
        // mediaPlayer = new MediaPlayer(h);
        // PlayStopMusicAction();

        // buildKeyListeners();
        buildSideButtons();
        this.thresholdWindow = new ThresholdWindow(model, pane, sideButtonBar, cryptoListView);
        // pane.setLeft(sideButtonBar);
        // pane.setCenter(cryptoListView.getList());
        pane.setCenter(thresholdWindow.getBox());
    }

    public Scene getScene() {
        return this.scene;
    }

    // public void music(MediaPlayer mediaPlayer) {
    // String s = "src/main/resources/music.mp3";
    // Media h = new Media(Paths.get(s).toUri().toString());
    // mediaPlayer = new MediaPlayer(h);
    // mediaPlayer.mediaPlayer.play();
    // mediaPlayer.setAutoPlay(true);

    // }

    private void buildSideButtons() {
        Button AddCurrencyBtn = new Button("Add Currency");
        AddCurrencyBtn.setOnAction((event) -> AddCurrencyAction());
        AddCurrencyBtn.setPrefWidth(120);

        Button ClearListBtn = new Button("Clear List");
        ClearListBtn.setOnAction((event) -> ClearCryptoAction());
        ClearListBtn.setPrefWidth(120);

        // Button PlayStopMusicBtn = new Button("Play/Stop Music");
        // PlayStopMusicBtn.setOnAction((event) -> PlayStopMusicAction());
        // PlayStopMusicBtn.setPrefWidth(120);

        this.sideButtonBar.getChildren().addAll(AddCurrencyBtn, ClearListBtn);
        sideButtonBar.setSpacing(10);
        sideButtonBar.setPrefWidth(140);

    }

    // private void buildKeyListeners() {
    // // took from task3
    // // This allows keyboard input. Note that the scene is used, so any time
    // // the window is in focus the keyboard input will be registered.
    // // More often, keyboard input is more closely linked to a specific
    // // node that must have focus, i.e. the Enter key in a text input to submit
    // // a form.

    // scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    // if (event.isControlDown() && event.getCode() == KeyCode.A) {
    // AddCurrencyAction();
    // }
    // if (event.isControlDown() && event.getCode() == KeyCode.C) {
    // ClearCryptoAction();
    // }

    // });
    // }

    private void buildConvertBox() {
        if (model.getCryptoList().size() >= 2) {
            convertBox = new ConvertBox(model, outputmodel, hostServices);
            pane.setBottom(convertBox.getBox());
        } else {
            pane.setBottom(null);
        }
    }

    private void ClearCryptoAction() {
        model.ClearCrypto();
    }

    private void AddCurrencyAction() {
        ToAddData cyptoList = model.getCryptoCurrencies();

        ListView<ToAddCurrency> toAddCrytoListView = new ListView<ToAddCurrency>();
        Stage newWindow = new Stage();
        toAddCrytoListView.getItems().addAll(Arrays.asList(cyptoList.getData()));
        toAddCrytoListView.setOnMouseClicked(event -> {

            ToAddCurrency currentItemSelected;

            if (event.getClickCount() == 2) {
                // Use ListView's getSelected Item
                currentItemSelected = (ToAddCurrency) toAddCrytoListView.getSelectionModel()
                        .getSelectedItem();
                newWindow.close();
                model.AddCrypto(currentItemSelected.getId());

            }

        });
        Scene secondScene = new Scene(toAddCrytoListView, 400, 800);

        // New window (Stage)

        newWindow.setTitle("Select Wanted Coin");
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    // public void PlayStopMusicAction() {
    // if (mediaPlayer.getStatus() != Status.PLAYING) {
    // mediaPlayer.play();
    // mediaPlayer.setAutoPlay(true);
    // mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    // } else {
    // mediaPlayer.pause();
    // }
    // }

    @Override
    public void update() {
        cryptoListView.update();
        buildConvertBox();
    }

}
