package so2.comunidade.dados;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissao")
public class Permissao {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "cargo")
    private String cargo;

    public Permissao() {
    }

    public Permissao(String username, String cargo) {
        this.username = username;
        this.cargo = cargo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "username='" + username + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
