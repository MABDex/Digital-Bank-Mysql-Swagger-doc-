package ebankangular.ebankangular.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@DiscriminatorValue("Sk")

@Entity
public class Sparkonten extends Bankkonto {

   private double zinsen;

}
