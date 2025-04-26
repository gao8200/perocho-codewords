package myapp.application.guessword.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game")
public class GameConfig {
	
	
	private String randomApiEndpoint;
	private String[] randomWords;
	private int maxAttempt;
	 
	public int getMaxAttempt() {
		return maxAttempt;
	}

	public void setMaxAttempt(int maxAttempt) {
		this.maxAttempt = maxAttempt;
	}

	public String getRandomApiEndpoint() {
		return randomApiEndpoint;
	}

	public void setRandomApiEndpoint(String randomApiEndpoint) {
		this.randomApiEndpoint = randomApiEndpoint;
	}

	public String[] getRandomWords() {
		return randomWords;
	}

	public void setRandomWords(String[] randomWords) {
		this.randomWords = randomWords;
	}
	
	

}
