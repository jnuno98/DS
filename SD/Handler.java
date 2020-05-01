import java.lang.Thread;
import java.io.*;
import java.util.*;
import java.net.*;
import java.time.LocalDateTime;

public class Handler extends Thread {
	private ServerSocket ssocket;
	private Socket csocket;
	private BufferedReader br;
	private Utilizador u;
	private String input;
	private PrintWriter pw;
	private Menu menu;

	public Handler (ServerSocket ssocket, Menu menu) {
		this.ssocket = ssocket;
		this.csocket = null;
		this.menu = menu;
		/*this.br = null;
		this.pw = null;
		this.u = null;
		this.input = null;*/
	}

	public void run () {
		while (true) {
			System.out.println(ssocket.getInetAddress() + " connected");

			try {
				System.out.println("Thread " + "criada!");
				this.csocket = ssocket.accept();
				System.out.println("Cliente entrou!");
				br = new BufferedReader (new InputStreamReader (csocket.getInputStream()));
				pw = new PrintWriter (csocket.getOutputStream(),true);


				initialize();

				csocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Cliente saiu!");
			//System.out.println(ssocket.getInetAddress() + " disconnected");
		}
	}

	public void initialize () throws IOException {
		Utilizador novo = new Utilizador();
		pw.println("Escolha a opção registar(R) ou login(L)");

		input = br.readLine();
		switch (input) {
			case "R":
				pw.println("Insira o seu hostname:");
				novo.sethostname(br.readLine());

				pw.println("Insira o seu email:");
				novo.setemail(br.readLine());

				pw.println("Insira a sua password:");
				novo.setpassword(br.readLine());

				novo.setloggedin(false);
				
				if (menu.registar(novo))
					pw.println("Utilizador registado com sucesso!");
				else 
					pw.println("Este email já está em uso!");

				initialize();
				break;

			case "L":					
				pw.println("Insira o seu email:");
				novo.setemail(br.readLine());

				pw.println("Insira a sua password:");
				novo.setpassword(br.readLine());

				switch (menu.login(novo.getemail(), novo.getpassword())){
					case 1:
						this.u = menu.get(novo.getemail());
						pw.println("Login efetuado como " + u.gethostname());
						opcoes();
						break;
					case 2:
						pw.println("Este utilizador já tem login feito na aplicação!");
						initialize();
						break;
					case 3:
						pw.println("Dados inválidos!");
						initialize();
						break;
				}

				break;

			default:
				pw.println("Opção inválida!");
				initialize();
				break;
		}
		
		System.out.println("saiu");
	}

	public void opcoes () throws IOException {
		pw.println("Escolha uma opção:");
		pw.println("	Reservar servidor a pedido (P)");
		pw.println("	Reservar servidor em leilão (L)");
		pw.println("	Consultar a sua conta (C)");
		pw.println("	Libertar um servidor (LS)");
		pw.println("	LogOut (LO)");

		input = br.readLine();
		switch (input) {
			case "P": // reservar servidor falta cancelar leilão caso não haja servidores
				Servidor pedido;
				int res_id;

				pw.println("Escolha o tipo de servidor: micro ou large");
				input = br.readLine();

				while (!(input.equals("micro") || input.equals("large"))) {
					pw.println("Insira micro ou large!");
					input = br.readLine();
				}

				if ((res_id = menu.reserva(u,input)) != -1) { 
					pw.println("A reserva foi feita com o id: " + res_id);
				} else {
					pw.println("Não há servidores disponíveis do tipo " + input);
				}
				
				opcoes();
				break;

			case "L": // licitar em leilão
				String tipo; int preco = 0;
				
				pw.println("Escolha o tipo de servidor: micro ou large");
				tipo = br.readLine();

				while (!(tipo.equals("micro") || tipo.equals("large"))) {
					pw.println("Insira micro ou large!");
					tipo = br.readLine();
				}
				
				

				if (menu.halivre(tipo)) {
					while (preco == 0) {
						pw.println("Indique o preço que pretende pagar:");
						preco = Integer.parseInt(br.readLine());
					}

					pw.println("A reserva foi feita com o id: " + menu.rleilao(u,tipo,preco,pw));
				} else {
					int valminimo = menu.valminimo(tipo);

					while (preco <= valminimo) {
						pw.println("Indique o preço que pretende pagar, tem de ser maior que " + valminimo);
						preco = Integer.parseInt(br.readLine());
					}

					pw.println("A sua licitação foi registada, será notificado se ganhar o leilão ou for ultrapassada");
					menu.leilao(u,pw,preco,tipo);
				}



				//if () {
//
//				//} else {
//
				//}



				opcoes();
				break;

			case "C": // consultar conta
				pw.println("Tem neste momento " + menu.divida(u) + " em dívida");	
				opcoes();
				break;

			case "LS": // libertar servidor
				pw.println("Neste momento tem as reservas:" + menu.getids(u));
				pw.println("Insira o ID da reserva que pretende cancelar");

				int id = Integer.parseInt(br.readLine());
				double divida = menu.liberta(id,this.u);

				switch ((int) divida) {
					case -1:
						pw.println("Não tem nenhuma reserva com este iD!");
						break;
					default:
						pw.println("Reserva cancelada, acumulou " + divida + " de divida");
						//pw.println("A sua dívida é agora de: " + u.getdivida() + " euros");
						break;

				}


				opcoes();
				break;

			case "LO": // logout
				this.u = null;
				initialize();
				break;

			default:
				pw.println("Opção inválida!");	
				opcoes();
				break;
		}
	}
}
