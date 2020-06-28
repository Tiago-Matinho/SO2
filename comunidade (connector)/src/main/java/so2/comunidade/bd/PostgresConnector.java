package so2.comunidade.bd;

import so2.comunidade.dados.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class PostgresConnector implements PostgresConn {
    private String PG_HOST;
    private String PG_DB;
    private String USER;
    private String PWD;

    Connection con = null;
    Statement stmt = null;

    public PostgresConnector(String PG_HOST, String PG_DB, String USER, String PWD) {
        this.PG_HOST = PG_HOST;
        this.PG_DB = PG_DB;
        this.USER = USER;
        this.PWD = PWD;
    }

    //---------------------------------------------------------------------------//

    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection("jdbc:postgresql://" + PG_HOST +
                    ":5432/" + PG_DB, USER, PWD);
            stmt = con.createStatement();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nErro a establecer ligação...\n");
            return false;
        }
    }

    public boolean disconnect() {
        try {
            stmt.close();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean novoEspaco(String nome, String coord) {
        // query1 -> verifica se já existe um espaco com o mesmo nome e coord
        String query1 = "SELECT EXISTS(SELECT 1 FROM ESPACO WHERE NOME = '" + nome + "' AND WHERE COORD = '" +
                coord + "')";

        // query2 -> adiciona caso a query 1 retornar false
        String query2 = "INSERT INTO ESPACO VALUES( DEFAULT, '" + nome + "', '" + coord + "')";

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            if(rs.next()) {
                if (rs.getBoolean("EXISTS")) {
                    rs.close();
                    return false;
                }
            }
            rs.close();

            System.out.println(query2);
            stmt.executeUpdate(query2);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Espaco espacoID(long id) {
        // query1 -> retorna nome e coordenadas do espaco com id
        String query1 = "SELECT NOME, COORD FROM ESPACO WHERE ID = " + id + ")";

        Espaco ret = null;

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            if(rs.next())
                ret = new Espaco(id, rs.getString("NOME"), rs.getString("COORD"));

            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public List<Espaco> espacoNome(String nome) {
        // query1 -> retorna id e coordenadas do espaco com nome
        String query1 = "SELECT ID, COORD FROM ESPACO WHERE NOME = '" + nome + "')";

        List<Espaco> ret = new LinkedList<>();
        Espaco curr = null;

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()) {
                curr = new Espaco(rs.getLong("ID"), nome, rs.getString("COORD"));
                ret.add(curr);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public List<Espaco> espacoCoord(String coord) {
        // query1 -> retorna id e nome do espaco com coord
        String query1 = "SELECT ID, NOME FROM ESPACO WHERE COORD = '" + coord + "')";

        List<Espaco> ret = new LinkedList<>();
        Espaco curr = null;

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()) {
                curr = new Espaco(rs.getLong("ID"), rs.getString("NOME"), coord);
                ret.add(curr);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public void novoRegisto(long espaco_id, String utilizador_nome) {
        // tempo atual
        TimeZone timeZone = TimeZone.getTimeZone("Portugal");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String data_atual = dateFormat.format(Calendar.getInstance(timeZone).getTime());


        // query1 -> adiciona caso a query 1 retornar false
        String query1 = "INSERT INTO REGISTO VALUES(DEFAULT, '" + data_atual + "', " + espaco_id + ",'" +
                utilizador_nome + "')";

        try {
            System.out.println(query1);
            stmt.executeUpdate(query1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Registo registoID(long id) {
        // query1 -> retorna nome e coordenadas do espaco com id
        String query1 = "SELECT DATA_PUB, ESPACO_ID, UTILIZADOR_NOME " +
                "FROM REGISTO WHERE ID = " + id + ")";

        Registo ret = null;

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            if(rs.next())
                ret = new Registo(id, rs.getLong("ESPACO_ID"),
                        rs.getString("DATA_PUB"), rs.getString("UTILIZADOR_NOME"));

            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public List<Registo> registoEspaco(long espaco_id) {
        // query1 -> retorna nome e coordenadas do espaco com id
        String query1 = "SELECT ID, DATA_PUB, UTILIZADOR_NOME " +
                "FROM REGISTO WHERE ESPACO_ID = " + espaco_id + ")";

        List<Registo> ret = new LinkedList<>();
        Registo curr = null;

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()) {
                curr = new Registo(rs.getLong("ID"), espaco_id,
                        rs.getString("DATA_PUB"), rs.getString("UTILIZADOR_NOME"));
                ret.add(curr);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public List<Registo> registoUtilizador(String utilizador_nome) {
        // query1 -> retorna nome e coordenadas do espaco com id
        String query1 = "SELECT ID, DATA_PUB, ESPACO_ID " +
                "FROM REGISTO WHERE UTILIZADOR_NOME = '" + utilizador_nome + "')";

        List<Registo> ret = new LinkedList<>();
        Registo curr = null;

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()) {
                curr = new Registo(rs.getLong("ID"), rs.getLong("ESPACO_ID"),
                        rs.getString("DATA_PUB"), utilizador_nome);
                ret.add(curr);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public boolean nomeUtilizador(String nome, String password) {
        // TODO segurança e passwords
        // query1 -> verifica se já existe um espaco com o mesmo nome e coord
        String query1 = "SELECT EXISTS(SELECT 1 FROM UTILIZADOR WHERE NOME = '" + nome + "')";

        // query2 -> adiciona caso a query 1 retornar false
        String query2 = "INSERT INTO UTILIZADOR VALUES(' " + nome + " ')";

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            if(rs.next()) {
                if (rs.getBoolean("EXISTS")) {
                    rs.close();
                    return false;
                }
            }
            rs.close();

            System.out.println(query2);
            stmt.executeUpdate(query2);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean utilizadorLogin(String nome, String password) {
        // TODO segurança e passwords
        // query1 -> verifica se já existe um espaco com o mesmo nome e coord
        String query1 = "SELECT EXISTS(SELECT 1 FROM UTILIZADOR WHERE NOME = '" + nome + "')";

        try {
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);

            if(rs.next()) {
                if (rs.getBoolean("EXISTS")) {
                    rs.close();
                    return true;
                }
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
