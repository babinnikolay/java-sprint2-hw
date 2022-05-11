package services.http.serialization;

import com.google.gson.*;
import services.http.adapters.LocalDateTimeAdapter;
import tasks.AbstractTask;
import tasks.Epic;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EpicSerializer implements JsonSerializer<Epic>, JsonDeserializer<Epic> {

    @Override
    public Epic deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Epic newEpic = new Epic(jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString());

        int id = 0;
        if (jsonObject.has("id")) {
            id = jsonObject.get("id").getAsInt();
        }
        newEpic.setId(id);

        Duration duration;
        if (!jsonObject.has("duration")) {
            duration = Duration.ZERO;
        } else {
            duration = Duration.ofMinutes(jsonObject.get("duration").getAsInt());
        }
        newEpic.setDuration(duration);

        LocalDateTime startTime;
        if (!jsonObject.has("startTime")) {
            startTime = LocalDateTime.now();
        } else {
            startTime =
                    LocalDateTime.parse(jsonObject.get("startTime").getAsString(), LocalDateTimeAdapter.formatter);
        }
        newEpic.setStartTime(startTime);

        LocalDateTime endTime;
        if (!jsonObject.has("endTime")) {
            endTime = LocalDateTime.now();
        } else {
            endTime =
                    LocalDateTime.parse(jsonObject.get("endTime").getAsString(), LocalDateTimeAdapter.formatter);
        }
        newEpic.setEndTime(endTime);

        newEpic.updateStatus();


        return newEpic;
    }

    @Override
    public JsonElement serialize(Epic epic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", epic.getId());
        jsonObject.addProperty("name", epic.getName());
        jsonObject.addProperty("description", epic.getDescription());
        jsonObject.addProperty("status", epic.getStatus().toString());
        jsonObject.addProperty("duration", epic.getDuration().toMinutes());
        jsonObject.addProperty("startTime", epic.getStartTime().format(LocalDateTimeAdapter.formatter));
        jsonObject.addProperty("endTime", epic.getEndTime().format(LocalDateTimeAdapter.formatter));
        jsonObject.addProperty("subTasks", Arrays.toString(epic.getSubTasks().stream().mapToInt(AbstractTask::getId).toArray()));
        return jsonObject;
    }
}
