package services.kv;

import exceptions.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private long apiKey;
    private final String serverURL;
    private HttpClient client;
    private HttpResponse.BodyHandler<String> defaultHandler = HttpResponse.BodyHandlers.ofString();

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

        HttpResponse<String> response;
        try {
            response = client.send(request, defaultHandler);
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException();
        }

        if (response.statusCode() != 200) {
            return;
        }

        apiKey = Long.parseLong(response.body());
    }

    public void put(String key, String json) {
//        POST /save/<ключ>?API_KEY=
        URI uri = URI.create(String.format("%s/save/%s?API_KEY=%d", serverURL, key, apiKey));

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();

        try {
            HttpResponse<String> response = client.send(request, defaultHandler);
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException();
        }
    }

    public String load(String key) {
//        GET /load/<ключ>?API_KEY=
        URI uri = URI.create(String.format("%s/load/%s?API_KEY=%d", serverURL, key, apiKey));

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, defaultHandler);
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException();
        }

        if (response == null || response.statusCode() != 200) {
            return null;
        }
        return response.body();
    }
}
