package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Boek;


public interface BoekRepository extends CrudRepository<Boek,Long> {


}
