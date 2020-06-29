package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Registo> findByEspacoAndDateAfter(long espaco) {
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar hora_acores = Calendar.getInstance(tz);
        hora_acores.add(Calendar.HOUR, -1);

        return repository.findByEspacoAndDateAfter(espaco, hora_acores.getTime());
    }

    public Registo findById(long id) {
        return this.repository.findById(id);
    }
}
