package repositories;

import com.google.gson.Gson;
import repositories.services.TaskHistoryRepositoryService;
import services.http.util.GsonHelper;
import services.kv.KVTaskClient;

public class HTTPTaskHistoryRepository extends FileBackedTaskHistoryRepository{
    private final KVTaskClient client;
    private Gson gson;
    private static final String KV_KEY = "history";

    public HTTPTaskHistoryRepository(TaskHistoryRepositoryService service, KVTaskClient client) {
        super(service, null);
        gson = GsonHelper.getGson();
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
