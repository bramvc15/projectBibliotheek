package com.spring.bieb;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Boek;
import domain.User;
import repository.AuthoritieRepository;
import repository.BoekRepository;

@Controller
public class BibController {
	@Autowired
	private BoekRepository boekRepository;
	@Autowired
	private AuthoritieRepository authoritieRepository;

	@GetMapping(value = "/home")
	public String listBoeken(Model model, Principal principal) {
		String username = principal.getName();

		model.addAttribute("username", username);
		model.addAttribute("boekList", boekRepository.findAll());
		return "overzicht";
	}

	@GetMapping(value = "/toevoegenBoek")
	public String toevoegenBoek(Model model) {
		return "toevoegenBoek";
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
				.sorted(Comparator.comparingInt(Boek::getSterren).reversed().thenComparing(Boek::getNaam)).limit(5)
				.collect(Collectors.toList());
		model.addAttribute("boekList", boekfavoriet);

		return "populaireKeuzes";
	}

}
