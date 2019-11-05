package king.darkhttpd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;

import android.os.RemoteException;

import android.os.IBinder;
import android.os.Message;

import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import king.darkhttpd.R;

import android.Manifest;
import androidx.annotation.NonNull;
import android.widget.Toast;

import java.security.Permission;

import android.content.Intent;
import android.os.Messenger;
import android.os.Handler;
import android.content.ServiceConnection;
import android.content.ComponentName;

public class MainActivity extends AppCompatActivity {

    private Darkhttpd darkhttpd;
    private String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    private EditText etPort;
    private EditText etRoot;
    private EditText etIndex;
    private EditText etLog;
    private Button btCon;
/*
    private Messenger mServerMessenger;
    private Messenger mClientMessenger;
    private ServiceConnection mMessengerConnection;

    private boolean mbound=false;
*/
    private boolean mbound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

        darkhttpd = new Darkhttpd();

        etPort= (EditText) findViewById(R.id.activitymain0EditText1);
        etRoot = (EditText) findViewById(R.id.activitymain0EditText2);
        etIndex = (EditText) findViewById(R.id.activitymain0EditText3);
        etLog = (EditText) findViewById(R.id.activitymain0EditText4);
	btCon= (Button) findViewById(R.id.activitymain0Button1);

	etPort.setText("8080");
	etRoot.setText(sdcardPath);
        etIndex.setText("index.html");
        etLog.setText(null);

	btCon.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
    	             verifyStoragePermissionsForDarkhttpdTask();
                }
        });
/*
        mClientMessenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_SERVER){
                    if (msg.getData() == null){
                        return;
                    }
    
                    String content = (String) msg.getData().get(ConfigHelper.MSG_CONTENT);
                    LogUtils.d(TAG, "Message from server: " + content);
                }
	      
            }
        });
*/
/*        mMessengerConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder service) {
                mServerMessenger = new Messenger(service);

		Message message = Message.obtain();
                message.arg1 = ConfigHelper.MSG_ID_CLIENT;
                Bundle bundle = new Bundle();

		String port=etPort.getText()+"";
		String root=etRoot.getText()+"";
		String index=etIndex.getText()+"";
		String logfile=etLog.getText()+"";

                bundle.putString(ConfigHelper.MSG_PORT, port);
                bundle.putString(ConfigHelper.MSG_ROOT, root);
                bundle.putString(ConfigHelper.MSG_INDEX,index);
                bundle.putString(ConfigHelper.MSG_LOG,  logfile);

                message.setData(bundle);
              //  message.replyTo = mClientMessenger;
                try {
                   mServerMessenger.send(message);
                } catch (RemoteException e) {
                   e.printStackTrace();
                }
            }
    
            @Override
            public void onServiceDisconnected(final ComponentName name) {
                mServerMessenger = null;	
            }
        };
*/
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
  //      unbindService(mMessengerConnection);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionTool.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    private void verifyStoragePermissionsForDarkhttpdTask() {

        String[] permissions = new String[]{
      	         Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //是否支持显示系统设置权限设置窗口跳转
        PermissionTool.showSystemSetting = false;

        //这里的this不是上下文，是Activity对象！
        PermissionTool.getInstance().chekPermissions(
	    	       MainActivity.this,
		       permissions,
                       new PermissionTool.IPermissionsResult() {
           @Override
           public void passPermissons() {
                   handleService();
           }

           @Override
           public void forbitPermissons() {
              Toast.makeText(MainActivity.this, "权限不通过，无法启动！", Toast.LENGTH_SHORT).show();
           }
       });
    }

   
    private void handleService(){   
         if(!mbound){
	    Intent intent = new Intent(this, TaskService.class);
	    intent.putExtras(makeData());
            startService(intent);
 //           bindService(intent, mMessengerConnection, 0);
	    btCon.setText("关闭");
            setEnabled(false);
	 }else{
	    Intent intent = new Intent(this, TaskService.class);
	    stopService(intent);
//            unbindService(mMessengerConnection);
	    btCon.setText("启动");
            setEnabled(true);
	 }
	 mbound=!mbound;
    }

    private void setEnabled(boolean is){
        etPort.setEnabled(is);
        etRoot.setEnabled(is);
        etIndex.setEnabled(is);
        etLog.setEnabled(is);
    }

    private Bundle makeData(){
            Bundle bundle = new Bundle();
            String port=etPort.getText()+"";
            String root=etRoot.getText()+"";
            String index=etIndex.getText()+"";
            String logfile=etLog.getText()+"";
            bundle.putString(ConfigHelper.MSG_PORT, port);
            bundle.putString(ConfigHelper.MSG_ROOT, root);
            bundle.putString(ConfigHelper.MSG_INDEX,index);
            bundle.putString(ConfigHelper.MSG_LOG,  logfile);
            return bundle;
    }

}
