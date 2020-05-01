import java.time.LocalDateTime;
import java.io.PrintWriter;

public class Reserva {
	private Utilizador u;
	private Servidor s;
	private LocalDateTime data;
	private int id;
	private int leilao; // >0 é o preço que foi pago em leilão
	private PrintWriter pw;

	//Construtores
	public Reserva (Utilizador u, Servidor s, LocalDateTime data, int id, int leilao, PrintWriter pw) {
		this.u = u;
		this.s = s;
		this.data = data;
		this.id = id;
		this.leilao = leilao;
		this.pw = pw;
	}

	//Getters
	public Utilizador getutilizador () {
		return this.u;
	}

	public Servidor getservidor () {
		return this.s;
	}

	public LocalDateTime getdata () {
		return this.data;
	}

	public int getid () {
		return this.id;
	}

	public int getleilao () {
		return this.leilao;
	}

	//Setters
	public void setutilizador (Utilizador u) {
		this.u = u;
	}

	public void setservidor (Servidor s) {
		this.s = s;
	}

	public void setdata (LocalDateTime data) {
		this.data = data;
	}

	public void setid (int id) {
		this.id = id;
	}

	public void setleilao (int leilao) {
		this.leilao = leilao;
	}

	public void setprintwriter (PrintWriter pw) {
		this.pw = pw;
	}

	public void print (String content) {
		pw.println(content);
	}
}