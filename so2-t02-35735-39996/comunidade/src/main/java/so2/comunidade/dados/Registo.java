package so2.comunidade.dados;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registo")
public class Registo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "espaco")
    private long espaco;
    @Column(name = "utilizador_nome")
    private String utilizador_nome;
    @Column(name = "nivel")
    private int nivel;

    public Registo() {
    }

    public Registo(Date date, long espaco, String utilizador_nome, int nivel) {
        this.date = date;
        this.espaco = espaco;
        this.utilizador_nome = utilizador_nome;
        this.nivel = nivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getEspaco() {
        return espaco;
    }

    public void setEspaco(long espaco) {
        this.espaco = espaco;
    }

    public String getUtilizador_nome() {
        return utilizador_nome;
    }

    public void setUtilizador_nome(String utilizador_nome) {
        this.utilizador_nome = utilizador_nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Registo{" +
                "id=" + id +
                ", date=" + date +
                ", espaco=" + espaco +
                ", utilizador_nome='" + utilizador_nome + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
