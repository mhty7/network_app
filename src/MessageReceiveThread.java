import java.lang.Runnable;

/**
 * @author mhty7
 *
 */
public class MessageReceiveThread implements Runnable {
	
	Thread thr;
	MyConnection mc;
	MyChatHandler mch;
	private final static String _name = "receive";
	
	public MessageReceiveThread(){
	}
	public MessageReceiveThread(MyConnection mc,MyChatHandler mch){
		this.thr=new Thread(this,_name);
		this.mc=mc;
		this.mch=mch;
	}
	
	public void start(){
		thr.start();
	}
	
	public void run(){
		String serveroutput;
		
		while ((serveroutput=mc.getMessage())!=null) {
			
			mch.handleWindow(serveroutput);
			
			
			if (serveroutput.equals(MySetting.goodbye)) {
				break;
			}
		}	
	}
}
