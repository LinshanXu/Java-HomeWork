package cn.hangman.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * ������߳�ͨ�ſͻ�����
 */
public class Client extends JFrame implements ActionListener {
	private Socket sc = null;
	private JTextField ip = new JTextField(16);			// ip��ַ
	private JTextField port = new JTextField(4);		// �˿�
	private JTextArea receive = new JTextArea(5, 30);	// ������Ϣ�ı���
	private JTextField sent = new JTextField(25);		// ������Ϣ�ı���
	private JScrollPane jsp = new JScrollPane(receive);
	private JLabel lip = new JLabel("ip:");
	private JLabel lport = new JLabel("port:");
	private JLabel lreceive = new JLabel("Input ip and port first."+
	"Hungman game process is as followes");
	private JLabel lsent = new JLabel("Input a letter or a word");

	private JButton conn = new JButton("connect");
	private JButton send = new JButton("send");

	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();
	
	/**
	 * �ͻ�����Ĺ��췽����ʵ�ֽ���ĳ�ʼ��
	 * @see Client
	 * 
	 */
	public Client() {
		receive.setEditable(false);
		this.setSize(450, 600);
		this.setLocation(300, 200);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Client");
		jp4.add(lip);
		jp4.add(ip);
		jp4.add(lport);
		jp4.add(port);
		conn.addActionListener(this);
		jp4.add(conn);
		jp1.setLayout(new GridLayout(2, 1));
		jp1.add(jp4);
		jp1.add(lreceive);
		jp2.setLayout(null);
		jsp.setBounds(10, 10, 444, 420);
		jp2.add(jsp);
		jp3.setLayout(new GridLayout(2, 1));
		sent.setDocument(new CharDocument());
		jp5.add(sent);
		
		jp5.add(send);
		send.addActionListener(this);
		jp3.add(lsent);
		jp3.add(jp5);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jp1, BorderLayout.NORTH);
		getContentPane().add(jp2, BorderLayout.CENTER);
		getContentPane().add(jp3, BorderLayout.SOUTH);
	}

	/**
	 * ���е���¼�������ʱ����ø÷�����������Ӧ�Ĵ���
	 * @param e �¼������
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("connect")) {
			/*	�����connect��ť��ʱ��ִ������Ĳ���*/
			this.receive.setText(this.receive.getText() + "connecting !!\n");
			int port1 = 8000;		//Ĭ������¶˿���8000
			try {
				String host = this.ip.getText();
				port1 = Integer.parseInt(this.port.getText());
				this.sc = new Socket(host, port1);
				this.receive.setText(this.receive.getText()
						+ "connect success !!\n"+"Begin hungman game with input a letter or a word!\n"
						+"Your guess chances are limited!\n"+"You can always quit by click the Red Cross!\n"
						+"Multiplayer supportted by reuse the program with different port number!\n");
				this.conn.setEnabled(false);
				this.send.setEnabled(true);
				/* �������߳̽��ܷ���˷�������Ϣ*/
				new ListenThread(sc.getInputStream()).start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				this.receive.setText(this.receive.getText()
						+ "connect failed !!\n");
				e1.printStackTrace();
			}

		} else if (e.getActionCommand().equals("send")) {
			/*	�����send��ť��ʱ��ִ������Ĳ���*/
			try {
				OutputStream out = sc.getOutputStream();
				out.write(this.sent.getText().trim().getBytes());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	/**
	 * ���շ���˷�����Ϣ���߳���
	 */
	private class ListenThread extends Thread {
		private InputStream in = null;
		
		/**
		 * ���շ���˷�����Ϣ�߳���Ĺ��췽��
		 * @param in	����������
		 */
		public ListenThread(InputStream in) {
			this.in = in;
		}
		/**
		 * �̵߳Ĺ��ܷ���
		 */
		public void run() {
			byte[] b = new byte[1024];
			int i;
			try {
				i = this.in.read(b);
				while (i > 0) {
					/*	������Ϣ������Ϣ�������ʾλ��*/
					receive.setText(receive.getText() + new String(b,0,i) + "\n");
					i = this.in.read(b);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Client();
	}

}
