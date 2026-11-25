package lab4;

import lab3.Comanda;
import java.math.BigDecimal;

/**
 * Interfață pentru reguli de reducere interschimbabile.
 * Pentru a permite lambda, metoda getDenumire are implementare default.
 */
public interface RegulaReducere {
    /**
     * Calculează valoarea reducerii pentru comanda dată.
     * @return suma care se scade din total (nu totalul redus)
     */
    BigDecimal calculeazaReducere(Comanda comanda);

    default String getDenumire() {
        return "Regulă fără nume";
    }
}
