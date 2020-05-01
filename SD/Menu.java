import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.time.LocalDateTime;
import java.time.Duration;
import java.io.PrintWriter;

public class Menu {
	private DadosCliente dc;
	private DadosServidor ds;

    private ReentrantLock rl;

    private Licitacao licitacao_micro;
    private Licitacao licitacao_large;

    public Menu (DadosCliente dc, DadosServidor ds) {
    	this.dc = dc;
    	this.ds = ds;
    	this.rl = new ReentrantLock ();
    	this.licitacao_micro = null;
    	this.licitacao_large = null;
    }

    public Utilizador get (String email) {
    	return dc.get(email);
    }

    public int login (String email, String password) {
    	rl.lock();

    	try {	

			if (dc.validar(email,password)) {
				if (!(dc.get(email).getloggedin())) {
					dc.get(email).setloggedin(true);
					return 1; // login com sucesso
				} else {
					return 2; // utilizador já logado
				}
			} else {
				return 3; // dados invalidos
			}
		} finally {
			rl.unlock();
		}
    }

    public boolean registar (Utilizador u) {
    	rl.lock();

    	try {
	    	boolean res;
    
    		if (dc.contains(u)) {
    			return false;
    		} else {
    			dc.put(u);
    			return true;
    		}
    	} finally {
    		rl.unlock();
    	}
    }

    public int reserva (Utilizador u, String tipo) { // falta cancelar leilão caso não haja servidores disponiveis
    	rl.lock();

    	try {
	    	int res;
    		Servidor pedido;

    		if ((pedido = ds.getlivre(tipo)) != null) {
    			Reserva r = new Reserva (u, pedido, LocalDateTime.now(),ds.nextid(),0,null);

    			ds.addreserva(r);
    			ds.ocupa(pedido.getid());

    			return r.getid();
    		} else {
    			Reserva r;
    			if ((r = ds.haleilao()) == null) return -1;
    			else {
    				Utilizador antigo = r.getutilizador();
    				antigo.setdivida(antigo.getdivida() + tempopassado(r.getdata()) * r.getleilao());
    				r.print("A sua reserva com o ID " + r.getid() + " foi cancelada");

    				r.setutilizador(u);
    				r.setdata(LocalDateTime.now());
    				r.setprintwriter(null);
    				r.setleilao(0);

    				return r.getid();
    			}

    		}
    	} finally {
    		rl.unlock();
    	}

    }

    public int valminimo (String tipo) {
    	if (tipo.equals("micro")) {
    		return licitacao_micro != null ? licitacao_micro.getpreco() : 0;
    	} else {
    		return licitacao_large != null ? licitacao_large.getpreco() : 0;
    	}
    }

    public void leilao (Utilizador u, PrintWriter pw, int preco, String tipo) {
    	if (tipo.equals("micro")) {
    		if (licitacao_micro == null) {
    			licitacao_micro = new Licitacao (u,pw,preco,tipo);
    		} else {
    			licitacao_micro.print("A sua licitação foi ultrapassada");
    			licitacao_micro = new Licitacao (u,pw,preco,tipo);
    		}
    	} else {
    		if (licitacao_large == null) {
    			licitacao_large = new Licitacao (u,pw,preco,tipo);
    		} else {
    			licitacao_large.print("A sua licitação foi ultrapassada");
    			licitacao_large = new Licitacao (u,pw,preco,tipo);
    		}
    	}
    }

    public int rleilao (Utilizador u, String tipo, int preco, PrintWriter pw) {
    	Servidor pedido = ds.getlivre(tipo);

    	if (pedido != null) {
    		Reserva r = new Reserva (u,pedido, LocalDateTime.now(),ds.nextid(),preco,pw);

    		ds.addreserva(r);
    		ds.ocupa(pedido.getid());

    		return r.getid();
    	} else {
    		return 0;
    	}
    }

    public boolean halivre (String tipo) {
    	return ds.getlivre(tipo) == null ? false : true;
    }

	public String getids (Utilizador u) {
	    String ids = "";
	
	    for (Reserva r : ds.getreservas()) {
	      if (u.equals(r.getutilizador()))
	        ids = ids + " " + r.getid();
	    }
	
	    return ids;
	} 

    public double liberta (int res_id, Utilizador u) {
    	rl.lock();

    	try {
    		List <Reserva> reservas = ds.getreservas();

    		for (Reserva r : reservas) {
    			if (res_id == r.getid()) {
    				if (u.equals(r.getutilizador())) {
    					r.getservidor().setestado(true);
    					double nova_divida = r.getleilao() == 0 ? tempopassado(r.getdata()) * r.getservidor().getpreco() : tempopassado(r.getdata()) * r.getleilao();

    					if (r.getservidor().gettipo().equals("micro") && licitacao_micro != null) {
    						r.setutilizador(licitacao_micro.getutilizador());
    						r.setleilao(licitacao_micro.getpreco());
    						r.setdata(LocalDateTime.now());
    						r.setprintwriter(licitacao_micro.getpw());

    						r.print("Ganhou o leilão! A sua reserva tem o id " + r.getid());
    						ds.addreserva(r);
    						licitacao_micro = null;
    						r.getservidor().setestado(false);
    					

    					} 

    					if (r.getservidor().gettipo().equals("large") && licitacao_large != null) {
    						r.setutilizador(licitacao_large.getutilizador());
    						r.setleilao(licitacao_large.getpreco());
    						r.setdata(LocalDateTime.now());
    						r.setprintwriter(licitacao_large.getpw());

    						r.print("Ganhou o leilão! A sua reserva tem o id " + r.getid());
    						ds.addreserva(r);
    						licitacao_large = null;
    						r.getservidor().setestado(false);
    					} 


    					ds.remove(r);
    					u.setdivida(u.getdivida() + nova_divida);
    					return nova_divida;
    				}
    			}
    		}

    		return -1;
    	} finally {
    		rl.unlock();
    	}
    }

    public void logout (Utilizador u) {
    	dc.get(u.getemail()).setloggedin(false);
    }

    public double divida (Utilizador u) {
    	double res = 0;

    	for (Reserva r : ds.getreservas()) {
    		if (u.equals(r.getutilizador()))
    			res += r.getleilao() == 0 ? tempopassado(r.getdata()) * r.getservidor().getpreco() : tempopassado(r.getdata()) * r.getleilao();
    	}

    	return res + u.getdivida();
    }

    public double tempopassado (LocalDateTime inicio) {
    	double diff = 4;
 	
 		return diff;
	}


}