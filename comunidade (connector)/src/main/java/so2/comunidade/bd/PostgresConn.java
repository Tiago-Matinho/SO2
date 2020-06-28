package so2.comunidade.bd;

import so2.comunidade.dados.*;
import java.util.List;

public interface PostgresConn {

    public boolean connect();
    public boolean disconnect();

    public boolean novoEspaco(String nome, String coord);
    public Espaco espacoID(long id);
    public List<Espaco> espacoNome(String nome);
    public List<Espaco> espacoCoord(String coord);

    public void novoRegisto(long espaco_id, String utilizador_nome);
    public Registo registoID(long id);
    public List<Registo> registoEspaco(long espaco_id);
    public List<Registo> registoUtilizador(String utilizador_id);

    public boolean nomeUtilizador(String nome, String password);
    public boolean utilizadorLogin(String nome, String password);
}
