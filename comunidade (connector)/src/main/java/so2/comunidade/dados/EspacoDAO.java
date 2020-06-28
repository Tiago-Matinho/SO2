package so2.comunidade.dados;

import so2.comunidade.bd.PostgresConn;

import java.sql.Connection;

public class EspacoDAO {

    public Espaco findById(long id) {
        Connection conn = null;
        Espaco ret = null;

        try{
            conn = PostgresConn
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
