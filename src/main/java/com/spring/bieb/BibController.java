package com.spring.bieb;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import domain.User;
import jakarta.validation.Valid;
import repository.AuteurRepository;
import repository.AuthoritieRepository;
import repository.BoekRepository;
import repository.LocatieRepository;
import utitlity.Message;
import validator.AddValidation;

@Controller
public class BibController {
	@Autowired
	private BoekRepository boekRepository;
	@Autowired
	private AuthoritieRepository authoritieRepository;
	@Autowired
	private AddValidation addValidation;
	@Autowired
	private AuteurRepository auteurRepository;
	@Autowired
	private LocatieRepository locatieRepository;

	@GetMapping(value = "/home")
	public String listBoeken(Model model, Principal principal) {
		String username = principal.getName();

		model.addAttribute("username", username);
		model.addAttribute("boekList", boekRepository.findAll());
		return "overzicht";
	}

	

	@GetMapping(value = "/boekdetail/{index}")
	public String boekDetail(@PathVariable Long index, Model model) {
		Optional<Boek> boekOptional = boekRepository.findById(index);
		if (boekOptional.isPresent()) {
			Boek boek = boekOptional.get();
			model.addAttribute("boek", boek);
		}

		return "boekdetail";
	}

	@GetMapping(value = "/populaireKeuzes")
	public String populaireBoeken(Model model) {

		List<Boek> boekall = (List<Boek>) boekRepository.findAll();

		List<Boek> boekfavoriet = boekall.stream()
				.sorted(Comparator.comparingInt(Boek::getAantalsterren).reversed().thenComparing(Boek::getNaam)).limit(5)
				.collect(Collectors.toList());
		model.addAttribute("boekList", boekfavoriet);

		return "populaireKeuzes";
	}
	@GetMapping(value = "/toevoegenBoek")
	public String toevoegenBoek(Model model, Authentication auth, Principal principal) {
		model.addAttribute("userName", principal.getName());
		model.addAttribute("userListRoles", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
		model.addAttribute(new Boek());
		return "toevoegenBoek";
	}

	@PostMapping(value = "/toevoegenBoek")
	public String processBoekToevoegen(@Valid Boek boek, BindingResult result, Model model, Authentication auth) {
		addValidation.validate(boek, result);
		if (result.hasErrors()) {
			return "toevoegenBoek";
		}
		for (Auteur auteur : boek.getAuteurs()) {
			auteurRepository.save(auteur);
		}
		for (Locatie locatie : boek.getLocaties()) {
			locatieRepository.save(locatie);
		}
		boekRepository.save(boek);
		return "overzicht";
	}
}
