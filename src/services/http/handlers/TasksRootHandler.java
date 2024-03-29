package services.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import services.TaskManager;
import tasks.AbstractTask;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TasksRootHandler extends AbstractHandler {
    public TasksRootHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        requestMethod = httpExchange.getRequestMethod();

        String response;
        if (requestMethod.equals("GET")) {

            List<AbstractTask> abstractTaskList = new ArrayList<>();
            abstractTaskList.addAll(taskManager.getAllTasks());
            abstractTaskList.addAll(taskManager.getAllEpics());
            abstractTaskList.addAll(taskManager.getAllSubTasks());
            response = gson.toJson(abstractTaskList);
        } else {
            response = "incorrect method";
        }

        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
