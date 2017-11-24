package cn.hangman.test;


import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
import java.nio.charset.Charset;  
import java.util.Scanner;  
 
public class NClient {  
     
   //������Sockethannel��Selector����  
   private Selector  selector=null;  
   static final int PORT=30000;  
   //���崦�������ַ���  
   private Charset charset=Charset.forName("GBK");  
   //�ͻ���SocketChannel  
   private SocketChannel sc=null;  
     
   public void init() throws IOException{  
       selector=Selector.open();  
       InetSocketAddress isa=new InetSocketAddress("127.0.0.1", PORT);  
       //����open�ľ�̬������������ָ����������SocketChannel  
       sc=SocketChannel.open(isa);  
       //���ø�sc�ѷ������ķ�ʽ����  
       sc.configureBlocking(false);  
       //��SocketChannel����ע�ᵽָ����Selector  
       sc.register(selector, SelectionKey.OP_READ);  
       //������ȡ���������ݶ˵��߳�  
       new ClientThread().start();  
       //��������������  
       Scanner scan=new Scanner(System.in);  
       while(scan.hasNextLine()){  
           //��ȡ���̵�����  
           String line=scan.nextLine();  
           //�����̵����������SocketChanenel��  
           sc.write(charset.encode(line));  
       }  
   }  
     
   //�����ȡ�������˵����ݵ��߳�  
   private class ClientThread extends Thread{  
 
       @Override  
       public void run() {  
           try{  
               while(selector.select()>0){  
                   //����ÿ���п��ܵ�IO������Channel�����е�SelectionKey  
                   for(SelectionKey sk:selector.selectedKeys()){  
                       //ɾ�����ڴ����SelectionKey  
                       selector.selectedKeys().remove(sk);  
                       //�����SelectionKey��Ӧ��Channel���пɶ�������  
                       if(sk.isReadable()){  
                           //ʹ��NIO��ȡChannel�е�����  
                           SocketChannel sc=(SocketChannel)sk.channel();  
                           String content="";  
                           ByteBuffer bff=ByteBuffer.allocate(1024);  
                           while(sc.read(bff)>0){  
                               sc.read(bff);  
                               bff.flip();  
                               content+=charset.decode(bff);  
                           }  
                           //��ӡ��ȡ������  
                           System.out.println("������Ϣ:"+content);  
                           sk.interestOps(SelectionKey.OP_READ);  
                             
                       }  
                   }  
               }  
                 
           }catch(IOException io){  
               io.printStackTrace();  
           }  
       }  
         
   }  
     
   public static void main(String [] args) throws IOException{  
       new NClient().init();  
   }  
 
}  
