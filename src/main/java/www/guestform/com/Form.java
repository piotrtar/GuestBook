package www.guestform.com;

import www.guestform.com.controllers.GuestBookController;
import www.guestform.com.Dao.GuestBookDAO;
import www.guestform.com.model.GuestBookRecord;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Form implements HttpHandler{

//    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")) {

            GuestBookController gbc = new GuestBookController();
            String records = gbc.getRecordsAsHTML();

            response = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\"/>\n" +
                            "    <title> Codecool Quest Store </title>\n" +
                            "    <meta name=\"description\" content=\"Codecool Quest Storeo\"/>\n" +
                            "    <link rel=\"stylesheet\" href=\"static/css/style.css\" type=\"text/css\"/>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <header>\n" +
                            "        <h1>Guest Book</h1>\n" +
                            "    </header>\n" +
                            "    <div id=\"form-container\">\n" +
                            "        <form method=\"POST\">\n" +
                            "            <h2>Name</h2>\n" +
                            "            <input type=\"text\" id=\"full-name\" name=\"name\" placeholder=\"Marian Paździoch\">\n" +
                            "            <h2>Message</h2>\n" +
                            "            <textarea typeof=\"text\" name=\"message\" id=\"text-area\" placeholder=\"Your message\"></textarea>\n" +
                            "            <br/>\n" +
                            "            <input id=\"button\" type=\"submit\" value=\"Submit\">\n" +
                            "        </form>\n" +
                            "    </div>\n" +
                            "    <div id=\"records-container\">\n" +
                                    records +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>";
        }

        if(method.equals("POST")) {

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map inputs = parseFormData(formData);

            String name = inputs.get("name").toString();
            String message = inputs.get("message").toString();

            GuestBookRecord record = new GuestBookRecord(name, getActualDateTime(), message);
            GuestBookDAO.addNewRecord(record);

            GuestBookController gbc = new GuestBookController();
            String records = gbc.getRecordsAsHTML();

            response = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\"/>\n" +
                            "    <title> Codecool Quest Store </title>\n" +
                            "    <meta name=\"description\" content=\"Codecool Quest Storeo\"/>\n" +
                            "    <link rel=\"stylesheet\" href=\"static/css/style.css\" type=\"text/css\"/>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <header>\n" +
                            "        <h1>Guest Book</h1>\n" +
                            "    </header>\n" +
                            "    <div id=\"form-container\">\n" +
                            "        <form method=\"POST\">\n" +
                            "            <h2>Name</h2>\n" +
                            "            <input type=\"text\" id=\"full-name\" name=\"name\" placeholder=\"Marian Paździoch\">\n" +
                            "            <h2>Message</h2>\n" +
                            "            <textarea typeof=\"text\" name=\"message\" id=\"text-area\" placeholder=\"Your message\"></textarea>\n" +
                            "            <br/>\n" +
                            "            <input id=\"button\" type=\"submit\" value=\"Submit\">\n" +
                            "        </form>\n" +
                            "    </div>\n" +
                            "    <div id=\"records-container\">\n" +
                                    records +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>";
        }

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }


    private static String getActualDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        return dateString;
    }
}
