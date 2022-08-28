package major_project.model;

// crpyto class
public class Crypto {
    int id;
    String logo;
    String name;
    String symbol;
    String description;
    String date_launched;
    URLS urls;

    public Crypto(int id, String logo, String name, String symbol, String description, String date_launched,
            URLS urls) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.date_launched = date_launched;
        this.urls = urls;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public URLS getUrls() {
        return this.urls;
    }

    public void setUrls(URLS urls) {
        this.urls = urls;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_launched() {
        return this.date_launched;
    }

    public void setDate_launched(String date_launched) {
        this.date_launched = date_launched;
    }

    @Override
    public String toString() {
        return getName() + "(" + getSymbol() + ")";
    }

}
