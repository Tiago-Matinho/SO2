package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dados.Registo;
import so2.comunidade.dto.NiveisDto;
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

    private final TimeZone tz = TimeZone.getTimeZone("Portugal");
    private final Calendar hora_acores = Calendar.getInstance(tz);

/************************************************************************************/

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

        //controi a data
        try {
            String data_str = registoDto.getData() + " " + registoDto.getHora();
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

    public void removeRegisto(long id) {
        Registo registo = repository.findById(id);

        if(registo == null)
            return;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!registo.getUtilizador().equals(username))
            return;

        this.repository.deleteById(id);
    }

    public List<Registo> getAllRegisto() {
        return this.repository.findAll();
    }

    public List<RegistoDto> pesquisaNome(String nome_espaco) {
        Calendar calendario = hora_acores;
        calendario.add(Calendar.HOUR, -1);

        List<Espaco> espacos = espacoService.getEspacoContaining(nome_espaco);
        if(espacos.isEmpty())
            return null;

        List<Registo> registos;
        Registo recente;
        List<RegistoDto> ret = new LinkedList<>();

        for (Espaco espaco : espacos) {
            registos = repository.getByEspacoAndDateAfter(espaco.getNome(), calendario.getTime());

            //caso nao tenham sido todos apagados
            if(registos.size() > 0) {
                Collections.sort(registos); //organiza lista por ordem crescente
                recente = registos.get(registos.size() - 1);    //vai buscar o mais recente (i.e. o ultimo)
                ret.add(new RegistoDto(recente.getId(), espaco.getNome(), espaco.getCoord(),
                        recente.printData(), recente.printHora(), recente.getNivel()));
            }
        }

        System.out.println(ret);
        return ret;
    }

    public NiveisDto getByespacoAndDateAfter(String nome_espaco) {
        Calendar calendario = hora_acores;
        calendario.add(Calendar.HOUR, -1);

        Espaco espaco = espacoService.getByNome(nome_espaco);
        if(espaco == null)
            return null;

        List<Registo> registos = repository.getByEspacoAndDateAfter(nome_espaco, calendario.getTime());
        int grau1 = 0;
        int grau2 = 0;
        int grau3 = 0;
        int grau4 = 0;

        for (Registo registo : registos) {
            switch(registo.getNivel()) {
                case 1:
                    grau1++;
                    break;
                case 2:
                    grau2++;
                    break;
                case 3:
                    grau3++;
                    break;
                case 4:
                    grau4++;
                    break;
                default:
                    break;
            }
        }
        return new NiveisDto(nome_espaco, espaco.getCoord(), grau1, grau2, grau3, grau4);
    }

    public List<RegistoDto> getRegistosUltimaHora() {
        //tempo
        Calendar calendario = hora_acores;
        calendario.add(Calendar.HOUR, -1);

        List<Espaco> espacos = espacoService.getAllEspaco();
        List<RegistoDto> ret = new LinkedList<>();
        List<Registo> registos;
        Registo recente;

        for (Espaco espaco : espacos) {
            registos = repository.getByEspacoAndDateAfter(espaco.getNome(), calendario.getTime());

            //caso nao tenham sido todos apagados
            if(registos.size() > 0) {
                Collections.sort(registos); //organiza lista por ordem crescente
                recente = registos.get(registos.size() - 1);    //vai buscar o mais recente (i.e. o ultimo)

                ret.add(new RegistoDto(recente.getId(), espaco.getNome(), espaco.getCoord(),
                        recente.printData(), recente.printHora(), recente.getNivel()));
            }
        }
        return  ret;
    }

    public List<RegistoDto> getByUtilizador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Registo> registos = repository.getByUtilizador(authentication.getName());
        List<RegistoDto> ret = new LinkedList<>();
        Espaco espaco;

        for(Registo registo : registos) {
            espaco = espacoService.getByNome(registo.getEspaco());
            ret.add(new RegistoDto(registo.getId(), espaco.getNome(), espaco.getCoord(),
                    registo.printData(), registo.printHora(), registo.getNivel()));
        }

        return ret;
    }

}
