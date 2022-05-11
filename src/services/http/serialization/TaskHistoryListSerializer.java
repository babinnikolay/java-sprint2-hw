package services.http.serialization;

import com.google.gson.*;
import repositories.domain.TaskHistoryList;
import services.http.adapters.LocalDateTimeAdapter;
import tasks.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class TaskHistoryListSerializer implements JsonSerializer<TaskHistoryList>, JsonDeserializer<TaskHistoryList>{
    @Override
    public JsonElement serialize(TaskHistoryList taskHistoryList, Type type, JsonSerializationContext jsonSerializationContext) {
        List<AbstractTask> history = taskHistoryList.getHistory();
        JsonArray historyJson = jsonSerializationContext.serialize(history).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("history", historyJson);
        return jsonObject;
    }

    @Override
    public TaskHistoryList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        TaskHistoryList taskHistoryList = new TaskHistoryList(new HashMap<>());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("history")) {
            JsonElement history = jsonObject.get("history");
            for (JsonElement task : history.getAsJsonArray()) {
                JsonObject taskAsJsonObject = task.getAsJsonObject();
                AbstractTask newTask;
                String name = taskAsJsonObject.get("name").getAsString();
                String description = taskAsJsonObject.get("description").getAsString();
                switch (taskAsJsonObject.get("type").getAsString()) {
                    case "TASK":
                        newTask = new Task(name, description);
                        break;
                    case "Epic":
                        newTask = new Epic(name, description);
                        break;
                    case "SubTask":
                        newTask = new SubTask(name, description, null);
                        break;
                    default:
                        newTask = null;
                }
                if (newTask != null) {
                    newTask.setId(taskAsJsonObject.get("id").getAsInt());
                    newTask.setStatus(TaskStatus.valueOf(taskAsJsonObject.get("status").getAsString()));
                    newTask.setDuration(Duration.ofMinutes(taskAsJsonObject.get("duration").getAsInt()));
                    newTask.setStartTime(LocalDateTime.parse(taskAsJsonObject.get("startTime").getAsString(),
                            LocalDateTimeAdapter.formatter));
                }

                taskHistoryList.linkLast(newTask);
            }
        }
        return taskHistoryList;
    }
}
