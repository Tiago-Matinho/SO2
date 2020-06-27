package so2.comunidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.security.Security;

@SpringBootApplication(proxyBeanMethods = false)
public class ComunidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComunidadeApplication.class, args);
	}

}
