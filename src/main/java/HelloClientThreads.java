import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class HelloClientThreads {

  // custom executor
  private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .executor(executorService)
      .version(HttpClient.Version.HTTP_2)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws Exception {

    List<URI> targets = Arrays.asList(
        new URI("http://ec2-3-94-187-54.compute-1.amazonaws.com:8080/tcp_server_war/HelloWorldServlet/1"),
        new URI("http://ec2-3-94-187-54.compute-1.amazonaws.com:8080/tcp_server_war/HelloWorldServlet/2"),
        new URI("http://ec2-3-94-187-54.compute-1.amazonaws.com:8080/tcp_server_war/HelloWorldServlet/3"));

    List<CompletableFuture<String>> result = targets.stream()
        .map(url -> httpClient.sendAsync(
            HttpRequest.newBuilder(url)
                .GET()
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build(),
            HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> response.body()))
        .collect(Collectors.toList());

    for (CompletableFuture<String> future : result) {
      System.out.println(future.get());
    }

  }

}