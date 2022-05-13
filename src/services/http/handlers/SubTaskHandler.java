package services.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import services.TaskManager;
import services.http.util.QueryHelper;
import tasks.SubTask;

import java.io.IOException;
import java.io.OutputStream;

public class SubTaskHandler extends AbstractHandler{
    public SubTaskHandler(TaskManager taskManagerService, Gson gson) {
        super(taskManagerService, gson);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        requestMethod = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        int id;

        String response;
        switch (requestMethod) {
            case "GET":
                if (query == null) {
                    response = gson.toJson(taskManager.getAllSubTasks());
                } else {
                    id = QueryHelper.getIdFromQuery(query);
                    response = gson.toJson(taskManager.getSubTaskById(id));
                }
                break;
            case "POST":
                id = QueryHelper.getIdFromQuery(query);
                inputStream = httpExchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                SubTask newSubTask = gson.fromJson(body, SubTask.class);
                newSubTask.setParent(taskManager.getEpicById(newSubTask.getParent().getId()));
                newSubTask.setId(id);
                if (newSubTask.getId() == 0) {
                    taskManager.createSubTask(newSubTask);
                    response = "subTask created";
                } else {
                    taskManager.updateSubTask(newSubTask);
                    response = "subTask updated";
                }
                break;
            case "DELETE":
                if (query == null) {
                    taskManager.removeAllSubTasks();
                    response = "all subtasks removed";
                } else {
                    id = QueryHelper.getIdFromQuery(query);
                    taskManager.deleteSubTaskById(id);
                    response = String.format("subTask %d deleted", id);
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
