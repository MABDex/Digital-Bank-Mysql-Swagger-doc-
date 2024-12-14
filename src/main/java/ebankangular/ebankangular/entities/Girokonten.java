package ebankangular.ebankangular.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Gk")

public class Girokonten extends Bankkonto{

    private double ueberziehung; // t9der twsl bih l minus max howa

}

