package major_project.view;

import javafx.application.HostServices;
import javafx.scene.layout.VBox;
import major_project.model.Crypto;
import major_project.model.Observer;
import major_project.model.input.InputEngine;

public class CryptoListView implements Observer {

    private final InputEngine model;
    private final HostServices hostServices;
    private final VBox list;

    public CryptoListView(InputEngine model, HostServices hostServices) {
        this.model = model;
        this.hostServices = hostServices;
        model.addObserver(this);
        list = new VBox();
        list.setSpacing(7);
    }

    @Override
    public void update() {
        buildList();

    }

    private void buildList() {
        list.getChildren().clear();

        for (Crypto crypto : model.getCryptoList()) {
            CryptoView item = new CryptoView(model, hostServices, crypto);
            list.getChildren().add(item.getCryptoView());
        }

    }

    public VBox getList() {
        return this.list;
    }

}
