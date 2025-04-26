package myapp.application.guessword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import myapp.application.guessword.config.GameConfig;

@SpringBootApplication
@EnableConfigurationProperties(GameConfig.class)
public class GuesswordApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuesswordApplication.class, args);
	}

}
