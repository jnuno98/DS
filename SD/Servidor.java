public class Servidor {
	private String tipo;
	private int id;
	private boolean estado; //true -> livre; false -> ocupado
	private double preco; // preço por hora

	// Construtores
	public Servidor (String tipo, int id, boolean estado, double p) {
		this.tipo = tipo;
		this.id = id;
		this.estado = estado;
		this.preco = p;
	}

	public Servidor (Servidor s) {
		this.tipo = s.gettipo();
		this.id = s.getid();
		this.estado = s.getestado();
		this.preco = s.getpreco();
	}

	//Getters
	public String gettipo () {
		return this.tipo;
	}

	public int getid () {
		return this.id;
	}

	public boolean getestado(){
		return this.estado;
	}

	public double getpreco(){
		return this.preco;
	}

	//Setters
	public void settipo (String tipo) {
		this.tipo = tipo;
	}

	public void setid (int id) {
		this.id = id;
	}

	public void setestado(boolean estado){
		this.estado = estado;
	}

	public void setpreco(double p){
		this.preco = p;
	}

	//Outros

	public Servidor clone () {
		return new Servidor (this);
	}
}
