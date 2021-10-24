import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;

import com.rabbitmq.client.*;

public class RPCServer {

  private static final String RPC_QUEUE_NAME = "rpc_queue";

  private static int fib(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    return fib(n - 1) + fib(n - 2);
  }

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
//    factory.setUsername("guest");
//    factory.setPassword("guest");
//    factory.setPort(5672);

    try (Connection connection = factory.newConnection();  //establish a connection
        Channel channel = connection.createChannel()) {  //establish a channel
      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);  //declare the queue
      channel.queuePurge(RPC_QUEUE_NAME);

      channel.basicQos(1);  //Spread the load equally over multiple servers

      System.out.println(" [x] Awaiting RPC requests");

      Object monitor = new Object();
      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        AMQP.BasicProperties replyProps = new AMQP.BasicProperties
            .Builder()
            .correlationId(delivery.getProperties().getCorrelationId())
            .build();

        String response = "";

        try {
          String message = new String(delivery.getBody(), "UTF-8");
          int n = Integer.parseInt(message);

          System.out.println(" [.] fib(" + message + ")");
          response += fib(n);
        } catch (RuntimeException e) {
          System.out.println(" [.] " + e.toString());
        } finally {
          channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
          // RabbitMq consumer worker thread notifies the RPC server owner thread
          synchronized (monitor) {
            monitor.notify();
          }
        }
      };

      channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));  //access the queue. Provide a callback in the form of an object(DeliverCallback)
      // Wait and be prepared to consume the message from RPC client.
      while (true) {
        synchronized (monitor) {
          try {
            monitor.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}