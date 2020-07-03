package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Registo;
import so2.comunidade.repository.RegistoRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//TODO: Organizar metodos

@Service
public class RegistoService {

    @Autowired
    private RegistoRepository repository;

    public List<Registo> getAllRegisto() {
        return this.repository.findAll();
    }

    public List<Registo> getByNome_espacoAndDateAfter(String nome_espaco) {
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar hora_acores = Calendar.getInstance(tz);
        hora_acores.add(Calendar.HOUR, -1); //TODO novo nome

        return repository.getByNome_espacoAndDateAfter(nome_espaco, hora_acores.getTime());
    }

    public Registo findById(long id) {
        return this.repository.findById(id);
    }

    public List<Registo> getByUtilizador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repository.getByUtilizador(authentication.getName());
    }

    public void removeRegisto(long id) {
        Registo registo = repository.findById(id);

        if(registo == null)
            return;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!registo.getUtilizador().equals(username))
            return;

        this.repository.deleteById(id);
    }

    public void createRegisto(Date data, String nome_espaco, int nivel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        System.out.println("Novo registo feito por: " + username);

        Registo novo = new Registo(data, nome_espaco, username, nivel);
        this.repository.save(novo);
    }
}
