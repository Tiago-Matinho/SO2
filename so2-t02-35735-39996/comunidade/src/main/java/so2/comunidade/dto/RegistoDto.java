package so2.comunidade.dto;


import java.text.SimpleDateFormat;

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

        // verificar coordenadas
        if(!coord.contains(","))
            return false;
        String[] coordSplited = coord.split(",", 2);
        try {
            float lat = Float.parseFloat(coordSplited[0]);
            float lng = Float.parseFloat(coordSplited[1]);
            if(lat < -90 || lat > 90)
                return false;
            if(lng < -180 || lng > 180) // ref: https://stackoverflow.com/questions/15965166/what-is-the-maximum-length-of-latitude-and-longitude
                return false;
        }
        catch (Exception e) {
            return false;
        }

        // verificar data
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            df.parse(data + " " + hora);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("xeeee");
            return false;
        }

        return nivel >= 1 && nivel <= 4;
    }
}
