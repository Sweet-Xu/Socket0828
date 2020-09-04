package com.example.socket0828.thread;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import com.example.socket0828.SocketActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

@SuppressLint("HandlerLeak")
public class MessageTransmit implements Runnable {
    private static final String TAG = "MessageTransmit";
    // 以下为Socket服务器的IP和端口，根据实际情况修改
    private static final String SOCKET_IP = "172.17.50.176";
    private static final int SOCKET_PORT = 9898;
    private BufferedReader mReader = null; // 声明一个缓存读取器对象
   // private OutputStream mWriter = null; // 声明一个输出流对象
    private BufferedWriter mWriter = null;
  //  private Socket socket;

    private ServerSocket serverSocket;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    InetAddress addressToSend;
    HandlerThread mHandlerThread;

    @Override
    public void run() {
//        // 创建一个套接字对象
//        socket = new Socket();
//        try {
//            // 命令套接字连接指定地址的指定端口
//            socket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
//            // 根据套接字的输入流，构建缓存读取器
//            mReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            // 获得套接字的输出流
//          //  mWriter = socket.getOutputStream();
//            mWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            // 启动一条子线程来读取服务器的返回数据
//            new RecvThread().start();
//            // 为当前线程初始化消息队列
//            Looper.prepare();
//            // 让线程的消息队列开始运行，之后就可以接收消息了
//            Looper.loop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //换成udp通信
        try{
//            serverSocket = new ServerSocket(9898); //绑定端口
//
//            while (true) {
//                Socket socket = serverSocket.accept(); //等待接收客户端的请求
//                Log.d("Debug", "socket accept");
//                //处理其他代码
//                new UdpRecvThread(socket).start(); // 启动一条子线程来读取服务器的返回数据
//
//            }
            datagramSocket = new DatagramSocket(60001);
             addressToSend = InetAddress.getByName("192.168.1.121");
        //    datagramSocket.setSoTimeout(5000);

            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteout);

            try {
                dataOut.writeInt(121212);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data_send = byteout.toByteArray();
            DatagramPacket request = new DatagramPacket(data_send, data_send.length, addressToSend,60001);
            try {
                datagramSocket.send(request);
            } catch (IOException e) {
                e.printStackTrace();
            }

            new UdpRecvThread(datagramSocket).start();
           // Looper.prepare();  //放了这个就发不出去消息
            // 让线程的消息队列开始运行，之后就可以接收消息了
           // Looper.loop();

//            Looper.prepare();
//            // 让线程的消息队列开始运行，之后就可以接收消息了
//            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    // 创建一个发送处理器对象，让App向后台服务器发送消息
//    public Handler mSendHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Log.d(TAG, "handleMessage: " + msg.obj);
//            // 换行符相当于回车键，表示我写好了发出去吧
//            String send_msg = msg.obj.toString() + "\n";
//            try {
//                // 往输出流对象中写入数据
//            //    mWriter.write(send_msg.getBytes("utf8"));
//                mWriter.write(String.valueOf(send_msg.getBytes("utf8")));
//            } catch (IOException e) {
//                Log.e(TAG, "SockSend Error!");
//                e.printStackTrace();
//            }
//        }
//    };


  //  mHandlerThread = new HandlerThread("handlerThread");
        //    mHandlerThread.start();
    // 创建一个发送处理器对象，让App向后台服务器发送消息
//    public Handler mSendHandler = new Handler(mHandlerThread.getLooper() ) {
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                addressToSend = InetAddress.getByName("192.168.1.121");
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
//            DataOutputStream dataOut = new DataOutputStream(byteout);
//
//            try {
//                dataOut.writeInt(121212);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            byte[] data_send = byteout.toByteArray();
//            DatagramPacket request = new DatagramPacket(data_send, data_send.length, addressToSend, 60001);
//            try {
//                datagramSocket.send(request);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }



//            Log.d(TAG, "handleMessage: " + msg.obj);
////            // 换行符相当于回车键，表示我写好了发出去吧
////            String send_msg = msg.obj.toString() + "\n";
//            try {
//                // 往输出流对象中写入数据
//                //    mWriter.write(send_msg.getBytes("utf8"));
////                OutputStream os = socket.getOutputStream();
////                OutputStreamWriter osw = new OutputStreamWriter(os);
////                BufferedWriter bw = new BufferedWriter(osw);
////                bw.write("今天天气好");
//
//            //    bw.flush();
//             //   bw.close();
//               // mWriter.write(String.valueOf(send_msg.getBytes("utf8")));
//            } catch (IOException e) {
//                Log.e(TAG, "SockSend Error!");
//                e.printStackTrace();
//            }


       // }
  //};



    // 创建一个发送处理器对象，让App向后台服务器发送消息
    public Handler mSendHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                addressToSend = InetAddress.getByName("192.168.1.121");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteout);

            try {
                dataOut.writeInt(121212);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data_send = byteout.toByteArray();
            DatagramPacket request = new DatagramPacket(data_send, data_send.length, addressToSend,60001);
            try {
                datagramSocket.send(request);
            } catch (IOException e) {
                e.printStackTrace();
            }



//            Log.d(TAG, "handleMessage: " + msg.obj);
////            // 换行符相当于回车键，表示我写好了发出去吧
////            String send_msg = msg.obj.toString() + "\n";
//            try {
//                // 往输出流对象中写入数据
//                //    mWriter.write(send_msg.getBytes("utf8"));
////                OutputStream os = socket.getOutputStream();
////                OutputStreamWriter osw = new OutputStreamWriter(os);
////                BufferedWriter bw = new BufferedWriter(osw);
////                bw.write("今天天气好");
//
//            //    bw.flush();
//             //   bw.close();
//               // mWriter.write(String.valueOf(send_msg.getBytes("utf8")));
//            } catch (IOException e) {
//                Log.e(TAG, "SockSend Error!");
//                e.printStackTrace();
//            }


        }
    };

//    // 定义消息接收子线程，让App从后台服务器接收消息
//    private class RecvThread extends Thread {
//        @Override
//        public void run() {
//            try {
//                String content;
//                // 读取到来自服务器的数据
//                while ((content = mReader.readLine()) != null) {
//                    // 获得一个默认的消息对象
//                    Message msg = Message.obtain();
//                    msg.obj = content; // 消息描述
//                    // 通知SocketActivity收到消息
//                    SocketActivity.mHandler.sendMessage(msg);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private class UdpRecvThread extends Thread{
        private DatagramSocket socket = null;

        public UdpRecvThread(DatagramSocket socket){
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                while(true) {
                    byte[] data_receive = new byte[1024];
                    DatagramPacket response = new DatagramPacket(data_receive, data_receive.length);
                    socket.receive(response);
                    DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(data_receive));
                    int sum = dataIn.readUnsignedShort();
                    Log.d(TAG, "size = " + response.getLength() + " data = " + sum);

                    //socket.close();
                }

//                InputStream is = socket.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String s = " ";
//                while((s=br.readLine())!= null){
//                    System.out.println(s);
//                    Log.d("info","s");
//                }
//                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

