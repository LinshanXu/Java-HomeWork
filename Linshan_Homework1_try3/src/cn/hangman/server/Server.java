package cn.hangman.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 网络多线程通信服务端类
 */
public class Server extends JFrame implements ActionListener {
	private JLabel lport = new JLabel("port:  ");		
	private JTextField port = new JTextField(4);		//端口
	private JButton listen = new JButton("listen");
	private JButton cancel = new JButton("cancel");
	private JTextArea message = new JTextArea(5, 30);	//显示信息文本域
	private JScrollPane jsp = new JScrollPane(message);
	private JLabel ltemp1 = new JLabel("  ");
	private JLabel ltemp2 = new JLabel("  ");
	private JLabel ltemp3 = new JLabel("  ");

	private JPanel jp1 = new JPanel();

	/**
	 * 多线程网络通信服务端类的构造方法，实现界面的初始化
	 * @see Server
	 */
	public Server() {
		message.setEditable(false);
		this.setSize(450, 600);
		this.setLocation(300, 200);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Server");
		jp1.add(lport);
		jp1.add(port);
		listen.addActionListener(this);
		jp1.add(listen);
		cancel.addActionListener(this);
		cancel.setEnabled(false);
		jp1.add(cancel);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jp1, BorderLayout.NORTH);
		getContentPane().add(jsp, BorderLayout.CENTER);
		getContentPane().add(ltemp1, BorderLayout.WEST);
		getContentPane().add(ltemp2, BorderLayout.EAST);
		getContentPane().add(ltemp3, BorderLayout.SOUTH);
	}

	/**
	 * 当有点击事件发生的时候调用该方法，并做相应的处理
	 * @param a 事件类对象
	 * 
	 */
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("cancel")) {
			/*	点击cancel的时候，做相应的处理*/
			this.listen.setEnabled(true);
			this.cancel.setEnabled(false);
			System.exit(0);
		} else if (a.getActionCommand().equals("listen")) {
			/*	点击listen的时候，做相应的处理*/
			this.listen.setEnabled(false);
			this.cancel.setEnabled(true);
			this.message.setText(this.message.getText() + "server starting..\n");
			if (Common.isnumber(this.port.getText()) == false) {
				/*	判断端口是否是数字，若不是，提示信息*/
				this.message.setText(this.message.getText() + "port must be a number.\n server start failed!!\n");
				this.listen.setEnabled(true);
				this.cancel.setEnabled(false);
			}else if(Integer.parseInt(this.port.getText())<8000){
				/*	判断端口是否大于8000，若不是，提示信息*/
				this.message.setText(this.message.getText() + "port must be more than 9000 .\n server start failed!!\n");
				this.listen.setEnabled(true);
				this.cancel.setEnabled(false);
			}else {
				/*	参数正确的时候，创建socket并启动监听线程*/
				try {
					int ports = Integer.parseInt(this.port.getText());
					ServerSocket serversocket = new ServerSocket(ports);
					this.message.setText(this.message.getText() + "server start success !!\n server started..\n");
					/*	启动监听线程*/
					new serverlistenThread(serversocket).start();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
				}
			}
		}
	}
	
	/**
	 * 多线程网络通信服务端监听线程
	 *
	 */
	private class serverlistenThread extends Thread{
		private ServerSocket serverSocket = null;
		
		/**
		 * 监听线程构造方法
		 * @param sc	主线程传给子线程的serversocket对象
		 */
		public serverlistenThread(ServerSocket sc){
			this.serverSocket = sc;
		}
		/**
		 * 功能方法
		 */
		public void run(){
			while (true) {
				message.setText(message.getText() + "Waiting...\n");
				Socket sc;
				try {
					sc = serverSocket.accept();//监听
					message.setText(message.getText()+"client from "+sc.getInetAddress().getHostAddress()+":"
						+sc.getPort()+"\n");
					/*	当有客户端连接，并建连接后，启动新线程处理与客户端的通信*/
					(new serverThread(sc)).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 处理与客户端通信的线程类
	 *
	 */
	private class serverThread extends Thread {
		private Socket socket = null;
		//TODU
		private String word ;
		char[] result ;
		int count;
		private int score =0;
		/**
		 * 构造方法
		 * @param sc	监听线程传给本线程的socket对象
		 */
		public serverThread(Socket sc) {
			this.socket = sc;
		}
		/**
		 * 接收客户端信息命令的线程功能方法
		 */
		public void run() {
			try{
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				word = Common.getWord().toLowerCase();
				message.setText(message.getText()+"begin---answer:"+word+"  "+socket.getInetAddress().getHostAddress()+":"
						+socket.getPort()+"\n");
				
				result = new char[word.length()];
				for (int i = 0; i < result.length; i++) {
					result[i]='*'; 
				}
				byte[] b = new byte[1024];
				int n = in.read(b);
				count = word.length();
				
				while(count>0){
					String guess = new String(b,0,n);
					message.setText(message.getText()+guess+"  ---from "+socket.getInetAddress().getHostAddress()+":"
							+socket.getPort()+"\n");
					count--;
					String temp = new String(result);
					result = Common.compare(word, guess, result);
					String revert=new String(result);
					System.out.println(revert);
					if(!revert.equals(temp)){
						count++;
					}
					if(revert.equals(word)){
						score++;
						revert = revert+"\tBingo!"+(word.length()-count)+""+"chances used.Score now:"+score;
						out.write(revert.getBytes());
						word = Common.getWord().toLowerCase();
						message.setText(message.getText()+"Game begin---answer is:"+word+"  "+socket.getInetAddress().getHostAddress()+":"
								+socket.getPort()+"\n");
						result = new char[word.length()];
						for (int i = 0; i < result.length; i++) {
							result[i]='*'; 
						}
						count = word.length();
					}else{
						revert = revert+"\tCome on!"+count+""+"chances left .Score now:"+score;
						if(count==0){
							score--;
							revert = "\tAnswer is:"+word+"Score now"+score;
						}
						out.write(revert.getBytes());
					}
					if(count==0){
						word = Common.getWord().toLowerCase();
						message.setText(message.getText()+"begin---answer is:"+word+"  "+socket.getInetAddress().getHostAddress()+":"
								+socket.getPort()+"\n");
						result = new char[word.length()];
						for (int i = 0; i < result.length; i++) {
							result[i]='*'; 
						}
						count = word.length();
					}
			
					out.flush();
					n = in.read(b);
				}
			}catch (Exception e) {
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server();
	}

}
