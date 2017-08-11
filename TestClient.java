

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient {
	public static void main(String[] args) {
        Socket client = null;
        PrintWriter writer = null;
        try {
            client = new Socket("127.0.0.1", 9000);
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);
            Scanner sc=new Scanner(System.in);
            String aa=sc.nextLine();
            writer.print(aa);
            writer.flush();    
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(writer!=null){
                    writer.close();
                }
                if(client!=null){
                    client.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
