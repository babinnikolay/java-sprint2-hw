package services.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import services.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler extends AbstractHandler{
    public HistoryHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        requestMethod = httpExchange.getRequestMethod();

        if (requestMethod.equals("GET")) {
            response = gson.toJson(taskManager.getHistoryManager().getHistory());
        }else {
            response = "incorrect method";
        }

        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }

    }
}
