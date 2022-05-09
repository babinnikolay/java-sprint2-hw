package services.http.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import services.http.adapters.LocalDateTimeAdapter;
import tasks.SubTask;

import java.lang.reflect.Type;

public class SubTaskSerializer implements JsonSerializer<SubTask> {
    @Override
    public JsonElement serialize(SubTask subTask, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", subTask.getId());
        jsonObject.addProperty("name", subTask.getName());
        jsonObject.addProperty("status", subTask.getStatus().toString());
        jsonObject.addProperty("duration", subTask.getDuration().toMinutes());
        jsonObject.addProperty("startTime", subTask.getStartTime().format(LocalDateTimeAdapter.formatter));
        jsonObject.addProperty("parent", subTask.getParent().getId());
        return jsonObject;
    }
}
