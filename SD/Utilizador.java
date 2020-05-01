import java.io.*;
import java.net.*;
import java.util.*;

public class Utilizador {
	private String email;
	private String password;
	private String hostname;
	private boolean loggedin;
	private double divida;

	// Construtores
	public Utilizador () {
		this.email = "";
		this.password = "";
		this.hostname = "";
		this.loggedin = false;
		this.divida = 0;
	}

	public Utilizador (String email, String password, String hostname, boolean loggedin) {
		this.email = email;
		this.password = password;
		this.hostname = hostname;
		this.loggedin = loggedin;
	}

	public Utilizador (Utilizador u) {
		this.email = u.getemail();
		this.password = u.getpassword();
		this.hostname = u.gethostname();
		this.loggedin = u.getloggedin();
	}

	// Getters
	public String getemail () {
		return this.email;
	}

	public String getpassword () {
		return this.password;
	}

	public String gethostname () {
		return this.hostname;
	}

	public boolean getloggedin () {
		return this.loggedin;
	}

	public double getdivida () {
		return this.divida;
	}

	// Setters
	public void setemail (String email) {
		this.email = email;
	}

	public void setpassword (String password) {
		this.password = password;
	}

	public void sethostname (String host) {
		this.hostname = host;
	}

	public void setloggedin (boolean loggedin) {
		this.loggedin = loggedin;
	}

	public void setdivida (double divida) {
		this.divida = divida;
	}

	// Outros

	public Utilizador clone () {
		return new Utilizador (this);
	}

	public boolean equals (Utilizador u) {
		return this.email.equals(u.getemail()) && this.password.equals(u.getpassword()) &&
			   this.hostname.equals(u.gethostname()) && this.loggedin == u.getloggedin() && this.divida == u.getdivida();
	}


}