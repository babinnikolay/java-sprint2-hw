package services.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import services.TaskManager;
import services.http.util.QueryHelper;
import tasks.Epic;

import java.io.IOException;
import java.io.OutputStream;

public class EpicHandler extends AbstractHandler {

    public EpicHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        requestMethod = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        int id;

        switch (requestMethod) {
            case "GET":
                if (query == null) {
                    response = gson.toJson(taskManager.getAllEpics());
                } else {
                    id = QueryHelper.getIdFromQuery(query);
                    response = gson.toJson(taskManager.getEpicById(id));
                }
                break;
            case "POST":
                id = QueryHelper.getIdFromQuery(query);
                inputStream = httpExchange.getRequestBody();
                body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Epic newEpic = gson.fromJson(body, Epic.class);
                newEpic.setId(id);
                if (newEpic.getId() == 0) {
                    taskManager.createEpic(newEpic);
                    response = "epic created";
                } else {
                    taskManager.updateEpic(newEpic);
                    response = "epic updated";
                }
                break;
            case "DELETE":
                if (query == null) {
                    taskManager.removeAllEpics();
                    response = "all epics removed";
                } else {
                    id = QueryHelper.getIdFromQuery(query);
                    taskManager.deleteEpicById(id);
                    response = String.format("epic %d deleted", id);
                }
                break;
            default:
                response = "incorrect method";
        }

        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
