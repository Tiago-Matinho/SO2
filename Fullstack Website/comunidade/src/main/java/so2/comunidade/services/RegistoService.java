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

@Service
public class RegistoService {

    @Autowired
    private RegistoRepository repository;
    @Autowired
    private EspacoService espacoService;


/************************************************************************************/

    /*
    * Ao criar um novo registo verifica-se primeiro se os dados inseridos sao validos,
    * de seguida procura-se um espaço que tenha o mesmo nome e coordenadas. Uma vez
    * que só pode existir um lugar com um determinado nome as coordenadas do espaço
    * inserido têm de coincidir. Caso nao exista um espaco com esse nome cria um novo.
    * Por fim adiciona o novo registo usando o username de quem estiver autenticado.
    */
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

    // Remove um registo pelo seu ID
    public void removeRegisto(long id) {
        Registo registo = repository.findById(id);

        if(registo == null)
            return;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!registo.getUtilizador().equals(username))
            return;

        this.repository.deleteById(id);
    }

    /*
    * Procura os registos de espaços com nomes que contenham a string inserida. Serve para
    * uma procura de espaços pelo nome pouco sofisticada.
    *
    * Apenas retorna os registos efectuados na ultima hora.
    * */
    public List<RegistoDto> pesquisaNome(String nome_espaco) {
        // vai buscar a hora de portugal
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar calendario =  Calendar.getInstance(tz);
        calendario.add(Calendar.HOUR, -1);

        List<Espaco> espacos = espacoService.getEspacoContaining(nome_espaco);
        if(espacos.isEmpty())
            return null;

        List<Registo> registos;
        Registo recente;
        List<RegistoDto> ret = new LinkedList<>();

        // precorre os espacos com nomes parecidos
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
        return ret;
    }

    /*
    * Procura os registos para um espaço dado e conta o numero de ocorrencias do mesmo nivel na
    * ultima hora.
    * */
    public NiveisDto getByespacoAndDateAfter(String nome_espaco) {
        // vai buscar a hora de portugal
        TimeZone tz = TimeZone.getTimeZone("Portugal");
        Calendar calendario =  Calendar.getInstance(tz);
        calendario.add(Calendar.HOUR, -1);

        Espaco espaco = espacoService.getByNome(nome_espaco);
        if(espaco == null)  //caso o espaço nao exista
            return null;

        List<Registo> registos = repository.getByEspacoAndDateAfter(nome_espaco, calendario.getTime());
        int grau1 = 0;
        int grau2 = 0;
        int grau3 = 0;
        int grau4 = 0;

        // conta as ocorrencias
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

    /*
    * Retorna uma lista dos ultimos registos deitos em cada espaço. Independentemente se foram
    * feitos na ultima hora ou nao.
    * */
    public List<RegistoDto> getUltimosRegistos() {
        List<Espaco> espacos = espacoService.getAllEspaco();
        List<RegistoDto> ret = new LinkedList<>();
        List<Registo> registos;
        Registo recente;

        // precorre os espaços
        for (Espaco espaco : espacos) {
            registos = repository.getByEspaco(espaco.getNome());

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

    /*
    * Retorna uma lista dos registos feitos pelo utilizador autenticado.
    * */
    public List<RegistoDto> getByUtilizador() {
        // vai buscar o username do utilizador
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Registo> registos = repository.getByUtilizador(authentication.getName());  //procura pelo nome

        List<RegistoDto> ret = new LinkedList<>();
        Espaco espaco;

        //precorre os registos e cria uma lista de RegistosDto
        for(Registo registo : registos) {
            espaco = espacoService.getByNome(registo.getEspaco());
            ret.add(new RegistoDto(registo.getId(), espaco.getNome(), espaco.getCoord(),
                    registo.printData(), registo.printHora(), registo.getNivel()));
        }

        return ret;
    }

}
