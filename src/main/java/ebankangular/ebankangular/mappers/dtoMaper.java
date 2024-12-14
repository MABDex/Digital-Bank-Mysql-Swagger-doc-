package ebankangular.ebankangular.mappers;



import ebankangular.ebankangular.DTO.BankkontenoperationenDTO;
import ebankangular.ebankangular.DTO.GirokontenDTO;
import ebankangular.ebankangular.DTO.KundeDTO;
import ebankangular.ebankangular.DTO.SparBankkontoDTO;
import ebankangular.ebankangular.entities.Bankkontenoperationen;
import ebankangular.ebankangular.entities.Girokonten;
import ebankangular.ebankangular.entities.Kunde;
import ebankangular.ebankangular.entities.Sparkonten;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class dtoMaper {


    //###################### Kunde Mapper##########################################
    public KundeDTO FromKundereturnKundeDTo(Kunde kunde) {
        KundeDTO kundeDTO = new KundeDTO();
        /*
        // Premier possibilite
        kundeDTO.setId(kunde.getId());
        kundeDTO.setVorname(kunde.getVorname());
        kundeDTO.setEmail(kunde.getEmail());
         */

        //Deuxième possibilité : Utilise BeanUtils.copyProperties(Object source , Object target)
        BeanUtils.copyProperties(kunde,kundeDTO);
        return kundeDTO;
    }

    public Kunde FromKundeDTOreturnKunde(KundeDTO kundedto) {
        Kunde kunde = new Kunde();
        BeanUtils.copyProperties(kundedto,kunde);
        return kunde;
    }



    //######################### BankKontosMapper [SparKonto / Girokonto] ###############################

            // A) SparKonto------------------
    public SparBankkontoDTO FromSparkontenReturnSparKontoDTO(Sparkonten sparkonten){

        SparBankkontoDTO sparBankkontoDTO = new SparBankkontoDTO();
        BeanUtils.copyProperties(sparkonten,sparBankkontoDTO);

        // Hna makhaskch tnsa kunde li 3ndk tzido manuellment
        sparBankkontoDTO.setKundedto(FromKundereturnKundeDTo(sparkonten.getKunde()));
        sparBankkontoDTO.setBankkontoType(sparkonten.getClass().getSimpleName());

        return sparBankkontoDTO;

    }


    public Sparkonten FromSparKontoDTOReturnSparKonten(SparBankkontoDTO sparkontdto){
    Sparkonten sparkonten = new Sparkonten();
    BeanUtils.copyProperties(sparkontdto,sparkonten);
    sparkonten.setKunde(FromKundeDTOreturnKunde(sparkontdto.getKundedto()));
    return sparkonten;

    }

         // B) Girokonto---------------------------------------

    public GirokontenDTO FromGirokontenReturnGirokontenDTO(Girokonten girokonten){
        GirokontenDTO girokontenDTO = new GirokontenDTO();
        BeanUtils.copyProperties(girokonten,girokontenDTO);
        girokontenDTO.setKundedto(FromKundereturnKundeDTo(girokonten.getKunde()));
        girokontenDTO.setBankkontoType(girokonten.getClass().getSimpleName());
        return girokontenDTO;
    }


    public Girokonten FromGirokontenDTOReturnGirokonten(GirokontenDTO girokontdto){

        Girokonten girokonten = new Girokonten();
        BeanUtils.copyProperties(girokontdto,girokonten);
        girokonten.setKunde(FromKundeDTOreturnKunde(girokontdto.getKundedto()));
        return girokonten;
    }




    //######################### BankKontosoperation Mapper  ###############################

    public BankkontenoperationenDTO BankkntoOperationToBankkntoOperationDTO(Bankkontenoperationen bankkontenoperationen){

        BankkontenoperationenDTO BankkntoOperationDTO = new BankkontenoperationenDTO();
        BeanUtils.copyProperties(bankkontenoperationen,BankkntoOperationDTO);
        return BankkntoOperationDTO;
    }



}
