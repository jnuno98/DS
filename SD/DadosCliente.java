import java.util.*;
import java.time.LocalDateTime;
import java.time.Duration;

public class DadosCliente {
	private Map <String,Utilizador> dados; // Chave -> Email

	public DadosCliente	 () {
		dados = new HashMap <String,Utilizador> ();
	}

	public void put (Utilizador u) {
		dados.put(u.getemail(),u);
	}

	public Utilizador get (String email) {
		return dados.get(email);
	}

	public void entrou (String email) {
		this.dados.get(email).setloggedin(true);
	}

	public boolean contains (Utilizador u) {
		return dados.containsKey(u.getemail());
	}

	public boolean validar (String email, String password) {
		if (dados.containsKey(email))
			if (dados.get(email).getpassword().equals(password))
				return true;
		
		return false;
	}
	
	public void printall () {
		for (Utilizador u : dados.values()) {
			System.out.println("Email: " + u.getemail() + Integer.toString(u.getemail().length()));
			System.out.println("Password: " + u.getpassword() + Integer.toString(u.getpassword().length()));
		}

	}
}