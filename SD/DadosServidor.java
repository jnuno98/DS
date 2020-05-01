import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DadosServidor{
  private Map <Integer, Servidor> dadosservidor; //chave -> id (pode ser o nome, se quiseres criar um atributo nome no servidor)
  private List <Reserva> reservas;

  ReentrantLock rl = new ReentrantLock();
  //Condition haLivres = rl.newCondition();

  public DadosServidor(){
    this.dadosservidor = new HashMap <Integer, Servidor> ();
    this.reservas = new ArrayList <Reserva> ();
  }

  // Reservas
  public Reserva haleilao () {
    for (Reserva r : reservas) {
      if (r.getleilao() > 0)
        return r;
    }

    return null;
  }

  public void addreserva (Reserva r) {
    this.reservas.add(r);
  }

  public List<Reserva> getreservas () {
    return this.reservas;
  }

  public void remove (Reserva r) {
    this.reservas.remove(r);
  }

  public int nextid () {
    if (reservas.size() == 0) return 0;

    return (reservas.get(reservas.size() - 1)).getid() + 1;
  }

  // servidores
  public void ocupa (int id) {
    this.dadosservidor.get(id).setestado(false);
  }

  public void put(Servidor s){
    this.dadosservidor.put(s.getid(), s);
  }

  public Servidor get(int id){
    return dadosservidor.get(id);
  }

  public boolean validarEstado(int id){ //não sei se isto será necessário mas ok
    boolean res = dadosservidor.get(id).getestado();

    return res;
  }

  public Servidor getlivre (String tipo) {
    for (Servidor s : dadosservidor.values()){
      if (s.getestado() && s.gettipo().equals(tipo))
        return s;
    }

    return null;
  }

  public List<Servidor> getLivres(){
        List<Servidor> res = new ArrayList<>();

        for(Servidor s : dadosservidor.values()){
            if(s.getestado() == true){
                res.add(s);
            }
        }

        return res;
  }

  /*
  public Servidor reservar() throws InterruptedException{
        Servidor s = null;
        this.rl.lock();
        if(getLivres().size() == 0) haLivres.await();

        for(Servidor server : getLivres()){
            if(server.getestado() == true){
                s = server;
                break;
            }
        }
        this.rl.unlock();
        return s;
    }
  */
}
