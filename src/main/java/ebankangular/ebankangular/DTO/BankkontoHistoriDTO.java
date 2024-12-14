package ebankangular.ebankangular.DTO;

import lombok.Data;


import java.util.List;

@Data
public class BankkontoHistoriDTO {

 private String kontoId;
 private double balance;
 private int currentPage;
 private int totalPages;
 private int Pagesize;
 private List<BankkontenoperationenDTO> kontenoperationend;



}
