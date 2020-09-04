package com.example.socket0828;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socket0828.thread.MessageTrans;
import com.example.socket0828.thread.MessageTransmit;
import com.example.socket0828.util.DateUtil;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ouyangshen on 2017/11/11.
 */
@SuppressLint(value={"HandlerLeak","StaticFieldLeak"})
public class SocketActivity extends AppCompatActivity implements OnClickListener {
    private final static String TAG = "SocketActivity";
    private EditText et_socket;
    private static TextView tv_socket;
    private MessageTransmit mTransmit; // 声明一个消息传输对象
    private MessageTrans messageTrans;
    private MessageTrans.SendThread sendThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        et_socket = findViewById(R.id.et_socket);
        tv_socket = findViewById(R.id.tv_socket);
        findViewById(R.id.btn_socket).setOnClickListener(this);
        //mTransmit = new MessageTransmit(); // 创建一个消息传输
        //new Thread(mTransmit).start(); // 启动消息传输线程
        messageTrans = new MessageTrans();
        new Thread(messageTrans).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_socket) {
            // 获得一个默认的消息对象
            Message msg = Message.obtain();
            msg.obj = et_socket.getText().toString(); // 消息内容
            Log.d(TAG, et_socket.getText().toString());
            // 通过消息线程的发送处理器，向后端发送消息
          //  mTransmit.mSendHandler.sendMessage(msg);
            messageTrans.sendMsg(msg);
        }
    }

    // 创建一个主线程的接收处理器，专门处理服务器发来的消息
    public static Handler mHandler = new Handler() {
        // 在收到消息时触发
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: " + msg.obj);
            if (tv_socket != null) {
                // 拼接服务器的应答字符串
                String desc = String.format("%s 收到服务器的应答消息：%s",
                        DateUtil.getNowTime(), msg.obj.toString());
                tv_socket.setText(desc);
            }
        }
    };

}

