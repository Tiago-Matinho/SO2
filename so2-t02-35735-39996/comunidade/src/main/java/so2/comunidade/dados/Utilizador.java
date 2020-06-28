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


}
