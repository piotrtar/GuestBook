package www.guestform.com;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import www.guestform.com.controllers.MimeTypeResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class Static implements HttpHandler{

    @Override

    public void handle(HttpExchange httpExchange) throws IOException {

        String root = "src/main/resources";
        URI uri = httpExchange.getRequestURI();

        String path = uri.getPath();
        File file = new File(root + path).getCanonicalFile();

        MimeTypeResolver mtr = new MimeTypeResolver(file);
        String mime = mtr.getMimeType();

        Headers h = httpExchange.getResponseHeaders();
        h.set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();
        FileInputStream fs = new FileInputStream(file);
        byte[] buffer = new byte[0x10000];
        int count = 0;
        while((count = fs.read(buffer)) >= 0) {
            os.write(buffer,0,count);
        }
        fs.close();
        os.close();

    }
}
