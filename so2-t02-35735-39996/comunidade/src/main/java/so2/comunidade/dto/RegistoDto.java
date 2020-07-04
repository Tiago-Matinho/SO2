package so2.comunidade.dto;


import java.util.Date;

public class RegistoDto {
    private String nome;
    private String coord;
    private String data;
    private int nivel;

    public RegistoDto() {
    }

    public RegistoDto(String nome, String coord, String data, int nivel) {
        this.nome = nome;
        this.coord = coord;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "RegistoDto{" +
                "nome='" + nome + '\'' +
                ", coord='" + coord + '\'' +
                ", data='" + data + '\'' +
                ", nivel=" + nivel +
                '}';
    }

    public boolean valida() {
        if(nome.isBlank())
            return false;
        if(nome.length() > 50)
            return false;
        //TODO verificar coordenadas
        //verificar data
        return nivel >= 1 && nivel <= 4;
    }
}
