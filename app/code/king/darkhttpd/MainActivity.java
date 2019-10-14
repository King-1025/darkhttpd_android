package king.darkhttpd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Environment;

import jni.layer.Core;

public class MainActivity extends AppCompatActivity {

    private  Thread th;
    private  String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	TextView tv = new TextView(this);
	
//	int ret=Core.darkhttpd(new String[]{"-h"});
	tv.setText(sdcardPath);
        
	setContentView(tv);


	th=new Thread(new Runnable() {
            @Override
            public void run() {
                Core.darkhttpd(new String[]{"darkhttpd",sdcardPath});
            }
        });

	th.start();

    }

    @Override
    protected void onDestroy(){
	super.onDestroy();
        th.stop();
    }
}
