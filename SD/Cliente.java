import java.io.*;
import java.util.*;
import java.net.*;

public class Cliente{
		public static void main(String[] args) throws UnknownHostException, IOException{
		int porto = Integer.parseInt(args[0]);
		String input = null;
		Utilizador utilizador = null;
		Socket socket = socket = new Socket("localhost", porto);

		new Thread(new Escreve(socket)).start();
		new Thread(new Ler(socket)).start();

		while(true);
	}
}
