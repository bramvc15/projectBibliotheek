package com.spring.bieb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Authoritie;
import domain.Boek;
import domain.User;
import domain.Locatie;
import repository.AuthoritieRepository;
import repository.BoekRepository;
import repository.GebruikerRepository;

@Component
public class InitDataConfig implements CommandLineRunner {
	@Autowired
	private BoekRepository boekRepository;
	@Autowired
	private GebruikerRepository gebruikerRepository;
	@Autowired
	private AuthoritieRepository authoritieRepository;

	@Override
	public void run(String... args) {
		
		List<Locatie> locaties = List.of(new Locatie("250","200","StandaardBoekhandel"),new Locatie("100","245","BiebAalst"));
		List<Locatie> locaties2 = List.of(new Locatie("250","200","StandaardBoekhandel"),new Locatie("250","200","StandaardBoekhandel"));
		
		List<Boek> boeken = List.of(
		new Boek("To Kill a Mockingbird", "Harper Lee", "9780446310789", 12.99, 5, locaties,
				"https://encyclopediaofalabama.org/wp-content/uploads/2023/02/m-2908.jpg"),
		new Boek("The Catcher in the Rye", "J.D. Salinger", "9780316769174", 9.99, 3, locaties,
				"https://m.media-amazon.com/images/I/91HPG31dTwL.jpg"),
		new Boek("1984", "George Orwell", "9780451524935", 10.99, 2, locaties,
				"https://kbimages1-a.akamaihd.net/a5312ed2-bc80-4f4c-972b-c24dc5990bd5/353/569/90/False/george-orwell-1984-4.jpg"),
		new Boek("Pride and Prejudice", "Jane Austen", "9780141439518", 6.99, 4, locaties,
				"https://kbimages1-a.akamaihd.net/afcd8653-3b27-4423-bee9-570fb1441aed/353/569/90/False/pride-and-prejudice-71.jpg"),
		new Boek("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 8.99, 5, locaties,
				"https://kbimages1-a.akamaihd.net/c5742da9-e63f-4ed5-acb6-074fab5e3f41/1200/1200/False/the-great-gatsby-11.jpg"),
		new Boek("Brave New World", "Aldous Huxley", "9780060850524", 11.99, 2, locaties,
				"https://media.s-bol.com/Rw73j1xDn94R/5Q8JK5v/547x840.jpg"),
		new Boek("Lord of the Flies", "William Golding", "9780571295715", 10.99, 1, locaties,
				"https://m.media-amazon.com/images/I/81WUAoL-wFL._AC_UF1000,1000_QL80_.jpg"),
		new Boek("The Hobbit", "J.R.R. Tolkien", "9780547928227", 14.99, 3, locaties,
				"https://m.media-amazon.com/images/I/710+HcoP38L._AC_UF1000,1000_QL80_.jpg"),
		new Boek("The Fellowship of the Ring", "J.R.R. Tolkien", "9780618346257", 16.99, 2, locaties,
				"https://kbimages1-a.akamaihd.net/7a557cb3-f72a-47c3-992b-951c9566e4d4/1200/1200/False/the-fellowship-of-the-ring-the-lord-of-the-rings-book-1-1.jpg"),
		new Boek("The Two Towers", "J.R.R. Tolkien", "9780618346264", 16.99, 4, locaties,
				"https://images.booksense.com/images/245/380/9780358380245.jpg"),
		new Boek("The Return of the King", "J.R.R. Tolkien", "9780618346271", 16.99, 5, locaties2,
				"https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1654216226i/61215384.jpg"),
		new Boek("The Lord of the Rings", "J.R.R. Tolkien", "9780544003415", 26.99, 3, locaties2,
				"https://m.media-amazon.com/images/I/71jLBXtWJWL._AC_UF1000,1000_QL80_.jpg"),
		new Boek("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780590353427", 12.99, 6,
				locaties2,
				"https://kbimages1-a.akamaihd.net/6750d058-29cb-4626-9c12-a62e816a80cc/1200/1200/False/harry-potter-and-the-philosopher-s-stone-3.jpg"),
		new Boek("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "9780439064873", 13.99, 4,
				locaties2,"https://kbimages1-a.akamaihd.net/a659c00c-c0d1-4c2c-bf55-316bf227a6b0/353/569/90/False/harry-potter-and-the-chamber-of-secrets-5.jpg"),
		new Boek("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "9780439136365", 14.99, 2,
				locaties2,"https://m.media-amazon.com/images/I/81f7bXC-tTL.jpg"),
		new Boek("Harry Potter and the Goblet of Fire", "J.K. Rowling", "9780439139601", 15.99, 1, locaties2,"https://m.media-amazon.com/images/I/810jKiNChxL.jpg"),
		new Boek("Harry Potter and the Order of Phoenix", "J.K. Rowling", "9780439358071", 16.99, 5,
				locaties2,"https://m.media-amazon.com/images/I/516c41oodRL._AC_UF1000,1000_QL80_.jpg")
		);
		User g1 = new User("admin", "$2y$10$xT2EKeAP.Ey84iy5dOwuOe5hxtRhvGVk6aLIpgAIpAzzu8xfJWPpO", true);
		User g2 = new User("bram", "$2y$10$E.442wS/c9QXLpkLcXaOY.Bet9jTm/aoOUi65yvtuvmJuBJJu1KcG", true);
		Authoritie a1 = new Authoritie("admin", "ROLE_ADMIN");
		Authoritie a2 = new Authoritie("bram", "ROLE_USER");
		

		for (Boek boek : boeken) {
			boekRepository.save(boek);
		}
		gebruikerRepository.save(g1);
		gebruikerRepository.save(g2);
		authoritieRepository.save(a1);
		authoritieRepository.save(a2);

	}

}
