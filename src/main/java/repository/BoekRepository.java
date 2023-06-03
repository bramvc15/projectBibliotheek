package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Boek;


public interface BoekRepository extends CrudRepository<Boek,Long> {

	
	List<Boek> findByAuteurs_AuteurNaam(String auteur);
	Boek findByISBNnummer(Long ISBNnummer);


}
