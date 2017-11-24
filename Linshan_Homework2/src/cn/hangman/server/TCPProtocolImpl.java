package cn.hangman.server;

import java.io.IOException;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.nio.charset.Charset;  

public class TCPProtocolImpl implements TCPProtocol {  
	private int bufferSize;  
	String word = "";
	char[] result ;
	int count;
	private int score =0;
	public TCPProtocolImpl(int bufferSize) {  
		this.bufferSize = bufferSize;  
	}  

	public void handleAccept(SelectionKey key) throws IOException {  
		// 返回创建此键的通道，接受客户端建立连接的请求，并返回 SocketChannel 对象  
		SocketChannel clientChannel = ((ServerSocketChannel) key.channel())  
				.accept();  
		// 非阻塞式  
		clientChannel.configureBlocking(false);  
		// 注册到selector  
		clientChannel.register(key.selector(), SelectionKey.OP_READ,  
				ByteBuffer.allocate(bufferSize));  
		word = Common.getWord().toLowerCase();
		result = new char[word.length()];
		for (int i = 0; i < result.length; i++) {
			result[i]='-'; 
		}
		count = word.length();
	}  

	public void handleRead(SelectionKey key) throws IOException {  
		// 获得与客户端通信的信道  
		SocketChannel clientChannel = (SocketChannel) key.channel();  
		// 得到并清空缓冲区  
		ByteBuffer buffer = (ByteBuffer) key.attachment();  
		buffer.clear();  
		// 读取信息获得读取的字节数  
		long bytesRead = clientChannel.read(buffer);  
		if (bytesRead == -1) {  
			// 没有读取到内容的情况  
			clientChannel.close();  
		} else {  
			// 将缓冲区准备为数据传出状态  
			buffer.flip();  
			// 将字节转化为为UTF-16的字符串  
			String receivedString = Charset.forName("UTF-8").newDecoder()  
					.decode(buffer).toString();  
			// 控制台打印出来  
			System.out.println("From"  
					+ clientChannel.socket().getRemoteSocketAddress() + "Message:"  
					+ receivedString);  
			// 准备发送的文本  
			if(receivedString.equals("It's ready.")){
				String sendString = "Begin now.Input a word or a letter.";  
				buffer = ByteBuffer.wrap(sendString.getBytes("UTF-8"));  
				clientChannel.write(buffer);  
				// 设置为下一次读取或是写入做准备  
				key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
				return; 
			}
			count--;
			result = Common.compare(word, receivedString, result);
			String revert=new String(result);
			if(revert.equals(word)){
				score++;
				revert = revert+"\tBingo!"+(word.length()-count)+"chances used.Score:"+score;
				word = Common.getWord().toLowerCase();
				result = new char[word.length()];
				for (int i = 0; i < result.length; i++) {
					result[i]='-'; 
				}
				count = word.length();
				buffer = ByteBuffer.wrap(revert.getBytes("UTF-8"));  
				clientChannel.write(buffer);  
				// 设置为下一次读取或是写入做准备  
				key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
			}else{
				revert = revert+"\tCome on!"+count+"chances left.Score:"+score;
				if(count==0){
					score--;
					revert = "\tAnswer:"+word+"Score"+score;
				}
				buffer = ByteBuffer.wrap(revert.getBytes("UTF-8"));  
				clientChannel.write(buffer);  
				// 设置为下一次读取或是写入做准备  
				key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
			}
			if(count==0){
				word = Common.getWord().toLowerCase();
				result = new char[word.length()];
				for (int i = 0; i < result.length; i++) {
					result[i]='-'; 
				}
				count = word.length();
			}
		}
	}  

	public void handleWrite(SelectionKey key) throws IOException {  
		// do nothing  
	}

}  
