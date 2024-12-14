package ebankangular.ebankangular.Service;

import ebankangular.ebankangular.DTO.BankkontenoperationenDTO;
import ebankangular.ebankangular.DTO.BankkontoHistoriDTO;
import ebankangular.ebankangular.DTO.BankkontoDTO;
import ebankangular.ebankangular.DTO.GirokontenDTO;
import ebankangular.ebankangular.DTO.KundeDTO;
import ebankangular.ebankangular.DTO.SparBankkontoDTO;
import ebankangular.ebankangular.EigeneExceptions.CustomerNotFoundException;
import ebankangular.ebankangular.Enum.OperationType;
import ebankangular.ebankangular.Repositories.BankOpeartionRep;
import ebankangular.ebankangular.Repositories.BankkontoRep;
import ebankangular.ebankangular.Repositories.KundeRep;
import ebankangular.ebankangular.entities.Bankkonto;
import ebankangular.ebankangular.entities.Girokonten;
import ebankangular.ebankangular.entities.Kunde;
import ebankangular.ebankangular.entities.Sparkonten;
import ebankangular.ebankangular.entities.Bankkontenoperationen;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ebankangular.ebankangular.mappers.dtoMaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ebankangular.ebankangular.EigeneExceptions.BalanceNotEnoughException;
import ebankangular.ebankangular.EigeneExceptions.CustomerNotFoundException;
import ebankangular.ebankangular.EigeneExceptions.BankkontoNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional

//Injection des dependances a travers ces deux annotations
@AllArgsConstructor
@Slf4j


public class BankAccountServiceImpl implements BankAccountService{


    private KundeRep kundeRep;
    private BankkontoRep bankkontoRep;
    private BankOpeartionRep bankOpeartionRep;
    private dtoMaper dtoMaper;


    @Override
    public KundeDTO saveKunde(KundeDTO kundedt) {
        log.info("Speichern eines neuen Kunden");
        Kunde k = dtoMaper.FromKundeDTOreturnKunde(kundedt);
        Kunde savekund =kundeRep.save(k);

        return dtoMaper.FromKundereturnKundeDTo(savekund);
    }

    @Override
    public GirokontenDTO saveGirokonto(double initialeBalance, double ueberziehung, Long kundeID) throws CustomerNotFoundException {
        Kunde kunde1 = kundeRep.findById(kundeID).orElse(null);

        if(kunde1==null){
            throw new CustomerNotFoundException("Kunde nicht gefunden");
        }

        Girokonten gkont= new Girokonten();
        gkont.setId(UUID.randomUUID().toString());
        gkont.setErstellungsdatum(new Date());
        gkont.setBalance(initialeBalance);
        gkont.setUeberziehung(ueberziehung);
        gkont.setKunde(kunde1);

        Girokonten savedGiroKonte = bankkontoRep.save(gkont);
        return dtoMaper.FromGirokontenReturnGirokontenDTO(savedGiroKonte);
    }

    @Override
    public SparBankkontoDTO saveSparkonto(double initialeBalance, double zinsen, Long kundeID) throws CustomerNotFoundException {
        Kunde kunde1 = kundeRep.findById(kundeID).orElse(null);

        if(kunde1==null){
            throw new CustomerNotFoundException("Kunde nicht gefunden");
        }

        Sparkonten spkont= new Sparkonten();
        spkont.setId(UUID.randomUUID().toString());
        spkont.setErstellungsdatum(new Date());
        spkont.setBalance(initialeBalance);
        spkont.setZinsen(zinsen);
        spkont.setKunde(kunde1);

        Sparkonten savedsparKonte = bankkontoRep.save(spkont);
        return dtoMaper.FromSparkontenReturnSparKontoDTO(savedsparKonte);
    }


    @Override
    public List<KundeDTO> listkunde() {

        /*Spring Data JPA, gibt die Methode findAll() standardmäßig
          eine Liste (oft vom Typ List<T>) zurück.
        */
        List<Kunde> kundobjlist =kundeRep.findAll();

        // Transformation Modern on Utilsant Streams d une Liste Kunde to KundeDto
        // [.map (obj-> ...) = chaque Objet on doit le remplacer par autre Objet   ]
        // Collectors.tolist
        List<KundeDTO> kundDtolists= kundobjlist.stream().map(customer-> dtoMaper.FromKundereturnKundeDTo(customer))
                                     .collect(Collectors.toList());


       /*
        // Transformation Classique d une Liste Kunde to KundeDto
        List<KundeDTO> kundDtolists = new ArrayList<>();
        for (Kunde kd : kundobjlist ){
            KundeDTO kdto = bankKontoMaper.returnFromKunde(kd);
            kundDtolists.add(kdto);
        }
       */

        return kundDtolists;

    }

    @Override
    public BankkontoDTO getBankkonto(String accountId) throws BankkontoNotFoundException {
        Bankkonto bankkonto = bankkontoRep.findById(accountId).orElseThrow(()-> new BankkontoNotFoundException("Bankkonto nicht gefunden"));

        if(bankkonto instanceof Sparkonten){
            Sparkonten sparBankkonto = (Sparkonten) bankkonto;
            return dtoMaper.FromSparkontenReturnSparKontoDTO(sparBankkonto);
        } else {
            Girokonten girokonten  = (Girokonten) bankkonto;
            return dtoMaper.FromGirokontenReturnGirokontenDTO(girokonten);
        }
    }

    @Override
    public void debit(String accountId, double amount, String beschreibung) throws BankkontoNotFoundException , BalanceNotEnoughException {

        Bankkonto bankkonto = bankkontoRep.findById(accountId).orElseThrow(()-> new BankkontoNotFoundException("Bankkonto nicht gefunden"));

        // Si le montant inferieur que amount -> C est une Exception
        if(bankkonto.getBalance()< amount){
            throw new BalanceNotEnoughException("Dein Konto enthält nicht diese Amount");
        }

        // L operation de debit
        Bankkontenoperationen bankkonteoperation = new Bankkontenoperationen();
        bankkonteoperation.setType(OperationType.DEBIT);
        bankkonteoperation.setAmount(amount);
        bankkonteoperation.setBeschreibung(beschreibung);
        bankkonteoperation.setOperationsDatum(new Date());
        bankkonteoperation.setBankkonto(bankkonto);
        bankOpeartionRep.save(bankkonteoperation);

        // enregistrer le status Actuel de notre Compte
        bankkonto.setBalance(bankkonto.getBalance()-amount);
        bankkontoRep.save(bankkonto);
    }

    @Override
    public void credit(String accountId, double amount, String beschreibung) throws BankkontoNotFoundException , BalanceNotEnoughException {

        Bankkonto bankkonto = bankkontoRep.findById(accountId).orElseThrow(()-> new BankkontoNotFoundException("Bankkonto nicht gefunden"));

        // L operation de debit
        Bankkontenoperationen bankkonteoperation = new Bankkontenoperationen();
        bankkonteoperation.setType(OperationType.CREDIT);
        bankkonteoperation.setAmount(amount);
        bankkonteoperation.setBeschreibung(beschreibung);
        bankkonteoperation.setOperationsDatum(new Date());
        bankkonteoperation.setBankkonto(bankkonto);
        bankOpeartionRep.save(bankkonteoperation);

        // enregistrer le status Actuel de notre Compte
        bankkonto.setBalance(bankkonto.getBalance()+amount);
        bankkontoRep.save(bankkonto);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotEnoughException, BankkontoNotFoundException {
     // Virement =[ premierment retirer et apres verser]

        debit(accountIdSource , amount,"Transfer to " + accountIdDestination);

        credit(accountIdDestination ,amount , "Transfer to " + accountIdSource);

    }


    @Override
    public List<BankkontoDTO> listsBankkonton(){



        List<Bankkonto> bankcontlist =bankkontoRep.findAll();

        // Map the list of Bankkonto
        List<BankkontoDTO> bankcontlistdto = bankcontlist.stream().map(bankkonto -> {

            if (bankkonto instanceof Sparkonten) {
                Sparkonten s = (Sparkonten) bankkonto;
                return dtoMaper.FromSparkontenReturnSparKontoDTO(s);
            } else if (bankkonto instanceof Girokonten) {
                Girokonten g = (Girokonten) bankkonto;
                return dtoMaper.FromGirokontenReturnGirokontenDTO(g);
            } else {
                throw new IllegalArgumentException("Unknown account type: " + bankkonto.getClass().getName());
            }
        }).collect(Collectors.toList());


            return bankcontlistdto;
    }

    @Override
    public KundeDTO getKunde(Long kundId) throws CustomerNotFoundException {
        Kunde kobj =kundeRep.findById(kundId).orElseThrow(()->new CustomerNotFoundException("CustomerNotFound") );

        return dtoMaper.FromKundereturnKundeDTo(kobj);
    }

   // UpdateKund
    @Override
    public KundeDTO updateKunde(KundeDTO kundedt) {
        log.info("updateKunde");
        Kunde k = dtoMaper.FromKundeDTOreturnKunde(kundedt);
        Kunde savekund =kundeRep.save(k);

        return dtoMaper.FromKundereturnKundeDTo(savekund);
    }

    @Override
    public void deleteKunde(Long kundId){
        kundeRep.deleteById(kundId);
    }


    @Override
    // Historiques d opearations
    public List<BankkontenoperationenDTO> Bankkonteoperationen(String accountId){

        List<Bankkontenoperationen> kontenoperation = bankOpeartionRep.findByBankkontoId(accountId);
        return kontenoperation.stream().map(op->dtoMaper.BankkntoOperationToBankkntoOperationDTO(op))
                                .collect(Collectors.toList());


    }

    @Override
    public BankkontoHistoriDTO getKontoHistorie(String bankkontId, int page, int size) throws BankkontoNotFoundException {
        Bankkonto bk = bankkontoRep.findById(bankkontId).orElse(null);
        if (bk == null) {
            throw new BankkontoNotFoundException("Account Not found");
        }
        Page<Bankkontenoperationen> bkop =bankOpeartionRep.findByBankkontoId(bankkontId , PageRequest.of(page,size));
        BankkontoHistoriDTO kontoHistorDTO = new BankkontoHistoriDTO();
        List<BankkontenoperationenDTO> bkopdto =bkop.getContent().stream().map(op->dtoMaper.BankkntoOperationToBankkntoOperationDTO(op))
                                  .collect(Collectors.toList());
        kontoHistorDTO.setKontenoperationend(bkopdto);
        kontoHistorDTO.setKontoId(bk.getId());
        kontoHistorDTO.setBalance(bk.getBalance());
        kontoHistorDTO.setCurrentPage(page);
        kontoHistorDTO.setPagesize(size);
        kontoHistorDTO.setTotalPages(bkop.getTotalPages());
        return kontoHistorDTO;
    }


}
