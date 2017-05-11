import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by NAVER on 2017. 4. 28..
 */
public class JavaSocketClient {

    public static void main(String[] args){
        try{
            int portNumber = 5001;
            Socket socket = new Socket("localhost", portNumber);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("Hello world");
            outputStream.flush();

            ObjectInputStream inSteram = new ObjectInputStream(socket.getInputStream());
            System.out.println(inSteram.readObject());

            socket.close();

        } catch (Exception e){

        }
    }
}
