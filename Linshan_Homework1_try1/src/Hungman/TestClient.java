package Hungman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 客户端
 */
public class TestClient {
	public static void main(String[] args) {
		Socket socket;
		System.out.println("Connect successfully!Game begins!");
		while(true)	{
			//发起连接
			try {
				String guess;
				Scanner scan = new Scanner(System.in);
				guess = scan.next();
				//此处"localhost"是服务端的地址，表示本机，可以在同一机器上通信
				//在两台机器上就换成对方的IP地址，端口号保持相同
				socket = new Socket("localhost", 8888);
				//发送数据到服务端
				OutputStream os = socket.getOutputStream();
				//接收服务端发来的消息
				InputStream is = socket.getInputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(guess);
				
				DataInputStream dis = new DataInputStream(is);
				System.out.println(dis.readUTF());
				
				dos.close();
				dis.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
