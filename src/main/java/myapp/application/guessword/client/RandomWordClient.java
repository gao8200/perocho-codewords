package myapp.application.guessword.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RandomWordClient {
  
	public String getWord(String url) throws URISyntaxException {
		System.out.println("Enter: RandomWordClient.getWord(): calling "+url+"(api) to get random word");
		RestClient restClient = RestClient.create();
		String word[] = restClient.get().uri(new URI(url)).retrieve().body(String[].class);
		return word[0];
	}
	
}
