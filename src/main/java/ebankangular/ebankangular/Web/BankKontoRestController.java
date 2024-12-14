package ebankangular.ebankangular.Web;



import ebankangular.ebankangular.DTO.BankkontenoperationenDTO;
import ebankangular.ebankangular.DTO.BankkontoDTO;
import ebankangular.ebankangular.DTO.BankkontoHistoriDTO;
import ebankangular.ebankangular.EigeneExceptions.BankkontoNotFoundException;
import ebankangular.ebankangular.Service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class BankKontoRestController {

    private BankAccountService bankAccountService;


    // Consulter un Compte
    @GetMapping("/konto/{KontoId}")
     public BankkontoDTO getbankkonto (@PathVariable String KontoId) throws  BankkontoNotFoundException {
        return bankAccountService.getBankkonto(KontoId);
    }


    @GetMapping("/AllKontos")
    public List<BankkontoDTO> getAllbankkontos(){
        return bankAccountService.listsBankkonton();
    }


    @GetMapping("/konto/{KontoID}/operations")
    public List<BankkontenoperationenDTO> getbankkontoOperationen(@PathVariable String KontoID) throws BankkontoNotFoundException {
        return bankAccountService.Bankkonteoperationen(KontoID);
    }


    @GetMapping("/konto/{kontoID}/pageOperations")
    public BankkontoHistoriDTO getKontHistori(@PathVariable String kontoID,
                                              @RequestParam(name = "page" , defaultValue = "0") int page ,
                                              @RequestParam(name = "size" , defaultValue = "5")  int size) throws BankkontoNotFoundException {

        return bankAccountService.getKontoHistorie(kontoID, page, size);
    }

}
