package so2.comunidade.dto;


public class RegistoDto {
    private String nome;
    private String coord;
    private int nivel;

    public RegistoDto() {
    }

    public RegistoDto(String nome, String coord, int nivel) {
        this.nome = nome;
        this.coord = coord;
        this.nivel = nivel;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
