package services.kv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private long API_KEY;
    private String serverURL;
    private HttpClient client;
    private HttpResponse.BodyHandler<String> defaultHandler = HttpResponse.BodyHandlers.ofString();
    private HttpResponse<String> response;

    public KVTaskClient(String serverURL) {
        this.serverURL = serverURL;
        connect();
    }

    private void connect() {
        URI uri = URI.create(serverURL + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        client = HttpClient.newHttpClient();

        try {
            response = client.send(request, defaultHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        if (response.statusCode() != 200) {
            return;
        }

        API_KEY = Long.parseLong(response.body());
    }

    public void put(String key, String json) {
//        POST /save/<ключ>?API_KEY=
        URI uri = URI.create(String.format("%s/save/%s?API_KEY=%d", serverURL, key, API_KEY));

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();

        try {
            response = client.send(request, defaultHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String load(String key) {
//        GET /load/<ключ>?API_KEY=
        URI uri = URI.create(String.format("%s/load/%s?API_KEY=%d", serverURL, key, API_KEY));

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = client.send(request, defaultHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            return null;
        }

        return response.body();
    }
}
