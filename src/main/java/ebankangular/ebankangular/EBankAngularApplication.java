package ebankangular.ebankangular;

import ebankangular.ebankangular.DTO.BankkontoDTO;
import ebankangular.ebankangular.DTO.GirokontenDTO;
import ebankangular.ebankangular.DTO.KundeDTO;
import ebankangular.ebankangular.DTO.SparBankkontoDTO;
import ebankangular.ebankangular.EigeneExceptions.BalanceNotEnoughException;
import ebankangular.ebankangular.EigeneExceptions.BankkontoNotFoundException;
import ebankangular.ebankangular.EigeneExceptions.CustomerNotFoundException;

import ebankangular.ebankangular.Service.BankAccountService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.List;

import java.util.stream.Stream;


@SpringBootApplication
public class EBankAngularApplication {


    public static void main(String[] args) {
        SpringApplication.run(EBankAngularApplication .class, args);
    }

    // Seulment pour Tester
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, StringHttpMessageConverter stringHttpMessageConverter) {
        return args -> {

            Stream.of("Hassan" , "Iman" , "Mohamed").forEach(nam->{

                KundeDTO kundedt = new KundeDTO();
                kundedt.setVorname("Hassan");
                kundedt.setEmail("Hassen" + "@gmail.com");
                bankAccountService.saveKunde(kundedt);


            });

            bankAccountService.listkunde().forEach(kun->{

                try {
                    bankAccountService.saveGirokonto(Math.random()+1200, 9000 , kun.getId());
                    bankAccountService.saveSparkonto(Math.random()+130287 , 5.5 , kun.getId());

                }
                // Hadi dyal saveKonto
                catch (CustomerNotFoundException e) {e.printStackTrace();}
            });

            List<BankkontoDTO> bankkontos= bankAccountService.listsBankkonton();
            for (BankkontoDTO bk : bankkontos){
                String kontoid;
                if (bk instanceof SparBankkontoDTO){
                    kontoid = ((SparBankkontoDTO) bk).getId();
                }else{
                    kontoid= ((GirokontenDTO) bk).getId();
                }

                try {
                    bankAccountService.credit(kontoid, 1221+Math.random()*121,"Credit Opeartion");
                    bankAccountService.debit(kontoid, 1221+Math.random()*121,"Debit Opeartion");
                }
                catch (BalanceNotEnoughException e){
                    throw new RuntimeException(e);
                }
                catch (BankkontoNotFoundException e){
                    throw new RuntimeException(e);
                }
            }




        };
    }


    }