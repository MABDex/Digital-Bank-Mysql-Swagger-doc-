package ebankangular.ebankangular.Service;



import ebankangular.ebankangular.DTO.*;
import ebankangular.ebankangular.EigeneExceptions.BalanceNotEnoughException;
import ebankangular.ebankangular.EigeneExceptions.BankkontoNotFoundException;
import ebankangular.ebankangular.EigeneExceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    KundeDTO saveKunde(KundeDTO kundedt);

    // Save von Konten  -> kundeID: pour quell Client mn li deja enregitrer 3ndna a travers Methode saveKunde
    GirokontenDTO saveGirokonto(double initialeBalance, double ueberziehung, Long kundeID) throws CustomerNotFoundException;

    SparBankkontoDTO saveSparkonto(double initialeBalance, double zinsen , Long kundeID) throws CustomerNotFoundException;


    // Consulter une Liste de client
    List<KundeDTO> listkunde() ;

    // Consulter un Compte
    BankkontoDTO getBankkonto(String accountId) throws BankkontoNotFoundException;

   // Operation Debite
    void debit(String accountId, double amount , String beschreibung) throws BankkontoNotFoundException , BalanceNotEnoughException;

   // Operation de credite
   void credit(String accountId, double amount , String beschreibung)  throws BankkontoNotFoundException , BalanceNotEnoughException;

   // Effectuer un virment
   void transfer(String accountIdSource , String accountIdDestination , double amount ) throws BalanceNotEnoughException, BankkontoNotFoundException;

    List<BankkontoDTO> listsBankkonton();

    KundeDTO getKunde(Long kundId) throws CustomerNotFoundException;

    // UpdateKund
    KundeDTO updateKunde(KundeDTO kundedt);

    void deleteKunde(Long kundId);


    // Historiques d opearations
    List<BankkontenoperationenDTO> Bankkonteoperationen(String accountId);


    BankkontoHistoriDTO getKontoHistorie(String kundId , int page , int size) throws BankkontoNotFoundException;
}
