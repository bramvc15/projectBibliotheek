package com.spring.bieb;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.User;
import repository.BoekRepository;


@Controller
public class BibController {
	@Autowired
	private BoekRepository boekRepository;

	
	@GetMapping(value = "/home")
	public String listBoeken(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("boekList", boekRepository.findAll());
		return "overzicht";
	}
	
	
}
