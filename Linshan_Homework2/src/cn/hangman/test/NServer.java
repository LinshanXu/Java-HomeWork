package cn.hangman.test;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.Channel;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.nio.charset.Charset;  
  
public class NServer {  
  
    // ���ڼ�����е�Channel״̬��selector  
    private Selector selector = null;  
    static final int PORT = 30000;  
    // ����ʵ�ֱ��롢������ַ���������  
    private Charset charse = Charset.forName("GBK");  
  
    public void init() throws IOException {  
        selector = Selector.open();  
        // ͨ��open��������һ��δ�󶨵�ServerSocketChannel����  
        ServerSocketChannel server = ServerSocketChannel.open();  
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);  
        // ����ServerSocketChannel�󶨵�ָ����IP��ַ  
        server.bind(isa);  
        // ����serverSocket�ѷ�������ʽ����  
        server.configureBlocking(false);  
        // ��serverע�ᵽָ����selector����  
        server.register(selector, SelectionKey.OP_ACCEPT);  
        while (selector.select() > 0) {  
            // һ�δ���selector�ϵ�ÿ��ѡ���SelectionKey  
            for (SelectionKey sk : selector.selectedKeys()) {  
                // ��selector����ѡ���Kye����ɾ�����ڴ����SelectionKey  
                selector.selectedKeys().remove(sk);  
                // ���sk��Ӧ��Channel�����ͻ��˵���������  
                if (sk.isAcceptable()) {  
                    // ����accept�����������ӣ������������ε�SocketChennal  
                    SocketChannel sc = server.accept();  
                    // ���ò��÷�����ģʽ  
                    sc.configureBlocking(false);  
                    // ����SocketChannelע�ᵽselector  
                    sc.register(selector, SelectionKey.OP_READ);  
                }  
                // ���sk��Ӧ��Channel��������Ҫ��ȡ  
                if (sk.isReadable()) {  
                    // ��ȡ��SelectionKey�����е�Channel����Channel���п̶ȵ�����  
                    SocketChannel sc = (SocketChannel) sk.channel();  
                    // ���屸עִ�ж�ȡ����Դ��ByteBuffer  
                    ByteBuffer buff = ByteBuffer.allocate(1024);  
                    String content = "";  
                    // ��ʼ��ȡ����  
                    try {  
                        while (sc.read(buff) > 0) {  
                            buff.flip();  
                            content += charse.decode(buff)+"123";  
                        }  
                        System.out.println("��ȡ�����ݣ�" + content);  
                        // ��sk��Ӧ��Channel���ó�׼����һ�ζ�ȡ  
                        sk.interestOps(SelectionKey.OP_READ);  
                    }  
                    // ������񵽸�sk�����е�Channel�������쳣������  
                    // Channel��Ӧ��Client���������⣬���Դ�Selector��ȡ��  
                    catch (IOException io) {  
                        // ��Selector��ɾ��ָ����SelectionKey  
                        sk.cancel();  
                        if (sk.channel() != null) {  
                            sk.channel().close();  
                        }  
                    }  
                    // ���content�ĳ��ȴ���0,��������Ϣ��Ϊ��  
                    if (content.length() > 0) {  
                        // ����selector��ע�������SelectionKey  
                        for (SelectionKey key : selector.keys()) {  
                            // ��ȡ��key��Ӧ��Channel  
                            Channel targerChannel = key.channel();  
                            // �����Channel��SocketChannel����  
                            if (targerChannel instanceof SocketChannel) {  
                                // ����ȡ��������д���Channel��  
                                SocketChannel dest = (SocketChannel) targerChannel;  
                                dest.write(charse.encode(content));  
                            }  
                        }  
                    }  
                }  
            }  
        }  
  
    }  
      
    public static void main(String [] args) throws IOException{  
        new NServer().init();  
    }  
  
}  