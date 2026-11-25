package lab4;

import lab3.Comanda;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** Reducere dacă totalul depășește pragul: 100 + 10*(N%4) → N=10 → 120 lei. */
public class ReducerePestePrag implements RegulaReducere {
    public static final BigDecimal PRAG = new BigDecimal("120.00");
    public static final BigDecimal REDUCERE_FIXA = new BigDecimal("15.00");

    @Override
    public BigDecimal calculeazaReducere(Comanda comanda) {
        BigDecimal subtotal = comanda.subtotal();
        if (subtotal.compareTo(PRAG) >= 0) {
            return REDUCERE_FIXA;
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDenumire() {
        return "Peste prag " + PRAG + " lei (-" + REDUCERE_FIXA + ")";
    }
}
