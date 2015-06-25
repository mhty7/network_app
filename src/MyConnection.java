import java.io.*;
import java.net.*;


/**
 * @author mhty7
 *
 */
public class MyConnection {

	private Socket _s;

	
	
	
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader in;
	
	private OutputStream os;
	private OutputStreamWriter osw;
	private PrintWriter out;
	


	public MyConnection(){
	}
	public MyConnection(Socket s){
		this._s=s;
		
		try {
			is = _s.getInputStream();
			isr = new InputStreamReader(is);
			in=new BufferedReader(isr);
			
			os=_s.getOutputStream();
			osw=new OutputStreamWriter(os);
			out=new PrintWriter(osw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public boolean sendMessage(String msg){
		boolean issuccess=true;
		out.println(msg);
		out.flush();
		return issuccess;
	}
	
	public String getMessage(){
		String msg="";
		try {
			msg=in.readLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return msg;

	}
	
	public void close(){
		try {
			_s.close();
			is.close();
			isr.close();
			in.close();
			os.close();
			osw.close();
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
