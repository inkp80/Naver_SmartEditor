import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by NAVER on 2017. 4. 28..
 */
public class JavaSocketServer {

    public static void main(String[] args){
        try{
            int portNumber = 5001;

            System.out.println("Starting Java Socket Server...");
            ServerSocket aServerSocket = new ServerSocket(portNumber);

            System.out.println("listening at port" + portNumber+"...");

            while(true){
                Socket socket = aServerSocket.accept();

                InetAddress clientHost = socket.getLocalAddress();
                int clientPort = socket.getPort();


                System.out.println("A client connected. host : " + clientHost + ", port" + clientPort);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object obj = inputStream.readObject();

                System.out.println("Input : " + obj);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                outputStream.writeObject(obj + " from server.");
                outputStream.flush();
                socket.close();
            }
        } catch (Exception e){

        }
    }


}
