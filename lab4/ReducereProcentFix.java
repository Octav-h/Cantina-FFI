package lab4;

import lab3.Comanda;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** Procent fix: 5 + N%5 → pentru N=10 → 5% */
public class ReducereProcentFix implements RegulaReducere {
    public static final int PROCENT = 5; // 5 + 10%5 = 5

    @Override
    public BigDecimal calculeazaReducere(Comanda comanda) {
        BigDecimal subtotal = comanda.subtotal();
        return subtotal.multiply(BigDecimal.valueOf(PROCENT))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDenumire() {
        return "Procent fix " + PROCENT + "%";
    }
}
