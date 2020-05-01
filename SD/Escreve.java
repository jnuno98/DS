import java.io.*;
import java.util.*;
import java.net.*;

public class Escreve implements Runnable {
	private PrintWriter out;
	private BufferedReader in;
	private Socket cs;

	public Escreve(Socket cs){
		this.cs = cs;
	}

	public void run(){
		try{
			this.out = new PrintWriter(cs.getOutputStream(),true);
			this.in = new BufferedReader(new InputStreamReader(System.in));

			String msg;

			while((msg = this.in.readLine())!= null)
				this.out.println(msg);

			this.out.close();
			this.in.close();
		}
		catch (Exception e) {
		}
	}

}
