package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dados.Registo;
import so2.comunidade.dto.RegistoDto;
import so2.comunidade.repository.RegistoRepository;

import java.text.SimpleDateFormat;
import java.util.*;

//TODO: Organizar metodos

@Service
public class RegistoService {

    @Autowired
    private RegistoRepository repository;
    @Autowired
    private EspacoService espacoService;

    public List<Registo> getAllRegisto() {
        return this.repository.findAll();
    }

    public List<RegistoDto> getRegistosUltimaHora() {
        List<Espaco> espacos = espacoService.getAllEspaco();
        List<RegistoDto> ret = new LinkedList<>();
        List<Registo> registos;
        Registo recente;

        for (Espaco espaco : espacos) {
            registos = getByespacoAndDateAfter(espaco.getNome());

            //caso nao tenham sido todos apagados
            if(registos.size() > 0) {
                Collections.sort(registos); //organiza lista por ordem crescente
                recente = registos.get(registos.size() - 1);    //vai buscar o mais recente (i.e. o ultimo)

                ret.add(new RegistoDto(espaco.getNome(), espaco.getCoord(), recente.printData(), recente.getNivel()));
            }
        }
        return  ret;
    }

    public List<Registo> getByespacoAndDateAfter(String espaco) {
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar hora_acores = Calendar.getInstance(tz);
        hora_acores.add(Calendar.HOUR, -1); //TODO novo nome

        return repository.getByEspacoAndDateAfter(espaco, hora_acores.getTime());
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

    public boolean createRegisto(RegistoDto registoDto) {
        //valida o registo
        if(!registoDto.valida())
            return false;

        //procura o espaco
        Espaco espaco = espacoService.getByNome(registoDto.getNome());

        //caso não exista cria um novo
        if(espaco == null)
            espacoService.createEspaco(registoDto.getNome(), registoDto.getCoord());

            //caso exista verifica se as coordenadas correspondem às anteriores
        else
        if(!espaco.getCoord().equals(registoDto.getCoord()))
            return false;

        //cria um novo registo
        Registo registo = new Registo();

        //controi a data
        try {
            String data_str = registoDto.getData();
            data_str = data_str.replace('T', ' ');
            System.out.println(data_str);
            Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(data_str);

            //usa o username
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            //cria e guarda registo
            Registo novo = new Registo(data, registoDto.getNome(), username, registoDto.getNivel());
            this.repository.save(novo);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
