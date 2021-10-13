
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpClientSynchronous {

  // 1. Synchronous Example
  private static final HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(
      Duration.ofSeconds(10)).build();

  public static void main(String[] args) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/tcp_server_war_exploded//HelloWorldServlet/")).setHeader("User-Agent", "Java 11 HttpClient Bot").build();
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print response headers
    HttpHeaders headers = response.headers();
    headers.map().forEach((k,v)->System.out.println(k+":"+v));

    // print status code
    System.out.println(response.statusCode());

    // print response body
    System.out.println(response.body());
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