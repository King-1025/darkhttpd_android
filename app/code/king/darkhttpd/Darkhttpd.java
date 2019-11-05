package king.darkhttpd;

import jni.layer.Core;
import java.util.List;
import java.util.ArrayList;

public class Darkhttpd {

	private String tag;
	private String root;
	private String port;
	private String index;
	private String logfile;

	private Thread thread;

	private boolean running=false;
        
	private List<String> command;

	public Darkhttpd(){
       
		this(".");
	}

	public Darkhttpd(
			String root
			){
               this(root,null);
	}

	public Darkhttpd(
			String root,
			String port
			){
               this(null,root,port,null,null); 
	}

	public Darkhttpd(
			String tag,
			String root,
			String port, 
			String index, 
			String logfile
			){
              this.tag     =  tag;
	      this.root    =  root;
	      this.port    =  port;
	      this.index   =  index;
	      this.logfile =  logfile;

	      running= false;
        }

	public boolean start(){
	       stop();
	       thread  = new Thread(new Runnable(){

                   @Override
                   public void run()
                   {
                       // TODO: Implement this method
	               try {
		          doTask();
	               }catch(Exception e){
		         //pass
	               }
		    }
               });
	       thread.start();
	       running=true;
	       return isRunning();
	}

	public void stop(){
	   if (thread != null && running == true){
	      running=false;
	      thread.stop();
	      thread=null;
              command=null;
	   }
	}

	public void setTag (String tag){
		this.tag = tag;
	}

	public void setRoot (String root){
		this.root = root;
	}

	public void setPort (String port){
		this.port = port;
	}

	public void setIndex (String index){
		this.index = index;
	}

	public void setLogfile (String logfile){
		this.logfile = logfile;
	}

	public boolean isRunning(){
		return running;
	}

	public List<String> getCommand(){
		return command;
	}
	
	private void doTask(){
 	  
	  List<String> command_list=new ArrayList<>();

	  if(tag != null && tag.length() > 0){
	     command_list.add(tag);
	  } else {
             command_list.add("darkhttpd");
	  }

	  if(root != null && root.length() > 0){
	     command_list.add(root);
	  } else {
	     command_list.add(".");
	  }
	  
          if(port != null && port.length() > 0){
             command_list.add("--port");
             command_list.add(port);
      	  }

          if(index != null && index.length() > 0){
             command_list.add("--index");
             command_list.add(index);
      	  }

          if(logfile != null && logfile.length() > 0){
             command_list.add("--log");
             command_list.add(logfile);
      	  }
           
	  command=command_list;
	 
	  Core.darkhttpd(
	       command.toArray(new String[command.size()])
	  );

        }

}
