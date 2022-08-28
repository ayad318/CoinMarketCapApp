package major_project.model.output;

public class DummyOutput implements OutputEngine {
    @Override
    public String PostImage(String image) {

        return "www.DUMMY.com";
    }

}
