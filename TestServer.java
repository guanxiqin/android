

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class TestServer {
	    
	    public static void main(String[] args) {
	        ServerSocket server = null;
	        BufferedReader reader = null;
	        Socket client = null;
	        try {
	            server = new ServerSocket(9000);//创建ServerSocket对象，并绑定端口
	            client = server.accept();//调用accept方法，等待客户端的连接
	            InputStream in = client.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(in));
	            String str = reader.readLine();
	            System.out.println(str);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            try{
	                if(client!=null){
	                    client.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	    }

	}
