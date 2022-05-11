package services.http.serialization;

import com.google.gson.*;
import tasks.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

import static services.http.adapters.LocalDateTimeAdapter.formatter;

public class AbstractTaskDeserializer implements JsonDeserializer<AbstractTask> {

    @Override
    public AbstractTask deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = new Gson();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        int id = jsonObject.get("id").getAsInt();

        Duration duration = Duration.ofMinutes(jsonObject.get("duration").getAsInt());
        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter);
        TaskStatus status = gson.fromJson(jsonObject.get("status").getAsString(), TaskStatus.class);

        AbstractTask newTask;
        TaskType taskType = TaskType.valueOf(jsonObject.get("type").getAsString());
        switch (taskType) {
            case TASK:
                newTask = new Task(name, description);
                break;
            case SUB_TASK:
                Epic parent = null;
                newTask = new SubTask(name, description, parent);
                break;
            case EPIC:
                newTask = new Epic(name, description);
                break;
            default:
                newTask = null;
        }

        if (newTask != null) {
            newTask.setId(id);
            newTask.setDuration(duration);
            newTask.setStartTime(startTime);
            newTask.setStatus(status);
            newTask.setType(taskType);
        }

        return newTask;
    }
}
