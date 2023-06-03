package perform;

import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

public class PerformRestBoek {
	private final String SERVER_URI = "http://localhost:8080";
	private WebClient webClient = WebClient.create();
	
	public PerformRestBoek() throws Exception{
		System.out.println("test");
		getAuteurBoeken("J.K. Rowling");
	}

	private void getAuteurBoeken(String auteur) {
		webClient.get().uri(SERVER_URI + "/boek/" + auteur).retrieve();
		
	}
}
