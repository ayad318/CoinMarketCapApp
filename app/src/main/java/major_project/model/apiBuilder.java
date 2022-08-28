package major_project.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class apiBuilder {

    private static String apiKey = System.getenv("INPUT_API_KEY");
    private static String ClientId = System.getenv("IMGUR_API_KEY");

    // create a get Api Call for Coinmarketcap
    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        // from https://coinmarketcap.com/api/documentation/v1/

        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);

            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }

    // create a post call for Imgur
    public static String makePostAPICall(String uri, List<NameValuePair> parameters, File file)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("Authorization", "Client-ID " + ClientId);

        // https://stackoverflow.com/questions/6917105/java-http-client-to-upload-file-over-post
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // fileParamName should be replaced with parameter name your REST API expect.
        builder.addPart("image", new FileBody(file));
        // builder.addPart("optionalParam", new StringBody("true",
        // ContentType.create("text/plain", Consts.ASCII)));
        request.setEntity(builder.build());

        CloseableHttpResponse response = client.execute(request);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);

            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }

}