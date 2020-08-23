package com.asdf1st.mydemo.Mqtt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class BaseMqttActivity extends BaseActivity {
    public static final int TYPE_DMS_AIRQUALITY =1;//空气仪配网
    public static final int TYPE_DETAIL_AIRQUALITY =2;//空气仪详情
    public static final int TYPE_DMS_FRESHAIR =3;//新风机配网
    public static final int TYPE_DETAIL_FRESHAIR =4;//新风机详情
    public static final int TYPE_DMS_WATERFILTER =5;//净水机配网
    public static final int TYPE_DETAIL_WATERFILTER =6;//净水机详情
    public static final String TAG = BaseMqttActivity.class.getSimpleName();
    private MqttAndroidClient client;
    private MqttConnectOptions conOpt;
    private ScheduledExecutorService timerService;
    private String host = "tcp://120.79.175.227:1883";
    private String userName = "admin";
    private String passWord = "admin";
    boolean isFirstData=true;
    private boolean haveResult=false;
    private boolean isDmsSuccess =false;
    private boolean timeOut=false;
    private int heartBeatTimeOut=33;
    private boolean isConutDownWork=false;
    private Timer timer_publish,timer_subscribe;
    private long period_publish=3000,timeoutMill_publish=15000,
            timeoutMill_subscribe=timeoutMill_publish+period_publish;
    protected String topic_subscribe = "ForTest";//要订阅的主题
    protected String topic_publish = "ForTest";//要发送的主题
    private String[] topicArrs_subscribe;//要订阅的主题组,用于一次订阅多个主题
    private String barcodeId ;
    protected String deviceMac=null;
    protected String clientId = "androidId";//客户端标识
    private CountDownTimer countDownTimer=new CountDownTimer();
    int timemill_publish =0;
    private CheckOnlineCallback onlineCallback;
    protected int type= TYPE_DMS_AIRQUALITY;

    @Override
    protected void initClass() {
        super.initClass();
        timerService = Executors.newScheduledThreadPool(3);
    }

    public void startMqtt() {
        // 服务器地址（协议+地址+端口号）
        String uri = host;
        client = new MqttAndroidClient(this, uri, clientId);
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);
        conOpt = new MqttConnectOptions();
        // 清除缓存
        conOpt.setCleanSession(true);
        // 设置超时时间，单位：秒
        conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
        conOpt.setKeepAliveInterval(20);
        // 用户名
        conOpt.setUserName(userName);
        // 密码
        conOpt.setPassword(passWord.toCharArray());     //将字符串转换为字符串数组
        // last will message
        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + clientId + "\"}";
        Log.e(getClass().getName(), "message是:" + message);
        String topic = topic_subscribe;
        Integer qos = 0;
        Boolean retained = false;
        if ((!message.equals("")) || (!topic.equals(""))) {
            // 最后的遗嘱
            // MQTT本身就是为信号不稳定的网络设计的，所以难免一些客户端会无故的和Broker断开连接。
            //当客户端连接到Broker时，可以指定LWT，Broker会定期检测客户端是否有异常。
            //当客户端异常掉线时，Broker就往连接时指定的topic里推送当时指定的LWT消息。

            try {
                conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
            } catch (Exception e) {
                Log.e(TAG, "Exception Occured:"+e.getMessage());
                doConnect = false;
                iMqttActionListener.onFailure(null, e);
            }
        }
        if (doConnect) {
            doClientConnection();
        }

    }

    /** 连接MQTT服务器 */
    private void doClientConnection() {
        if (!client.isConnected() && isConnectIsNormal()) {
            try {
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }
    
    public  void publish(String msg){
       byte[] msgBytes=msg.getBytes();
       publish(msgBytes);
    }

    public  void publish(byte[] byteArrs){
        String topic = topic_publish;
        Integer qos = 0;
        Boolean retained = false;
        try {
            if (client != null){
                client.publish(topic, byteArrs, qos.intValue(), retained.booleanValue());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Log.e(TAG, "连接成功 ");
            Log.e(TAG, "订阅通道:"+topic_subscribe);
            Log.e(TAG,"发送通道:"+topic_publish);
            try {
                switch (type){
                    case TYPE_DMS_AIRQUALITY:
                    case TYPE_DMS_FRESHAIR:
                    case TYPE_DMS_WATERFILTER:
                        client.subscribe(topic_subscribe,0);
                        BaseMqttActivity.this.publish(deviceMac);
                        startDmsTimeOutTimer();
                        break;
                    case TYPE_DETAIL_WATERFILTER:
                        client.subscribe(topic_subscribe,0);
                        Map<String,Object> map=new LinkedHashMap<>();
                        map.put("flag",242);
                        map.put("version",1);
                        map.put("cmd",16);
                        map.put("gprs",1);
                        map.put("addr","192.168.0.1");
                        map.put("barcodeid",barcodeId);
                        final String jsonStr=new Gson().toJson(map);
                        //TODO 有时候这里会为空,因此要做判断处理
                        if (timerService==null)
                            timerService = Executors.newScheduledThreadPool(3);
                        timerService.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                //Log.e(TAG,"timerpublish");
                                publish(jsonStr);
                            }
                        },500,5000, TimeUnit.MILLISECONDS);
                        break;
                    case TYPE_DETAIL_AIRQUALITY:
                        //int[] qoss=new int[]{0,0};
                        client.subscribe(topic_subscribe,0);
                        break;
                    case TYPE_DETAIL_FRESHAIR:
                        //int[] qoss=new int[]{0,0};
                        client.subscribe(topic_subscribe,0);
                        break;
                }

            } catch (MqttException e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            Log.e(TAG,"连接失败:"+arg1.getMessage());
            // 连接失败，重连
        }
    };

    private void startDmsTimeOutTimer() {
        timer_publish =new Timer();
        timer_publish.schedule(new TimerTask() {
            @Override
            public void run() {
                timemill_publish +=period_publish;
                if (timemill_publish >timeoutMill_publish){
                    timeOut=true;
                    Log.e(TAG,"mqtt配网发送结束");
                    if (timer_publish!=null)
                        timer_publish.cancel();
                    timer_publish=null;
                }else {
                    if (!isDmsSuccess){
                        BaseMqttActivity.this.publish(deviceMac);
                    }else {
                        if (timer_publish!=null)
                            timer_publish.cancel();
                        timer_publish=null;
                    }
                }
            }
        },1000 ,period_publish);
        timer_subscribe=new Timer();
        timer_subscribe.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isDmsSuccess){
                    getMessageFail("限时内没有收到mqtt信息,配网失败");
                }
            }
        },timeoutMill_subscribe);
    }

    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            if (isFirstData){
                isFirstData=false;
                if (onlineCallback!=null){
                    onlineCallback.onGetOnlineState(true);
                }
            }
            switch (type){
                case TYPE_DMS_AIRQUALITY:
                case TYPE_DMS_FRESHAIR:
                case TYPE_DMS_WATERFILTER:
                    messageArrived_Dms(topic,message);
                    break;
                case TYPE_DETAIL_AIRQUALITY:
                    messageArrived_Detail_Airquality(topic,message);
                    break;
                case TYPE_DETAIL_WATERFILTER:
                    messageArrived_Detail_Waterfilter(topic,message);
                    break;
                case TYPE_DETAIL_FRESHAIR:
                    messageArrived_Detail_FreshAir(topic,message);
                    break;
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {

        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
        }
    };

    private void messageArrived_Detail_Waterfilter(String topic, MqttMessage message) {
        String rs= new String(message.getPayload());
        rs= rs==null?"":rs;
        Log.e(TAG,"topic:"+topic+","+rs);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(rs);
            int cmd=jsonObject.getInt("cmd");
            if (cmd==34){
                startAndCheckHeartBeat();
            }else {
                getMessageSuccess(rs);
            }
        } catch (JSONException e) {
            getMessageFail(e.getMessage());
            //e.printStackTrace();
        }


    }

    private void messageArrived_Dms_Water(String topic, MqttMessage message) {
        if (!timeOut&&!isDmsSuccess){
            String rs= new String(message.getPayload());
            rs= rs==null?"":rs;
            Log.e(TAG,rs);
            if (rs.equals("DEVICETYPE=WTP")){
                isDmsSuccess =true;
                if (timer_publish !=null)
                    timer_publish.cancel();
                if (timer_subscribe!=null)
                    timer_subscribe.cancel();
               getMessageSuccess(rs);
                Log.e(TAG,"Mqtt净水机成功");
            }
        }

    }

    private void messageArrived_Dms(String topic, MqttMessage message) {
        if (!timeOut&&!isDmsSuccess){
            String rs= new String(message.getPayload());
            rs= rs==null?"":rs;
            Log.e(TAG,"topic:"+topic+","+rs);
            if (rs.equals("DEVICETYPE=AIRQ")||
                    rs.equals("DEVICETYPE=PAU1")||
                    rs.equals("DEVICETYPE=PAU2")||
                    rs.equals("DEVICETYPE=WTP")){
                isDmsSuccess =true;
                if (timer_publish !=null)
                    timer_publish.cancel();
                if (timer_subscribe!=null)
                    timer_subscribe.cancel();
                    switch (rs){
                        case "DEVICETYPE=AIRQ":
                            getMessageSuccess("AIRQ");
                            break;
                        case "DEVICETYPE=PAU1":
                            getMessageSuccess("PAU1");
                            break;
                        case "DEVICETYPE=PAU2":
                            getMessageSuccess("PAU2");
                            break;
                        case "DEVICETYPE=WTP":
                            getMessageSuccess("WTP");
                            break;
                    }

                Log.e(TAG,"Mqtt配网成功");
            }
        }
    }

    private void messageArrived_Detail_Airquality(String topic, MqttMessage message) {
        String rs= new String(message.getPayload());
        rs= rs==null?"":rs;
        Log.e(TAG,"topic:"+topic+","+rs);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(rs);
            int cmd=jsonObject.getInt("cmd");
            if (cmd==18){
                startAndCheckHeartBeat();
            }else {
                getMessageSuccess(rs);
            }
        } catch (JSONException e) {
            getMessageFail(e.getMessage());
            //e.printStackTrace();
        }
    }

    private void messageArrived_Detail_FreshAir(String topic, MqttMessage message) {
        String rs= new String(message.getPayload());
        rs= rs==null?"":rs;
        Log.e(TAG,"topic:"+topic+","+rs);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(rs);
            int cmd=jsonObject.getInt("cmd");
            if (cmd==68){
                startAndCheckHeartBeat();
            }else {
                getMessageSuccess(rs);
            }
        } catch (JSONException e) {
            getMessageFail(e.getMessage());
            //e.printStackTrace();
        }
    }



    private void startAndCheckHeartBeat(){
        if (!isConutDownWork){
            isConutDownWork=true;
            timerService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    int curCountDownSecond;
                    synchronized (countDownTimer){
                        if (countDownTimer.countDownSecond<=0){
                            countDownTimer.countDownSecond=0;

                        }else {
                            countDownTimer.countDownSecond--;
                        }
                        curCountDownSecond=countDownTimer.countDownSecond;
                    }
                    //Log.e(TAG,"countdown:"+curCountDownSecond);
                    if (curCountDownSecond>0){

                        if (onlineCallback!=null)
                            onlineCallback.onGetOnlineState(true);
                    }else {
                        if (onlineCallback!=null)
                            onlineCallback.onGetOnlineState(false);
                    }


                }
            },1000,1000,TimeUnit.MILLISECONDS);

        }
        synchronized (countDownTimer){
            countDownTimer.countDownSecond=heartBeatTimeOut;
        }
    }



    /** 判断网络是否连接 */
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.e(TAG, "MQTT当前网络名称：" + name);
            return true;
        } else {
            Log.e(TAG, "MQTT 没有可用网络");
            return false;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getTopicArrs_subscribe() {
        return topicArrs_subscribe;
    }

    public void setTopicArrs_subscribe(String[] topicArrs_subscribe) {
        this.topicArrs_subscribe = topicArrs_subscribe;
    }


    public boolean isClientAlreadyConnected() {
        if(client != null){
            try{
                boolean result = client.isConnected();
                if(result){
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    public class CountDownTimer{
        public int countDownSecond=heartBeatTimeOut;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        if (timerService != null) {
            if (!timerService.isShutdown() && !timerService.isTerminated()) {
                timerService.shutdownNow();
            }
            timerService = null;
        }
        if (isClientAlreadyConnected()){
            try {
                client.close();
                client.unregisterResources();
                client.disconnect();

            } catch (MqttException e) {
                Log.e(TAG,"解绑失败:"+e.getMessage());
            }
            Log.e(TAG,"mqtt客户端关闭成功");
        }

    }

    protected abstract void onGetOnlineState(boolean isOnline);

    protected abstract void getMessageSuccess(Object object);

    protected abstract void getMessageFail(String msg);

}
