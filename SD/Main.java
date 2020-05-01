import java.lang.Thread;
import java.io.*;
import java.util.*;
import java.net.*;

public class Main {
	public static void main (String[] args) throws IOException {
		// inicializar
		int port = Integer.parseInt(args[0]);
		int clientes = Integer.parseInt(args[1]);
		Handler handlers[] = new Handler[clientes];
		ServerSocket ssocket = new ServerSocket(port);

		// Povoamento
		// Servidores do tipo "micro"
		Servidor s1 = new Servidor("micro", 1, true, 10);
		Servidor s2 = new Servidor("micro", 2, true, 10);
		Servidor s3 = new Servidor("micro", 3, true, 10);

		// Servidores do tipo "large"
		Servidor s4 = new Servidor("large", 4, true, 50);
		Servidor s5 = new Servidor("large", 5, true, 50);
		Servidor s6 = new Servidor("large", 6, true, 50);


		DadosServidor ds = new DadosServidor ();
		ds.put(s1);
    	ds.put(s2);
    	ds.put(s3);
		ds.put(s4);
    	ds.put(s5);
    	ds.put(s6);

		DadosCliente dc = new DadosCliente ();
		Utilizador u = new Utilizador ("teste","teste","teste",false);
		dc.put(u);

		Menu menu = new Menu (dc,ds);

		try {
			for (int i=0; i<clientes; i++){
				handlers[i] = new Handler (ssocket,menu);
				handlers[i].start();
			}
		} catch (Exception e) {
     		e.printStackTrace();
     	}
	}
}
