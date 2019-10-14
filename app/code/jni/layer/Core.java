package jni.layer;

public class Core {

	static {
            System.loadLibrary("core");
	}
      
	public static native int darkhttpd(String []command_list);

}


