package major_project.model;

public class ToAddData {
    ToAddCurrency[] data;

    public ToAddData(ToAddCurrency[] data) {
        this.data = data;
    }

    public ToAddCurrency[] getData() {
        return this.data;
    }

    public void setData(ToAddCurrency[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String res = "";
        for (ToAddCurrency i : data) {
            res += i.toString() + "\n";
        }

        return res;
    }

}