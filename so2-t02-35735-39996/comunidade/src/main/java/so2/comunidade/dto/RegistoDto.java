package so2.comunidade.dto;


public class RegistoDto {
    private long id;
    private String nome;
    private String coord;
    private String data;
    private String hora;
    private int nivel;

    public RegistoDto() {
    }

    public RegistoDto(long id, String nome, String coord, String data, String hora, int nivel) {
        this.id = id;
        this.nome = nome;
        this.coord = coord;
        this.data = data;
        this.hora = hora;
        this.nivel = nivel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", coord='" + coord + '\'' +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", nivel=" + nivel +
                '}';
    }

    public String dataCompleta() {
        return data + " " + hora;
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
