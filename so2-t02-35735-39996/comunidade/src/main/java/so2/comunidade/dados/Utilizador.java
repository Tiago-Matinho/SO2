package so2.comunidade.dados;

import javax.persistence.*;

@Entity
@Table(name = "utilizador")
public class Utilizador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "pwdEncript")
    private String pwdEncript;

    public Utilizador() {
    }

    public Utilizador(String nome, String pwdEncript) {
        this.nome = nome;
        this.pwdEncript = pwdEncript;
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

    public String getPwdEncript() {
        return pwdEncript;
    }

    public void setPwdEncript(String pwdEncript) {
        this.pwdEncript = pwdEncript;
    }

}
