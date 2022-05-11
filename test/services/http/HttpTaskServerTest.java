package services.http;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.http.util.GsonHelper;
import services.kv.KVServer;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {

    private HttpTaskServer httpTaskServer;
    private KVServer kvServer;

    private Gson gson;

    private URI uri;

    private HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private HttpResponse.BodyHandler<String> stringBodyHandler = HttpResponse.BodyHandlers.ofString();

    private HttpResponse<String> response;

    private HttpRequest request;

    @BeforeEach
    public void setUp() {
        try {
            kvServer = new KVServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        kvServer.start();

        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();

        gson = GsonHelper.getGson();
    }

    @AfterEach
    public void setDown() {
        httpTaskServer.stop();
        kvServer.stop();
    }

    @Test
    public void shouldReturnJsonWhenGetAllTasks() {
        uri = URI.create("http://localhost:8080/tasks/task");

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10));

        createAbstractTaskOnHTTP(task);
        task.setId(1);
        List<Task> list = List.of(task);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldReturnJsonWhenGetAllEpics() {
        uri = URI.create("http://localhost:8080/tasks/epic");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());

        epic.updateStatus();

        createAbstractTaskOnHTTP(epic);
        epic.setId(1);
        List<Epic> list = List.of(epic);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldReturnJsonWhenGetAllSubTasks() {
        uri = URI.create("http://localhost:8080/tasks/subtask");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());
        epic.updateStatus();

        httpTaskServer.getTaskManagerService().createEpic(epic);

        SubTask subTask = new SubTask("name", "desc", epic);
        subTask.setStartTime(LocalDateTime.now());
        subTask.setDuration(Duration.ofMinutes(0));

        createAbstractTaskOnHTTP(subTask);
        subTask.setId(2);
        List<SubTask> list = List.of(subTask);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldReturnJsonWhenGetOneTask() {
        uri = URI.create("http://localhost:8080/tasks/task");

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10));

        createAbstractTaskOnHTTP(task);
        task.setId(1);

        uri = URI.create("http://localhost:8080/tasks/task?id=1");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(task), response.body());
    }

    @Test
    public void shouldReturnJsonWhenGetOneEpic() {
        uri = URI.create("http://localhost:8080/tasks/epic");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());

        epic.updateStatus();

        createAbstractTaskOnHTTP(epic);
        epic.setId(1);

        uri = URI.create("http://localhost:8080/tasks/epic?id=1");

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(epic), response.body());
    }

    @Test
    public void shouldReturnJsonWhenGetOneSubTask() {
        uri = URI.create("http://localhost:8080/tasks/subtask");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());
        epic.updateStatus();

        httpTaskServer.getTaskManagerService().createEpic(epic);

        SubTask subTask = new SubTask("name", "desc", epic);
        subTask.setStartTime(LocalDateTime.now());
        subTask.setDuration(Duration.ofMinutes(0));

        subTask.setId(2);
        createAbstractTaskOnHTTP(subTask);

        uri = URI.create("http://localhost:8080/tasks/subtask?id=2");

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(subTask), response.body());
    }

    @Test
    public void shouldReturnUpdatedJsonWhenUpdateOneTask() {
        uri = URI.create("http://localhost:8080/tasks/task");

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10));

        createAbstractTaskOnHTTP(task);
        task.setId(1);

        task.setName("new name");
        task.setDescription("new desc");

        uri = URI.create("http://localhost:8080/tasks/task?id=1");
        createAbstractTaskOnHTTP(task);
        String json = gson.toJson(task);


        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(task, gson.fromJson(response.body(), Task.class));
    }

    @Test
    public void shouldReturnUpdatedJsonWhenUpdateOneEpic() {
        uri = URI.create("http://localhost:8080/tasks/epic");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());

        epic.updateStatus();

        createAbstractTaskOnHTTP(epic);
        epic.setId(1);

        epic.setName("new name");
        epic.setDescription("new desc");

        uri = URI.create("http://localhost:8080/tasks/epic?id=1");
        createAbstractTaskOnHTTP(epic);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(epic.getName(), gson.fromJson(response.body(), Epic.class).getName());
        assertEquals(epic.getDescription(), gson.fromJson(response.body(), Epic.class).getDescription());
    }

    @Test
    public void shouldReturnUpdatedJsonWhenUpdateOneSubTask() {
        uri = URI.create("http://localhost:8080/tasks/subtask");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());
        epic.updateStatus();

        httpTaskServer.getTaskManagerService().createEpic(epic);

        SubTask subTask = new SubTask("name", "desc", epic);
        subTask.setStartTime(LocalDateTime.now());
        subTask.setDuration(Duration.ofMinutes(0));

        createAbstractTaskOnHTTP(subTask);
        subTask.setId(2);

        uri = URI.create("http://localhost:8080/tasks/subtask?id=2");

        subTask.setName("new name");
        subTask.setDescription("new desc");

        createAbstractTaskOnHTTP(subTask);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(subTask), response.body());
    }

    @Test
    public void shouldRemoveAllTasksWhenDeleteAllTasks() {
        uri = URI.create("http://localhost:8080/tasks/task");

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10));

        createAbstractTaskOnHTTP(task);
        task.setId(1);
        List<Task> list = new ArrayList<>();

        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldRemoveAllEpicsWhenDeleteAllEpics() {
        uri = URI.create("http://localhost:8080/tasks/epic");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());

        epic.updateStatus();

        createAbstractTaskOnHTTP(epic);
        epic.setId(1);
        List<Epic> list = new ArrayList<>();

        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldRemoveAllSubTasksWhenDeleteAllSubTasks() {
        uri = URI.create("http://localhost:8080/tasks/subtask");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());
        epic.updateStatus();

        httpTaskServer.getTaskManagerService().createEpic(epic);

        SubTask subTask = new SubTask("name", "desc", epic);
        subTask.setStartTime(LocalDateTime.now());
        subTask.setDuration(Duration.ofMinutes(0));

        createAbstractTaskOnHTTP(subTask);
        subTask.setId(2);
        List<SubTask> list = new ArrayList<>();

        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldRemoveTaskWhenDeleteTaskById() {
        uri = URI.create("http://localhost:8080/tasks/task");

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10));

        createAbstractTaskOnHTTP(task);
        task.setId(1);

        Task task2 = new Task("name", "desc");
        task2.setStartTime(LocalDateTime.now().plusMinutes(20));
        task2.setDuration(Duration.ofMinutes(10));

        createAbstractTaskOnHTTP(task2);
        task2.setId(2);

        List<Task> list = List.of(task2);

        uri = URI.create("http://localhost:8080/tasks/task?id=1");
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        uri = URI.create("http://localhost:8080/tasks/task");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldRemoveEpicWhenDeleteEpicById() {
        uri = URI.create("http://localhost:8080/tasks/epic");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());

        epic.updateStatus();

        createAbstractTaskOnHTTP(epic);
        epic.setId(1);

        Epic epic2 = new Epic("name", "desc");
        epic2.setStartTime(LocalDateTime.now());
        epic2.setDuration(Duration.ofMinutes(0));
        epic2.setEndTime(LocalDateTime.now());

        epic2.updateStatus();

        createAbstractTaskOnHTTP(epic2);
        epic2.setId(2);

        List<Epic> list = List.of(epic2);

        uri = URI.create("http://localhost:8080/tasks/epic?id=1");
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        uri = URI.create("http://localhost:8080/tasks/epic");

        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());

    }

    @Test
    public void shouldRemoveSubTaskWhenDeleteSubTaskById() {
        uri = URI.create("http://localhost:8080/tasks/subtask");

        Epic epic = new Epic("name", "desc");
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(0));
        epic.setEndTime(LocalDateTime.now());
        epic.updateStatus();

        httpTaskServer.getTaskManagerService().createEpic(epic);

        SubTask subTask = new SubTask("name", "desc", epic);
        subTask.setStartTime(LocalDateTime.now());
        subTask.setDuration(Duration.ofMinutes(0));

        createAbstractTaskOnHTTP(subTask);
        subTask.setId(2);

        SubTask subTask2 = new SubTask("name", "desc", epic);
        subTask2.setStartTime(LocalDateTime.now());
        subTask2.setDuration(Duration.ofMinutes(0));

        createAbstractTaskOnHTTP(subTask2);
        subTask2.setId(3);

        List<SubTask> list = List.of(subTask2);

        uri = URI.create("http://localhost:8080/tasks/subtask?id=2");
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        uri = URI.create("http://localhost:8080/tasks/subtask");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, response.statusCode());
        assertEquals(gson.toJson(list), response.body());
    }

    @Test
    public void shouldReturnIncorrectMethodWhenIncorrectMethod() {
        uri = URI.create("http://localhost:8080/tasks");
        request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString("json"))
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("incorrect method", response.body());
    }

    private void createAbstractTaskOnHTTP(AbstractTask task) {
        String json = gson.toJson(task);
        request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();

        try {
            response = httpClient.send(request, stringBodyHandler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}