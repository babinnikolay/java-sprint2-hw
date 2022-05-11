package repositories;

import com.google.gson.Gson;
import repositories.services.TaskRepositoryService;
import services.http.util.GsonHelper;
import services.kv.KVTaskClient;


public class HTTPTaskRepository extends FileBackedTaskRepository{
    private KVTaskClient client;

    private Gson gson = GsonHelper.getGson();

    private static final String KV_KEY = "service";

    public HTTPTaskRepository(TaskRepositoryService service, KVTaskClient client) {
        super(service, null);
        this.client = client;
    }

    @Override
    protected void save() {
        client.put(KV_KEY, gson.toJson(service));
    }

    @Override
    public void loadFromPath() {
        String jsonService = client.load(KV_KEY);
        service = gson.fromJson(jsonService, TaskRepositoryService.class);
        service.initPrioritizedTasks();
    }
}
