import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDateTime;

public class HelloWebServer {
  public static void main(String[] args) throws IOException {
    // Create HTTP server on port 8000
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    HttpContext context = server.createContext("/");

    // Set the request handler for the server
    context.setHandler(exchange -> {
      // Retrieve the URI of the incoming request
      URI requestURI = exchange.getRequestURI();
      System.out.println("Server Received HTTP Request" + requestURI.toString());

      // Parse query parameters
      String query = requestURI.getRawQuery();
      MapADT<String, String> params = new PlaceholderMap<>();
      if (query != null) {
        for (String param : query.split("&")) {
          String[] keyValue = param.split("=");
          if (keyValue.length > 1) {
            params.put(keyValue[0], keyValue[1]);
          }
        }
      }

      // Construct the response message
      String name = params.containsKey("name") ? params.get("name").replace("%20", " ") : "User";
      String dateTime = LocalDateTime.now().toString();
      String response = "Hello " + name + "! <br/>I hope you are having a great " + dateTime;

      // Set response headers and status code
      Headers responseHeaders = exchange.getResponseHeaders();
      responseHeaders.add("Content-type", "text/html");
      exchange.sendResponseHeaders(200, response.length());

      // Write the response to the output stream
      OutputStream outStream = exchange.getResponseBody();
      outStream.write(response.getBytes());
      outStream.close();
    });

    // Start the server
    server.start();
    System.out.println("Hello Web Server Running...");
  }
}
