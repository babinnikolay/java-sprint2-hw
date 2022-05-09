package services.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import services.TaskManager;
import services.http.adapters.*;
import services.http.handlers.*;
import services.http.serialization.EpicDeserializer;
import services.http.serialization.SubTaskSerializer;
import tasks.Epic;
import tasks.SubTask;
import util.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static final TaskManager taskManagerService = Managers.getDefaultTaskManager();
    private static final int PORT = 8080;
    private HttpServer httpServer;
    private Gson gson;

    public HttpTaskServer() {

        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(TaskStatusAdapter.class, new TaskStatusAdapter())
                .registerTypeAdapter(SubTask.class, new SubTaskSerializer())
                .registerTypeAdapter(Epic.class, new EpicDeserializer(taskManagerService))
                .create();

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

    public void start() {
        System.out.printf("server started at %n", PORT);
        httpServer.start();
    }
}
