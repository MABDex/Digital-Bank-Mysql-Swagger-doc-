package ebankangular.ebankangular.DTO;



import ebankangular.ebankangular.Enum.KontoStatus;
import lombok.Data;

import java.util.Date;

@Data


public class GirokontenDTO extends BankkontoDTO {

    private String id;
    private double balance;
    private Date erstellungsdatum;
    private KontoStatus status;
    private KundeDTO kundedto; // Hit hanhtajo name dyal Kund
    private double ueberziehung;

}

