import java.io.*;
import java.util.*;
import java.net.*;

public class Ler implements Runnable{
	private BufferedReader in;
	private Socket cs;

	public Ler(Socket cs){
		this.cs = cs;
	}

	public void run() {
		try{
			this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));

			String msg;

			while((msg = in.readLine()) != null)
				System.out.println(msg);

			this.in.close();
		}
		catch (Exception e) {

		}
	}
}
