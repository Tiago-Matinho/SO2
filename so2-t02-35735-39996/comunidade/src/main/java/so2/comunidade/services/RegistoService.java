package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Registo;
import so2.comunidade.repository.RegistoRepository;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class RegistoService {

    @Autowired
    private RegistoRepository repository;

    public List<Registo> getAllRegisto() {
        return this.repository.findAll();
    }

    public List<Registo> getByEspacoAndDateAfter(long espaco) {
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar hora_acores = Calendar.getInstance(tz);
        hora_acores.add(Calendar.HOUR, -1);

        return repository.getByEspacoAndDateAfter(espaco, hora_acores.getTime());
    }

    public Registo findById(long id) {
        return this.repository.findById(id);
    }

    public List<Registo> getByUtilizador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repository.getByUtilizador(authentication.getName());
    }

    public void removeRegisto(long id) {
        this.repository.deleteById(id);
    }

    public String createRegisto(long espaco, int nivel) {
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar data_atual = Calendar.getInstance(tz);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getName());

        Registo novo = new Registo(data_atual.getTime(), espaco, authentication.getName(), nivel);
        this.repository.save(novo);
        return "Adicionado com sucesso: " + novo.getId();

    }
}
