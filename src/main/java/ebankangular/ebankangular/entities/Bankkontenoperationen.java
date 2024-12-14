package ebankangular.ebankangular.entities;



import ebankangular.ebankangular.Enum.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Bankkontenoperationen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date operationsDatum;
    private double amount;

    private OperationType type; // enum package

    @ManyToOne
    // les opérations concerne un seul compte
    private Bankkonto bankkonto;

    private String beschreibung ;


}