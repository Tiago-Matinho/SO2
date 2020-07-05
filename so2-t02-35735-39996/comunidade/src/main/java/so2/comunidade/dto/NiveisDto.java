package so2.comunidade.dto;

public class NiveisDto {
    private int grau1;
    private int grau2;
    private int grau3;
    private int grau4;

    public NiveisDto(int grau1, int grau2, int grau3, int grau4) {
        this.grau1 = grau1;
        this.grau2 = grau2;
        this.grau3 = grau3;
        this.grau4 = grau4;
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
                "grau1=" + grau1 +
                ", grau2=" + grau2 +
                ", grau3=" + grau3 +
                ", grau4=" + grau4 +
                '}';
    }
}
