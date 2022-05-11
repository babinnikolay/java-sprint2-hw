package services.http.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import repositories.domain.TaskHistoryList;
import services.http.adapters.DurationAdapter;
import services.http.adapters.LocalDateTimeAdapter;
import services.http.adapters.TaskStatusAdapter;
import services.http.serialization.*;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.SubTask;

import java.time.Duration;
import java.time.LocalDateTime;

public class GsonHelper {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(TaskStatusAdapter.class, new TaskStatusAdapter())
            .registerTypeAdapter(SubTask.class, new SubTaskSerializer())
            .registerTypeAdapter(Epic.class, new EpicSerializer())
            .registerTypeAdapter(AbstractTask.class, new AbstractTaskDeserializer())
            .registerTypeAdapter(TaskHistoryList.class, new TaskHistoryListSerializer())
            .create();

    private GsonHelper() {}

    public static Gson getGson() {
        return gson;
    }
}
