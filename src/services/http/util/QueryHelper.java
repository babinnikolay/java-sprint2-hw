package services.http.util;

public class QueryHelper {
    public static int getIdFromQuery(String query) {
        int id = 0;
        if (query == null) {
            return id;
        }
        String[] params = query.split("&");
        String[] param = params[0].split("=");
        if (param[0].equals("id")) {
            id = Integer.parseInt(param[1]);
        }
        return id;
    }

    private QueryHelper() {

    }
}
