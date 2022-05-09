package services.http.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import tasks.TaskStatus;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TaskStatusAdapter extends TypeAdapter<TaskStatus> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void write(JsonWriter jsonWriter, TaskStatus status) throws IOException {
        jsonWriter.value(status.name());
    }

    @Override
    public TaskStatus read(JsonReader jsonReader) throws IOException {
        return TaskStatus.valueOf(jsonReader.nextString());
    }
}
