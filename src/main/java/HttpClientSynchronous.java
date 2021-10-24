
//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpClientSynchronous {

//  // 1. Synchronous Example
//  private static final HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(
//      Duration.ofSeconds(10)).build();
//
//  public static void main(String[] args) throws IOException, InterruptedException {
//    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/")).setHeader("User-Agent", "Java 11 HttpClient Bot").build();
//    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//    // print response headers
//    HttpHeaders headers = response.headers();
//    headers.map().forEach((k,v)->System.out.println(k+":"+v));
//
//    // print status code
//    System.out.println(response.statusCode());
//
//    // print response body
//    System.out.println(response.body());
//  }

  //1.1 multithreads
  final static private int NUMTHREADS = 100;
  private int count = 0;
  synchronized public void inc(){
    count++;
  }

  public int getValue(){
    return this.count;
  }

  private static final HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(
      Duration.ofSeconds(10)).build();

  public static void main(String[] args) throws IOException, InterruptedException {
    long startTime = System.currentTimeMillis();
    final HttpClientSynchronous counter = new HttpClientSynchronous();
    CountDownLatch  completed = new CountDownLatch(NUMTHREADS);
    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/")).setHeader("User-Agent", "Java 11 HttpClient Bot").build();
    for (int i = 0; i < NUMTHREADS; i++) {
      // lambda runnable creation - interface only has a single method so lambda works fine

      Runnable thread =  () -> {
        try {
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        completed.countDown();
      };
      new Thread(thread).start();
    }
    completed.await();
    long timeCost = System.currentTimeMillis() - startTime;
    System.out.println("Value should be equal to " + NUMTHREADS + " It is: " + counter.getValue() + "Time is " + timeCost);

//    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/")).setHeader("User-Agent", "Java 11 HttpClient Bot").build();
//    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//    // print response headers
//    HttpHeaders headers = response.headers();
//    headers.map().forEach((k,v)->System.out.println(k+":"+v));
//
//    // print status code
//    System.out.println(response.statusCode());
//
//    // print response body
//    System.out.println(response.body());
//    System.currentTimeMillis();
  }


//  //2.Asynchronous Example  GET
//  private static final HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_2).connectTimeout(Duration.ofSeconds(10)).build();
//
//  public static void main(String[] args)
//      throws InterruptedException, ExecutionException, TimeoutException {
//    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/")).setHeader("User-Agent","Java 11 HttpClient Bot").build();
//    CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//    String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
//    System.out.println(result);
//  }

//  //3. Post example
//  private static final HttpClient httpClient = HttpClient.newBuilder()
//      .version(HttpClient.Version.HTTP_2)
//      .connectTimeout(Duration.ofSeconds(10))
//      .build();
//
//  public static void main(String[] args) throws IOException, InterruptedException {
//
//    // json formatted data
//    String json = new StringBuilder()
//        .append("{")
//        .append("\"name\":\"mkyong\",")
//        .append("\"notes\":\"hello\"")
//        .append("}").toString();
//
//    // add json header
//    HttpRequest request = HttpRequest.newBuilder()
//        .POST(HttpRequest.BodyPublishers.ofString(json))
//        .uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/"))
//        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
//        .header("Content-Type", "application/json")
//        .build();
//
//    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//    // print status code
//    System.out.println(response.statusCode());
//
//    // print response body
//    System.out.println(response.body());
//
//  }

//  //4.authentication
//  private static final HttpClient httpClient = HttpClient.newBuilder()
//      .authenticator(new Authenticator() {
//        @Override
//        protected PasswordAuthentication getPasswordAuthentication() {
//          return new PasswordAuthentication(
//              "user",
//              "password".toCharArray());
//        }
//
//      })
//      .connectTimeout(Duration.ofSeconds(10))
//      .build();
//
//  public static void main(String[] args) throws IOException, InterruptedException {
//
//    HttpRequest request = HttpRequest.newBuilder()
//        .GET()
//        .uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/"))
//        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
//        .build();
//
//    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//    // print status code
//    System.out.println(response.statusCode());
//
//    // print response body
//    System.out.println(response.body());
//
//  }

}