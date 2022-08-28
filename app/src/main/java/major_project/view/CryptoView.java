package major_project.view;

import javafx.application.HostServices;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.model.Crypto;
import major_project.model.input.InputEngine;

public class CryptoView {
    private final InputEngine model;
    private final HostServices hostServices;
    private final HBox cryptoView;
    private final Crypto crypto;

    public CryptoView(InputEngine model, HostServices hostServices, Crypto crypto) {
        this.crypto = crypto;
        this.hostServices = hostServices;
        this.model = model;
        this.cryptoView = new HBox(10);
        ImageView imageView = new ImageView(crypto.getLogo());
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
        cryptoView.getChildren().add(imageView);
        VBox firstSection = new VBox();
        firstSection.getChildren().add(new Label(crypto.getSymbol()));
        firstSection.getChildren().add(new Label(crypto.getName()));
        firstSection.setPrefWidth(90);
        cryptoView.getChildren().add(firstSection);
        Label description = new Label(crypto.getDescription());
        description.setWrapText(true);

        description.setPrefWidth(600);
        cryptoView.getChildren().add(description);
        Label date_launched = new Label(crypto.getDate_launched());
        date_launched.setPrefWidth(80);
        cryptoView.getChildren().add(date_launched);
        Hyperlink hyperlink = new Hyperlink(crypto.getUrls().getWebsite()[0]);
        hyperlink.setPrefWidth(160);
        hyperlink.setOnAction((event) -> openLink(hyperlink.getText()));
        cryptoView.getChildren().add(hyperlink);

        Button remove = new Button("Remove");
        remove.setPrefWidth(80);
        remove.setOnAction((event) -> RemoveCurrencyAction());
        cryptoView.getChildren().add(remove);

    }

    private void openLink(String uri) {
        hostServices.showDocument(uri);
    }

    private void RemoveCurrencyAction() {
        model.RemoveCrypto(crypto);
    }

    public HBox getCryptoView() {
        return this.cryptoView;
    }

}
