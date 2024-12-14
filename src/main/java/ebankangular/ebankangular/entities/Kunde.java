package ebankangular.ebankangular.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Kunde {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vorname;
    private String email;

    @OneToMany(mappedBy = "kunde") // Beziehung zu Bankkonto

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //jackson Api Haydir Ignore La serialisation dyal Had Attribut Sinon
    // Haydir lik wahd boucle hit relation biderictionel
    private List<Bankkonto> bankkontos;

}
