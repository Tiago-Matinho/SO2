package so2.comunidade.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import so2.comunidade.dados.Utilizador;

public class UtilizadorDto implements Validator {

    @Override
    public void validate(Object target, Errors errors) {
        Utilizador utilizador = (Utilizador) target;
        String username = utilizador.getUsername();


    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Utilizador.class.isAssignableFrom(clazz);
    }
}
