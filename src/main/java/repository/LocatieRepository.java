package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Locatie;
import domain.User;

public interface LocatieRepository extends CrudRepository<Locatie, Long> {

}
