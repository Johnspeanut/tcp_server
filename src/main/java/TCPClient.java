import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient{
  Socket socket = null;
  OutputStream os = null;
  public TCPClient(){
    try{
      InetAddress inet = InetAddress.getByName("10.0.0.187");
      socket = new Socket(inet, 8888);

      os = socket.getOutputStream();
      os.write("Hello Joe".getBytes());
    }catch (IOException e){
      e.printStackTrace();
//    }finally{
//      if(os != null){
//        try{
//          os.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//      }
//      if(socket != null){
//        try{
//          socket.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//
//      }
    }
  }
}