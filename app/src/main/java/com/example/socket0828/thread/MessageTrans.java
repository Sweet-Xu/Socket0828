package com.example.socket0828.thread;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MessageTrans implements Runnable{

    private DatagramSocket socket ;
    //private DatagramSocket receiveSocket ;
   // private DatagramSocket sendSocket;
    private int PORT = 60001;
    private InetAddress addressToSend;

    //创建套接字
    @Override
    public void run() {
        try {
            socket = new DatagramSocket(PORT);
          //  receiveSocket = new DatagramSocket(PORT);
           // sendSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        initThread();
    }

    //启动接收线程
    public void initThread(){
        ReceiveThread receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    //启动发送线程
    public void sendMsg(Message msg) {
        SendThread sendThread = new SendThread(msg);
        sendThread.start();
    }


    //发送消息线程
    public class SendThread extends Thread{
        private Message msg;

        public SendThread(Message msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            //super.run();
            //发送
            try {
                addressToSend = InetAddress.getByName("172.17.50.138");
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
            DatagramPacket request = new DatagramPacket(data_send, data_send.length, addressToSend, 60001);
            try {
                socket.send(request);
            } catch (IOException e) {
                e.printStackTrace();
            }

           // Looper.prepare();

        }
    }

    //接收消息线程
    private class ReceiveThread extends Thread{

        @Override
        public void run() {
            while (true) {
                // ...不断接收
                byte[] data_receive = new byte[1024];
                DatagramPacket response = new DatagramPacket(data_receive, data_receive.length);
                try {
                    socket.receive(response);
                    DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(data_receive));
                    int sum = 0;
                    sum = dataIn.readUnsignedShort();

                    Log.d("receiveMsg", "size = " + response.getLength() + " data = " + sum);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    }


