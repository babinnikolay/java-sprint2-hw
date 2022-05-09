package repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import repositories.services.TaskHistoryRepositoryService;
import services.http.adapters.DurationAdapter;
import services.http.adapters.LocalDateTimeAdapter;
import services.http.serialization.AbstractTaskDeserializer;
import services.http.serialization.EpicDeserializer;
import services.http.serialization.SubTaskSerializer;
import services.kv.KVTaskClient;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.SubTask;

import java.time.Duration;
import java.time.LocalDateTime;

public class HTTPTaskHistoryRepository extends FileBackedTaskHistoryRepository{
    private KVTaskClient client;
    private Gson gson;
    private static final String KV_KEY = "history";

    public HTTPTaskHistoryRepository(TaskHistoryRepositoryService service, KVTaskClient client) {
        super(service, null);
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(AbstractTask.class, new AbstractTaskDeserializer())
                .registerTypeAdapter(SubTask.class, new SubTaskSerializer())
                .create();
        this.client = client;
    }

    @Override
    protected void save() {
        client.put(KV_KEY, gson.toJson(service));
    }

    @Override
    public void loadFromPath() {
        String jsonService = client.load(KV_KEY);
        service = gson.fromJson(jsonService, TaskHistoryRepositoryService.class);
    }
}
