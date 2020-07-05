package so2.comunidade.dto;

public class EspacoDto {
    private String nome;

    public EspacoDto() {
    }

    public EspacoDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "EspacoDto{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
