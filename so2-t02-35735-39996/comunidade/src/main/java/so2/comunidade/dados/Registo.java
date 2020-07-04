package so2.comunidade.dados;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "registo")
public class Registo implements Comparable<Registo> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "espaco")
    private String espaco;
    @Column(name = "utilizador")
    private String utilizador;
    @Column(name = "nivel")
    private int nivel;

    public Registo() {
    }

    public Registo(Date date, String espaco, String utilizador, int nivel) {
        this.date = date;
        this.espaco = espaco;
        this.utilizador = utilizador;
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

    public String getEspaco() {
        return espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
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
                ", espaco='" + espaco + '\'' +
                ", utilizador='" + utilizador + '\'' +
                ", nivel=" + nivel +
                '}';
    }

    @Override
    public int compareTo(Registo r) {
        return date.compareTo(r.date);
    }

    public String printData() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sf.format(date);
    }
}
