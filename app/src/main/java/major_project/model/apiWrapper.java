package major_project.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class apiWrapper {

    // call get Crypto list from coin market
    public ToAddData getCryptoCurrencies() {

        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("start", "1"));
        ToAddData item;
        try {
            String result = apiBuilder.makeAPICall(uri, paratmers);

            Gson gson = new Gson();
            item = gson.fromJson(result, ToAddData.class);

            return item;
        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return null;
    }

    // get crypto metadata from coin market
    public Crypto getCryptoMetaData(int id) {
        String uri = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/info";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("id", Integer.toString(id)));
        Crypto item;
        try {
            String result = apiBuilder.makeAPICall(uri, paratmers);

            Gson gson = new Gson();

            JsonObject json = gson.fromJson(result, JsonObject.class);
            JsonObject json2 = gson.fromJson(json.get("data"), JsonObject.class);
            item = gson.fromJson(json2.get(Integer.toString(id)), Crypto.class);
            System.out.println(item);
            return item;

        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return null;
    }

    // get quote of certain crpyto and convert to another
    public float getCryptoPrice(int id, int id2) {
        String uri = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("id", Integer.toString(id)));
        paratmers.add(new BasicNameValuePair("convert_id", Integer.toString(id2)));
        try {
            String result = apiBuilder.makeAPICall(uri, paratmers);

            Gson gson = new Gson();

            JsonObject json = gson.fromJson(result, JsonObject.class);
            json = gson.fromJson(json.get("data"), JsonObject.class);
            json = gson.fromJson(json.get(Integer.toString(id)), JsonObject.class);
            json = gson.fromJson(json.get("quote"), JsonObject.class);
            json = gson.fromJson(json.get(Integer.toString(id2)), JsonObject.class);

            return (float) Float.parseFloat(json.get("price").toString());

        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return -1;
    }

    // call post for imgur and get the link where it's posted
    public String postImage(String path) {
        String uri = "https://api.imgur.com/3/image";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();

        paratmers.add(new BasicNameValuePair("type", "file"));

        File file = new File(path);
        try {
            String result = apiBuilder.makePostAPICall(uri, paratmers, file);

            Gson gson = new Gson();

            JsonObject json = gson.fromJson(result, JsonObject.class);
            json = gson.fromJson(json.get("data"), JsonObject.class);

            return json.get("link").getAsString();

        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return "";
    }
}
