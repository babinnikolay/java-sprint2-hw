package services.http.serialization;

import com.google.gson.*;
import services.TaskManager;
import tasks.Epic;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class EpicDeserializer implements JsonDeserializer<Epic> {
    private TaskManager taskManagerService;

    public EpicDeserializer(TaskManager taskManagerService) {
        this.taskManagerService = taskManagerService;
    }

    @Override
    public Epic deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Epic newEpic;
        if (jsonObject.get("id") == null) {
            newEpic = new Epic(jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString());
            if (jsonObject.get("id") == null) {
                newEpic.setDuration(Duration.ZERO);
                newEpic.setStartTime(LocalDateTime.MIN);
                newEpic.setEndTime(LocalDateTime.MIN);
                newEpic.updateStatus();
            }
            if (jsonObject.get("subTasks") != null) {

            }
        } else {
            newEpic = taskManagerService.getEpicById(jsonObject.get("id").getAsInt());
            if (jsonObject.has("name")) {
                newEpic.setName(jsonObject.get("name").getAsString());
            }
            if (jsonObject.has("description")) {
                newEpic.setDescription(jsonObject.get("description").getAsString());
            }
        }
        return newEpic;
    }
}
