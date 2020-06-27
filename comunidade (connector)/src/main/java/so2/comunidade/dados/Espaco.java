package so2.comunidade.dados;

public class Espaco {
    private Long id;
    private String nome, coord;

    public Espaco(Long id, String nome, String coord) {
        this.id = id;
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
