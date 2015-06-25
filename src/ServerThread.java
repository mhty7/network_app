import java.lang.Runnable;
import java.net.*;


/**
 * @author mhty7
 *
 */
public class ServerThread implements Runnable {
		private Thread thr;
		private MyConnection mc;
		private MyServer ms;
		
		private boolean _halt;
	/**
	 * 
	 */
	public ServerThread(MyConnection mc,MyServer ms) {
		this.thr=new Thread(this);
		this.mc=mc;
		this.ms=ms;
		this._halt=false;
	}
	public void start(){
		thr.start();
		
	}
	public void run(){
		String input;
		while((input=mc.getMessage())!=null){
			
			//message=mc.checkUp(input);
			
			ms.notifyMessage(input,this);
			
			
			/*
			 if(!mc.sendMessage("S: "+mc.checkUp(mc.getMessage()))){
			 System.out.println("S: Fail to send message");
			 }*/
			
			if (_halt) {
				break;
			}
		}
			
	}
	
	public void halt(){
		_halt=true;
		mc.close();
	}
}
