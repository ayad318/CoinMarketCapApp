package major_project.view;

import javafx.scene.control.ComboBox;
import major_project.model.Crypto;
import major_project.model.input.InputEngine;

public class CryptoBoxView {
    private final ComboBox<Crypto> box = new ComboBox<Crypto>();

    public CryptoBoxView(InputEngine model) {
        box.getItems().addAll(model.getCryptoList());
        box.setPromptText("Select Crypto");
    }

    /**
     * @return the box
     */
    public ComboBox<Crypto> getBox() {
        return box;
    }

    public Crypto getSelectedBox() {
        System.out.println(box.getValue());
        return (Crypto) box.getValue();
    }

}
