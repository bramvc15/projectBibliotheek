package com.spring.bieb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import domain.Boek;
import repository.BoekRepository;

@RestController
public class restController {

	@Autowired
	BoekRepository boekRepository;
	
	@GetMapping(value="/boek/{auteur}")
	public List<Boek> getBoekenVanAuteur(@PathVariable String auteur) {
		return boekRepository.findByAuteurs_AuteurNaam(auteur);
	}
	@GetMapping(value="/boekOpISBN/{ISBN}")
	public Boek getBoekenVanAuteur(@PathVariable Long ISBN) {
		return boekRepository.findByISBNnummer(ISBN);
	}
}
