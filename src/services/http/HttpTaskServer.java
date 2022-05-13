package services.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import services.TaskManager;
import services.http.handlers.*;
import services.http.util.GsonHelper;
import util.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final TaskManager taskManagerService = Managers.getDefaultTaskManager();
    private static final int PORT = 8080;
    private HttpServer httpServer;
    private final Gson gson = GsonHelper.getGson();

    public HttpTaskServer() {

        try {
            httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(PORT), 0);
            TaskHandler taskHandler = new TaskHandler(taskManagerService, gson);
            httpServer.createContext("/tasks/task/", taskHandler);
            httpServer.createContext("/tasks/task", taskHandler);

            EpicHandler epicHandler = new EpicHandler(taskManagerService, gson);
            httpServer.createContext("/tasks/epic/", epicHandler);
            httpServer.createContext("/tasks/epic", epicHandler);

            SubTaskHandler subTaskHandler = new SubTaskHandler(taskManagerService, gson);
            httpServer.createContext("/tasks/subtask/", subTaskHandler);
            httpServer.createContext("/tasks/subtask", subTaskHandler);

            HistoryHandler historyHandler = new HistoryHandler(taskManagerService, gson);
            httpServer.createContext("/tasks/history/", historyHandler);
            httpServer.createContext("/tasks/history", historyHandler);

            TasksRootHandler tasksRootHandler = new TasksRootHandler(taskManagerService, gson);
            httpServer.createContext("/tasks/", tasksRootHandler);
            httpServer.createContext("/tasks", tasksRootHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TaskManager getTaskManagerService() {
        return taskManagerService;
    }

    public void start() {
        System.out.printf("server started at %d", PORT);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }
}
