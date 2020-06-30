package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Permissao;
import so2.comunidade.dados.Utilizador;
import so2.comunidade.repository.PermissaoRepository;
import so2.comunidade.repository.UtilizadorRepository;

@Service
public class UtilizadorService {
    @Autowired
    private UtilizadorRepository utilizadorRepository;
    @Autowired
    private PermissaoRepository permissaoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUtilizador(String username, String password) {
        if(this.utilizadorRepository.findByUsername(username) != null)
            return false;

        Utilizador novo = new Utilizador();
        novo.setUsername(username);
        novo.setPassword(this.passwordEncoder.encode(password));
        novo.setEnabled(true);

        this.utilizadorRepository.save(novo);

        Permissao permissao = new Permissao();

        permissao.setCargo("USER");
        permissao.setUsername(username);
        if(username.equals("tiago") || username.equals("joao") || username.equals("jose"))
            permissao.setCargo("ADMIN");

        this.permissaoRepository.save(permissao);
        return true;
    }

}
