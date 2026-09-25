import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

public class MultiClient {

    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try{
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address,port);
                    PrintWriter toSocket = new PrintWriter(socket.getOutputStream());
                    toSocket.println("Hello from client");
                    BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = fromSocket.readLine();
                    System.out.println("Message from Server : "+line);

                    fromSocket.close();
                    toSocket.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static void main(String[] args) {
        MultiClient client = new MultiClient();
        for(int i=0 ; i<100 ; i++){
            Thread thread = new Thread(client.getRunnable());
            thread.start();
        }
    }
}
