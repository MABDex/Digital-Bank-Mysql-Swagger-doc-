package ebankangular.ebankangular.entities;



import ebankangular.ebankangular.Enum.KontoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

//Single Table:
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE" , length = 4 , discriminatorType = DiscriminatorType.STRING)


/* Table per Class :
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
*/

/*
@Inheritance(strategy = InheritanceType.JOINED)
*/

public abstract class Bankkonto {

    @Id
    private String id;
    private double balance;
    private Date erstellungsdatum;

    private KontoStatus status; // enum package

    @ManyToOne
    // Un compte appartient a un client [ManyToOne] teil lwl dyla l class Donc Many dyal Bankkonto
    private Kunde kunde;

    @OneToMany (mappedBy = "bankkonto" , fetch = FetchType.EAGER)
    // Un compte peut avoir plusieurs Opearations
    private List<Bankkontenoperationen> bankkontenoperatons;



}
