package so2.comunidade.dto;

public class NiveisDto {
    private String nome;
    private String coord;
    private int grau1;
    private int grau2;
    private int grau3;
    private int grau4;

    public NiveisDto() {
    }

    public NiveisDto(String nome, String coord, int grau1, int grau2, int grau3, int grau4) {
        this.nome = nome;
        this.coord = coord;
        this.grau1 = grau1;
        this.grau2 = grau2;
        this.grau3 = grau3;
        this.grau4 = grau4;
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

    public int getGrau1() {
        return grau1;
    }

    public void setGrau1(int grau1) {
        this.grau1 = grau1;
    }

    public int getGrau2() {
        return grau2;
    }

    public void setGrau2(int grau2) {
        this.grau2 = grau2;
    }

    public int getGrau3() {
        return grau3;
    }

    public void setGrau3(int grau3) {
        this.grau3 = grau3;
    }

    public int getGrau4() {
        return grau4;
    }

    public void setGrau4(int grau4) {
        this.grau4 = grau4;
    }

    @Override
    public String toString() {
        return "NiveisDto{" +
                "nome='" + nome + '\'' +
                ", coord='" + coord + '\'' +
                ", grau1=" + grau1 +
                ", grau2=" + grau2 +
                ", grau3=" + grau3 +
                ", grau4=" + grau4 +
                '}';
    }
}
