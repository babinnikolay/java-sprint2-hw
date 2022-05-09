package repositories;

import com.google.gson.Gson;
import repositories.services.TaskRepositoryService;
import services.kv.KVTaskClient;


public class HTTPTaskRepository extends FileBackedTaskRepository{
    private KVTaskClient client;
    private Gson gson = new Gson();
    private static final String SERVICE_KEY = "service";

    public HTTPTaskRepository(TaskRepositoryService service, KVTaskClient client) {
        super(service, null);
        this.client = client;
    }

    @Override
    protected void save() {
        client.put(SERVICE_KEY, gson.toJson(service));
    }

    @Override
    public void loadFromPath() {
        String jsonService = client.load(SERVICE_KEY);
        service = gson.fromJson(jsonService, TaskRepositoryService.class);
        service.initPrioritizedTasks();
    }
}
