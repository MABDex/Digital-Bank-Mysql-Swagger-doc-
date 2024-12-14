package ebankangular.ebankangular.DTO;



import ebankangular.ebankangular.Enum.OperationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class BankkontenoperationenDTO {

    private long id;
    private Date operationsDatum;
    private double amount;
    private OperationType type; // enum package
    private String beschreibung;


}
