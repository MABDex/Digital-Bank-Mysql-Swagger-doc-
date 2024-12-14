package ebankangular.ebankangular.Repositories;



import ebankangular.ebankangular.entities.Bankkontenoperationen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankOpeartionRep extends JpaRepository<Bankkontenoperationen, Long> {

   // Kan3tih id dyal compte kay3tini resulte dyal ga3 les opearation li dar had compte
   public List <Bankkontenoperationen> findByBankkontoId(String Kontoid);


   Page<Bankkontenoperationen> findByBankkontoId(String Kontoid, Pageable p);

}
