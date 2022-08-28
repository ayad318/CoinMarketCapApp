package major_project.model.output;

import major_project.model.apiWrapper;

public class mainOutputEngine implements OutputEngine {

    private final apiWrapper api;

    public mainOutputEngine(apiWrapper api) {
        this.api = api;
    }

    @Override
    public String PostImage(String image) {
        if (image != null) {
            String path = QRcode.createQR(image);

            return api.postImage(path);
        }
        return null;

    }
}
