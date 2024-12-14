package ebankangular.ebankangular.EigeneExceptions;

public class CustomerNotFoundException extends Exception {


    /*
       Ila khdmna b extends RuntimeException Hatkon Exception non surveillée
       [ y3ni mathtajch dir lih Try catch ola throws]

       Exception non surveillée : un événement inattendu survient dans un programme et qu'il n'existe
       aucun mécanisme pour gérer cette erreur.
       */



   // Ms hna hna khdmna b extends Exception , donc haykhsna ndiro ima try/catch ola throws
    public CustomerNotFoundException(String message){
        super(message);
    }
}
