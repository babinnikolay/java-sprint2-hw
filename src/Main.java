
import services.kv.KVServer;
import services.kv.KVTaskClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println("Пришло время практики!");
//        new HttpTaskServer().start();

        new KVServer().start();
        KVTaskClient client = new KVTaskClient("http://localhost:8078");
        client.put("myKey", "myValue");
        System.out.println(client.load("myKey"));
        client.put("myKey", "new Value");
        System.out.println(client.load("myKey"));

    }
}
