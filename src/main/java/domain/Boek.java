package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Getter @Setter 
//@EqualsAndHashCode(exclude = "ISBNnummer")
@NoArgsConstructor
@AllArgsConstructor
public class Boek implements Serializable{
    private static final long serialVersionUID = 1L;
    @NotNull
    @Id
    @Digits(integer = 13, fraction = 0, message = "ISBNnummer moet 13 cijfers bevatten")
    private Long ISBNnummer;
    @NotEmpty 
    @NotBlank
    @JsonProperty("boek_naam")
    private String naam;
    @ManyToMany
    private List<Auteur> auteurs;
    @NotNull 
    @Min(1) 
    @Max(99)
    private double aankoopPrijs;
    private int aantalsterren;
    
    @ManyToMany
    private List<Locatie> locaties;
    @NotEmpty @NotBlank
    private String img;
    
}
