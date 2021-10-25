import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

// start this https://mkyong.com/spring-boot/spring-rest-spring-security-example/
public class HttpClientAuthentication {

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .authenticator(new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(
              "user",
              "password".toCharArray());
        }

      })
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://ec2-3-94-187-54.compute-1.amazonaws.com:8080/tcp_server_war/HelloWorldServlet/post"))
        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print status code
    System.out.println(response.statusCode());

    // print response body
    System.out.println(response.body());

  }

}