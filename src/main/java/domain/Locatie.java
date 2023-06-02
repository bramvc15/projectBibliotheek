package domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locatie implements Serializable{

	private String plaatscode1;
	private String plaatscode2;
	private String plaatsnaam;
	
	public Locatie(String plaatscode1, String plaatscode2, String plaatsnaam) {
		this.plaatscode1 = plaatscode1;
		this.plaatscode2 = plaatscode2;
		this.plaatsnaam = plaatsnaam;
	}
}
