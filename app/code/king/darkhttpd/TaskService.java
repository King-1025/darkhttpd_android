package king.darkhttpd;

import android.app.Service;
import android.content.Intent;
import android.os.Messenger;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

public class TaskService extends Service {
  
    private Darkhttpd darkhttpd;

    @Override
    public void onCreate() {
           super.onCreate();
           darkhttpd=new Darkhttpd();	
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
           startDarkhttpd(intent.getExtras());
           return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
           return null;
    }

    @Override
    public void onDestroy() {
           super.onDestroy();
	   darkhttpd.stop();
    }

    private void startDarkhttpd(Bundle bundle){

       if(bundle == null || darkhttpd == null) return;
       String port=bundle.getString(ConfigHelper.MSG_PORT);
       String root=bundle.getString(ConfigHelper.MSG_ROOT);
       String index=bundle.getString(ConfigHelper.MSG_INDEX);
       String logfile=bundle.getString(ConfigHelper.MSG_LOG);
    
       darkhttpd.setPort(port);
       darkhttpd.setRoot(root);
       darkhttpd.setIndex(index);
       darkhttpd.setLogfile(logfile);
       darkhttpd.start();
    }
    
    /*
    Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_CLIENT) {
              if (msg.getData() == null) {
                    return;
              }
              Bundle bundle = msg.getData();

              String port=bundle.getString(ConfigHelper.MSG_PORT);
              String root=bundle.getString(ConfigHelper.MSG_ROOT);
              String index=bundle.getString(ConfigHelper.MSG_INDEX);
              String logfile=bundle.getString(ConfigHelper.MSG_LOG);

    	      if(darkhttpd !=null){
		darkhttpd.setPort(port);
		darkhttpd.setRoot(root);
		darkhttpd.setIndex(index);
		darkhttpd.setLogfile(logfile);
		darkhttpd.start();
	      }
             
	      //回复消息给客户端
                Message replyMsg = Message.obtain();
                replyMsg.arg1 = ConfigHelper.MSG_ID_SERVER;
                Bundle bundle = new Bundle();
                bundle.putString(ConfigHelper.MSG_CONTENT, "听到你的消息了，请说点正经的");
                replyMsg.setData(bundle);

                try {
                    msg.replyTo.send(replyMsg);     //回信
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    n});

    @Override
    public IBinder onBind(final Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        darkhttpd.stop();
        return super.onUnbind(intent);
    }
    */
}
