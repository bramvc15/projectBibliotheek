package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Favoriet;

public interface FavorietenRepository extends CrudRepository<Favoriet, Long> {
	List<Favoriet> findAllBoeksByUserName(String userName);

	@Query("SELECT f FROM Favoriet f WHERE f.boek.ISBNnummer = :ISBNnummer AND f.userName = :userName")
	Optional<Favoriet> findBoekByISBNAndUsername(@Param("ISBNnummer") Long ISBNnummer,
			@Param("userName") String userName);
}
