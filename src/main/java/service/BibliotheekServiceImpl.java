package service;

import org.springframework.beans.factory.annotation.Autowired;

import repository.BoekRepository;

public class BibliotheekServiceImpl implements BibliotheekService{
	@Autowired
	private BoekRepository boekRepository;
}
