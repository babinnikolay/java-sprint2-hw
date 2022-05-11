package services.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import services.TaskManager;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class AbstractHandler implements HttpHandler {
    protected static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    protected TaskManager taskManager;
    protected Gson gson;
    protected String response = "";
    protected String body = "";
    protected InputStream inputStream;
    protected String requestMethod;

    protected AbstractHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }


}
