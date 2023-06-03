package domain;

import java.io.Serializable;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@NoArgsConstructor
public class Favoriet implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    @ManyToOne
    private Boek boek;

    public Favoriet(String userName, Boek boek) {
        this.userName = userName;
        this.boek = boek;
    }
}
