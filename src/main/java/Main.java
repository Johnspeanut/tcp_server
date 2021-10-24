import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//    TCPServer tcpServer = new TCPServer();
//    TCPClient tcpClient = new TCPClient();

    RPCClient fibRpt = new RPCClient();
    String response = fibRpt.call("30");
    fibRpt.close();
  }

}

