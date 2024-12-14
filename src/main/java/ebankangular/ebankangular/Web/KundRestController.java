package ebankangular.ebankangular.Web;



import ebankangular.ebankangular.DTO.KundeDTO;
import ebankangular.ebankangular.EigeneExceptions.CustomerNotFoundException;
import ebankangular.ebankangular.Service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j

public class KundRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/Kunden")
    public List<KundeDTO> kunden (){
        return bankAccountService.listkunde();
    }

    @GetMapping("/Kunde/{id}")
    public KundeDTO getkunde(@PathVariable(name = "id") Long kid) throws CustomerNotFoundException {
     //PathVariable(name = "id") , Bach ila 3ytna lien /kunde/hnaya haykon Nume de Id de client

        return bankAccountService.getKunde(kid) ;

    }

    @PostMapping("/saveKunde")
    public KundeDTO savekunde(@RequestBody KundeDTO kundeDTO) {
        // Methode Ajoute des clients
        /* @RequestBody permet d'indiquer à Spring que les données de notre KundeDTO
           doivent être récupérées à partir de la requête au format JSON
        */

        return  bankAccountService.saveKunde(kundeDTO);
    }

    @PutMapping("/updateKund/{kundId}")
    public KundeDTO updateKund (@PathVariable Long kundId,@RequestBody KundeDTO kundeDTO) {
        kundeDTO.setId(kundId);
        return bankAccountService.updateKunde(kundeDTO);
    }


    @DeleteMapping("/deletkund/{kundId}")
    public void deleteKund(@PathVariable Long kundId) {
        bankAccountService.deleteKunde(kundId);
    }
}
