package so2.comunidade.dados;

public class Registo {
    private Long id, espaco_id;
    private String data_pub, utilizador_nome;

    public Registo(Long id, Long espaco_id, String data_pub, String utilizador_nome) {
        this.id = id;
        this.espaco_id = espaco_id;
        this.data_pub = data_pub;
        this.utilizador_nome = utilizador_nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEspaco_id() {
        return espaco_id;
    }

    public void setEspaco_id(Long espaco_id) {
        this.espaco_id = espaco_id;
    }

    public String getdata_pub() {
        return data_pub;
    }

    public void setdata_pub(String data_pub) {
        this.data_pub = data_pub;
    }

    public String getUtilizador_nome() {
        return utilizador_nome;
    }

    public void setUtilizador_nome(String utilizador_nome) {
        this.utilizador_nome = utilizador_nome;
    }

    @Override
    public String toString() {
        return "Registo{" +
                "id=" + id +
                ", espaco_id=" + espaco_id +
                ", data_pub='" + data_pub + '\'' +
                ", utilizador_nome='" + utilizador_nome + '\'' +
                '}';
    }
}
