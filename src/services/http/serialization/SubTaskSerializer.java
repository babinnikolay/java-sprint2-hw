package services.http.serialization;

import com.google.gson.*;
import services.http.adapters.LocalDateTimeAdapter;
import tasks.Epic;
import tasks.SubTask;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubTaskSerializer implements JsonSerializer<SubTask>, JsonDeserializer<SubTask> {
    @Override
    public JsonElement serialize(SubTask subTask, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", subTask.getId());
        jsonObject.addProperty("name", subTask.getName());
        jsonObject.addProperty("description", subTask.getDescription());
        jsonObject.addProperty("status", subTask.getStatus().toString());
        jsonObject.addProperty("duration", subTask.getDuration().toMinutes());
        jsonObject.addProperty("startTime", subTask.getStartTime().format(LocalDateTimeAdapter.formatter));
        jsonObject.addProperty("parent", subTask.getParent().getId());
        return jsonObject;
    }

    @Override
    public SubTask deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        SubTask subTask;
        Epic epic = new Epic("e", "e");

        if (jsonObject.has("parent")) {
            epic.setId(jsonObject.get("parent").getAsInt());
        }

        subTask = new SubTask(jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString(), epic);

        if (jsonObject.has("id")) {
            subTask.setId(jsonObject.get("id").getAsInt());
        }

        Duration duration;
        if (!jsonObject.has("duration")) {
            duration = Duration.ZERO;
        } else {
            duration = Duration.ofMinutes(jsonObject.get("duration").getAsInt());
        }
        subTask.setDuration(duration);

        LocalDateTime startTime;
        if (!jsonObject.has("startTime")) {
            startTime = LocalDateTime.now();
        } else {
            startTime =
                    LocalDateTime.parse(jsonObject.get("startTime").getAsString(), LocalDateTimeAdapter.formatter);
        }
        subTask.setStartTime(startTime);

        return subTask;
    }
}
