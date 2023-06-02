package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity @Getter @Setter
@EqualsAndHashCode(exclude = "id")
@ToString(exclude="id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Boek implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String naam;
	private String auteurNaam;
	private String ISBNnr;
	private double aankoopprijs;
	private int sterren;
	private String img;
	@ElementCollection
	private List<Locatie> locatie;
	public Boek(String naam, String auteurNaam, String ISBNnr, double aankoopprijs, int sterren, List<Locatie> locatie, String img) {
		this.naam = naam;
		this.auteurNaam = auteurNaam;
		this.ISBNnr = ISBNnr;
		this.aankoopprijs = Math.round(aankoopprijs * 100.0) / 100.0;;
		this.locatie = locatie;
		this.sterren = sterren;
		this.img = img;
	}
}
