package so2.comunidade.dados;

import javax.persistence.*;

@Entity
@Table(name = "espaco")
public class Espaco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "coord")
    private String coord;

    public Espaco() {
    }

    public Espaco(String nome, String coord) {
        this.nome = nome;
        this.coord = coord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "Espaco{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", coord='" + coord + '\'' +
                '}';
    }
}
