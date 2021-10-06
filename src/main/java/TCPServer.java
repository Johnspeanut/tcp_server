import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer{
  ServerSocket ss = null;
  Socket socket = null;
  InputStream is = null;
  ByteArrayOutputStream baos = null;

  public TCPServer(){
    try {
      //1. create server socket, assign port
      ss = new ServerSocket(8081);
      //2. Accept the socket coming from client
      socket = ss.accept();
      //3. GEt input stream
      is = socket.getInputStream();
      //4. Read data from the input stream
      baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[20];
      int len;
      while((len = is.read(buffer)) != -1){
        baos.write(buffer, 0, len);
      }
      System.out.println(baos.toString());
    }catch (IOException e){
      e.printStackTrace();
//    }finally {
//      if(baos != null){
//        try{
//          baos.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//      }
//      if(is != null){
//        try{
//          is.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//      }
//
//      if(socket != null){
//        try{
//          socket.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//      }
//      if(ss != null){
//        try{
//          ss.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//
//      }
    }
  }


}