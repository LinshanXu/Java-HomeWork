package Hungman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 服务端
 */
public class TestServer {
	public static void main(String[] args) {
		System.out.println("Input a letter or a word");
		//开启扫描器
		Scanner scan = new Scanner(System.in);
		//声明一个word变量，用来接收键盘的输入
		String word = scan.next();
		System.out.println("Answer is:"+word);
		System.out.println("Waiting for guess");
		//声明一定char数组，长度为单词的长度，并初始化为{'-','-','-','-',……}
		char[] result = new char[word.length()];
		for (int i = 0; i < result.length; i++) {
			result[i]='-'; 
		}
		
		//定义一个计数器count,用来限制猜的次数，次数等于字母的个数
		int count = word.length();
		//定义一个得分计数器，初始为0
		int score =0;
		ServerSocket serverSocket = null;
		try {
			//new一个serverSocket对象，端口号设为8888，可为0-65536任意值
			serverSocket = new ServerSocket(8888);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//当还有猜的次数时循环
		while(count>0){
			try {
				//等待客户端的连接，此时即为阻塞
				Socket socket = serverSocket.accept();
				
				//接收客户端发来的消息
				InputStream is = socket.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				//读到的竞猜消息给guess变量
				String guess  = dis.readUTF();
				
				System.out.println("The players guess："+guess);
				//准备开始判断对错，竞猜次数减一
				count--;
				//调用比较的方法，把设定的答案、猜的字符串、和要显示的结果传给方法
				result = compare(word, guess, result);
				//发送消息给客户端
				OutputStream os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				//初始化等等要回复的消息，让它先等于提示信息
				String revert=new String(result);
				
				if(guess.equals(word)){
					//如果猜对了 得分加一，完善一下返回消息
					score++;
					revert = revert+"\tBingo!"+(word.length()-count)+"chances used,score now:"+score+"请等待出题";
					//通过网络回复最终结果
					dos.writeUTF(revert);
					//开始新的一轮竞猜
					System.out.println("The player wins,input a new word please:");
					word = scan.next();
					System.out.println("Answer is:"+word);
					System.out.println("Wairting for new guess:");
					result = new char[word.length()];
					for (int i = 0; i < result.length; i++) {
						result[i]='-'; 
					}
					//重置计数器
					count = word.length();

					//关闭资源
					dis.close();
					dos.close();
				}else{
					//如果没猜对 返回相应的提示消息
					revert = revert+"\tState:"+count+"chances left,score now:"+score;
					if(count==0){
						revert = "\tsorry,no chances left,the answer is:"+word;
					}
					dos.writeUTF(revert);
					//关闭资源
					dis.close();
					dos.close();
				}
				//次数用完，开始新游戏，重置计数器
				if(count==0){
					System.out.println("The player failed,please input a new word");
					word = scan.next();
					System.out.println("Answer is:"+word);
					System.out.println("Waiting for new guess");
					result = new char[word.length()];
					for (int i = 0; i < result.length; i++) {
						result[i]='-'; 
					}
					count = word.length();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 比较算法
	 * @param word
	 * @param guess
	 * @param result
	 * @return result
	 */
	static char[] compare(String word,String guess,char[] result){
		//把答案和猜的字符串转换成字符数组
		char[] wordChar = word.toCharArray();
		char[] guessChar = guess.toCharArray();
		//逐个去匹配
		for(int i=0;i<guessChar.length;i++){
			for(int j=0;j<wordChar.length;j++){
				//匹配正确的某一位替换成提示的信息
				if(guessChar[i]==wordChar[j]){
					result[j]=guessChar[i];
				}
			}
		}
		//把提示消息传回主函数，完成一次调用
		return result;
	}
}
