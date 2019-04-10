package com.asdf1st.mydemo.Socket;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketClientActivity extends AppCompatActivity {
    String TAG="client";
    RecyclerView rv_msg;
    MyAdapter adapter;
    Button btn_sendmsg;
    List<Data> dataList;
    Socket socket;
    Handler handler;
    public static final int GET_SERVER_SOCKET=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        initClass();
        initView();
        afterInit();
    }

    private void initClass() {
        dataList=new ArrayList<>();
        adapter=new MyAdapter(dataList,this);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case GET_SERVER_SOCKET:
                        Boolean isSuccess= (Boolean) msg.obj;
                        if (isSuccess){
                            Toast.makeText(SocketClientActivity.this,"接收服务器socket成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SocketClientActivity.this,"接收服务器socket失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                try {
                    socket=new Socket();
                    socket.connect(new InetSocketAddress("192.168.1.104", 3000), 5000);
                    System.out.println("接收服务器socket成功");
                    msg.obj=true;
                } catch (IOException e) {
                    System.out.println("接收服务器socket失败");
                    msg.obj=false;
                }finally {
                    msg.arg1=GET_SERVER_SOCKET;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void initView() {
        rv_msg= (RecyclerView) findViewById(R.id.rv_msg);
        rv_msg.setAdapter(adapter);
        btn_sendmsg= (Button) findViewById(R.id.btn_sendmsg);
        btn_sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        //Socket socket = null;
                        try {
                            socket = new Socket("192.168.1.104", 3000);
                            Log.e(TAG, "Success connected to Server");
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                            String line = br.readLine();
                            Log.e(TAG, "line: "+line);
                            Data data=new Data(line);
                            dataList.add(data);
                            adapter.notifyDataSetChanged();
                            br.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void afterInit() {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        List<Data> dataList;
        Context context;

        public MyAdapter(List<Data> dataList, Context context) {
            this.dataList = dataList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg,parent));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
           Data data=dataList.get(position);
           if (data!=null && data.getName()!=null){
               holder.tv_msg.setText(data.getName());
           }
        }

        @Override
        public int getItemCount() {
            return dataList==null?0:dataList.size();
        }

        class MyViewHolder extends  RecyclerView.ViewHolder{
           TextView tv_msg;
            public MyViewHolder(View itemView) {
                super(itemView);
                tv_msg=itemView.findViewById(R.id.tv_msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
