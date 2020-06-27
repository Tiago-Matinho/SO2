package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so2.comunidade.repository.RegistoRepository;
import so2.comunidade.dados.Registo;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class RegistoController {
    @Autowired
    RegistoRepository repository;

    @GetMapping("/registo/bulkcreate")
    public String bulkcreate() {

        Registo registo;

        registo = new Registo(new Date("2020-6-27 17:00"), 1, "Tiago", 1);
        repository.save(registo);
        registo = new Registo(new Date("2020-6-27 12:00"), 1, "Tiago", 2);
        repository.save(registo);

        return "Registos criados";
    }

    @GetMapping("/registo/findall")
    public List<Registo> findAll(){
        return repository.findAll();
    }

    @GetMapping("/registo/id={id}")
    public List<Registo> findById(@PathVariable("id") long id){
        return repository.findById(id);
    }

    @GetMapping("/registo/ei={espacoId}")
    public List<Registo> findByEspaco_id(@PathVariable("espacoId") long espacoId){
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar hora_acores = Calendar.getInstance(tz);
        hora_acores.add(Calendar.HOUR, -1);

        return repository.findByEspacoAndDateAfter(espacoId, hora_acores.getTime());
    }


}
