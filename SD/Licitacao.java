import java.io.PrintWriter;

public class Licitacao {
	private Utilizador u;
	private PrintWriter pw;
	private int preco;
	private String tipo;

	public Licitacao (Utilizador u, PrintWriter pw, int preco, String tipo) {
		this.u = u;
		this.pw = pw;
		this.preco = preco;
		this.tipo = tipo;
	}

	//Getters
	public Utilizador getutilizador () {
		return this.u;
	}

	public int getpreco () {
		return this.preco;
	}

	public String gettipo () {
		return this.tipo;
	}

	public PrintWriter getpw () {
		return this.pw;
	}

	//Setters
	public void setutilizador (Utilizador u) {
		this.u = u;
	}

	public void setpreco (int preco) {
		this.preco = preco;
	}

	public void settipo (String tipo) {
		this.tipo = tipo;
	}

	public void print (String content) {
		this.pw.println(content);
	}
}