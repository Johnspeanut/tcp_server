

import java.util.concurrent.CountDownLatch;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

public class HelloClient  {
  final static private int NUMTHREADS = 100;
  private int count = 0;
  private static String url = "http://ec2-3-94-187-54.compute-1.amazonaws.com:8080/tcp_server_war/HelloWorldServlet/";
  synchronized public void inc(HttpClient client,GetMethod method){

        try {
      // Execute the method.
      int statusCode = client.executeMethod(method);

      if (statusCode != org.apache.http.HttpStatus.SC_OK) {
        System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] responseBody = method.getResponseBody();

      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data
      System.out.println(new String(responseBody));
      count++;

    } catch (IOException e) {
      System.err.println("Fatal transport error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Release the connection.
      method.releaseConnection();
    }
  }
  public int getVal(){
    return this.count;
  }

  public static void main(String[] args) throws InterruptedException {
    final HelloClient counter = new HelloClient();
    CountDownLatch completed = new CountDownLatch(NUMTHREADS);

    // Create an instance of HttpClient.
    HttpClient client = new HttpClient();

    // Create a method instance.
    GetMethod method = new GetMethod(url);


    // Provide custom retry handler is necessary
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler(3, false));

    long start = System.currentTimeMillis();
    for(int i = 0; i < NUMTHREADS;i++){
      //lambda runnable creation-inteface only has a single method so lambda works fine
      Runnable thread = ()->{counter.inc(client,method); completed.countDown();};
      new Thread(thread).start();
    }

    completed.await();
    long spentTime = System.currentTimeMillis()-start;
    System.out.println("Spending time "+ spentTime + " It is: " + counter.getVal());




//

  }
}
