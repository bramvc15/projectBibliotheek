package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private double Aankoopprijs;
	private int sterren;
	private List<Locatie> locatie;
	private String img;
	public Boek(String naam, String auteurNaam, String ISBNnr, double Aankoopprijs, int sterren, List<Locatie> locatie, String img) {
		this.naam = naam;
		this.auteurNaam = auteurNaam;
		this.ISBNnr = ISBNnr;
		this.Aankoopprijs = Aankoopprijs;
		this.sterren = sterren;
		this.img = img;
	}
}
