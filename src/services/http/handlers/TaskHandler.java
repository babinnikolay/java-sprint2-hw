package services.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import services.TaskManager;
import services.http.util.QueryHelper;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;


public class TaskHandler extends AbstractHandler {

    public TaskHandler(TaskManager taskManager, Gson gson) {
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
                    response = gson.toJson(taskManager.getAllTasks());
                } else {
                    id = QueryHelper.getIdFromQuery(query);
                    response = gson.toJson(taskManager.getTaskById(id));
                }
                break;
            case "POST":
                id = QueryHelper.getIdFromQuery(query);
                inputStream = httpExchange.getRequestBody();
                body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Task newTask = gson.fromJson(body, Task.class);
                newTask.setId(id);
                if (newTask.getId() == 0) {
                    taskManager.createTask(newTask);
                    response = "task created";
                } else {
                    taskManager.updateTask(newTask);
                    response = "task updated";
                }
                break;
            case "DELETE":
                if (query == null) {
                    taskManager.removeAllTasks();
                    response = "all tasks removed";
                } else {
                    id = QueryHelper.getIdFromQuery(query);
                    taskManager.deleteTaskById(id);
                    response = String.format("task %d deleted", id);

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
