package com.spring.bieb;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import domain.Auteur;
import domain.Boek;
import domain.Favoriet;
import domain.Locatie;
import jakarta.validation.Valid;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.FavorietenRepository;
import repository.LocatieRepository;
import validator.AddValidation;

@Controller
public class BibController {
	@Autowired
	private BoekRepository boekRepository;
	@Autowired
	private AddValidation addValidation;
	@Autowired
	private AuteurRepository auteurRepository;
	@Autowired
	private LocatieRepository locatieRepository;
	@Autowired
	private FavorietenRepository favorietenRepository;
	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = "/home")
	public String listBoeken(Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("userName", username);
		model.addAttribute("boekList", boekRepository.findAll());
		return "overzicht";
	}

	@GetMapping(value = "/boekdetail/{index}")
	public String boekDetail(@PathVariable Long index, Model model, Principal principal) {

		List<Favoriet> favoriteBoeken = favorietenRepository.findAllBoeksByUserName(principal.getName());
		
		Favoriet isInFavorieten = favoriteBoeken.stream()
			    .filter(b -> b.getBoek().getISBNnummer().equals(index))
			    .findFirst()
			    .orElse(null);
		Optional<Boek> boekOptional = boekRepository.findById(index);
		model.addAttribute("userName", principal.getName());
		model.addAttribute("aantalFavorieten", favoriteBoeken.size());
		model.addAttribute("isFavoriet", isInFavorieten);
		if (boekOptional.isPresent()) {
			Boek boek = boekOptional.get();
			model.addAttribute("boek", boek);
		}

		return "boekdetail";
	}

	@PostMapping(value = "/favorietenAdd/{id}")
	public String processFavorietToevoegen(@PathVariable Long id, Favoriet favoriet, BindingResult result, Model model, Principal principal) {  	    
	    if (favoriet != null) {
	        Boek boek = boekRepository.findByISBNnummer(id);
	        String naamuser = principal.getName();
	        Favoriet favorietOutput = new Favoriet(naamuser, boek);
	        String message = messageSource.getMessage("book.added.to.favorites", null, LocaleContextHolder.getLocale());
	        String textWeergave = MessageFormat.format(message, favorietOutput.getBoek().getNaam());
	        model.addAttribute("textWeergave", textWeergave);
	        favorietenRepository.save(favorietOutput);
	        boek.setAantalsterren(boek.getAantalsterren()+1);
	        boekRepository.save(boek);
	    }    
	    return "redirect:/home";
	}
	
	@PostMapping(value = "/favorietenRemove/{id}")
	public String processFavorietVerwijderen(@PathVariable Long id, Favoriet favoriet, BindingResult result, Model model, Principal principal) { 
		if (favoriet != null) {
	        Boek boek = boekRepository.findByISBNnummer(id);
	        String naamuser = principal.getName();
	        
	        Optional<Favoriet> favoriteOptional = favorietenRepository.findBoekByISBNAndUsername(boek.getISBNnummer(), principal.getName());
	        String message = messageSource.getMessage("book.added.to.favorites", null, LocaleContextHolder.getLocale());
	        String textWeergave = MessageFormat.format(message, favoriteOptional.get().getBoek().getNaam());
	        model.addAttribute("textWeergave", textWeergave);
	        boek.setAantalsterren(boek.getAantalsterren()-1);
	        favorietenRepository.delete(favoriteOptional.get());
	        
	    } 
		return "redirect:/home";
	
	}



	@GetMapping(value = "/populaireKeuzes")
	public String populaireBoeken(Model model, Principal principal) {
		model.addAttribute("userName", principal.getName());
		List<Boek> boekall = (List<Boek>) boekRepository.findAll();

		List<Boek> boekfavoriet = boekall.stream()
				.sorted(Comparator.comparingInt(Boek::getAantalsterren).reversed().thenComparing(Boek::getNaam))
				.limit(5).collect(Collectors.toList());
		model.addAttribute("boekList", boekfavoriet);

		return "populaireKeuzes";
	}

	@GetMapping(value = "/toevoegenBoek")
	public String toevoegenBoek(Model model, Authentication auth, Principal principal) {
		model.addAttribute("userName", principal.getName());
		model.addAttribute("userListRoles",
				auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
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
		return "redirect:/home";
	}
}
