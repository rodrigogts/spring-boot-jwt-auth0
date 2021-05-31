package expertostech.password.encrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootApplication
public class PasswordEncryptApplication {

	@Value("${pbkdf2.pepper}")
	private String pepper;

	@Value("${pbkdf2.iterations}")
	private int iterations;

	@Value("${pbkdf2.hash.width}")
	private int hashWidth;

	public static void main(String[] args) {
		SpringApplication.run(PasswordEncryptApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
		pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
		return pbkdf2PasswordEncoder;
	}
}
