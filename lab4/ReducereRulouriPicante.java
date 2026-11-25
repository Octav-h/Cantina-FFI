package lab4;

import lab3.Comanda;
import lab3.LinieComanda;
import lab3.NivelPicanterie;
import lab3.Produs;
import lab3.Rulou;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Regula proprie a standului (din Lab 3):
 * Dacă există cel puțin 2 rulouri PICANT / EXTRA_PICANT → -10% din subtotal.
 */
public class ReducereRulouriPicante implements RegulaReducere {

    @Override
    public BigDecimal calculeazaReducere(Comanda comanda) {
        int rulouriPicante = 0;
        for (LinieComanda l : comanda.getLinii()) {
            Produs p = l.getProdus();
            if (p instanceof Rulou) {
                NivelPicanterie niv = ((Rulou) p).getPicanterie();
                if (niv == NivelPicanterie.PICANT || niv == NivelPicanterie.EXTRA_PICANT) {
                    rulouriPicante += l.getCantitate();
                }
            }
        }
        if (rulouriPicante >= 2) {
            return comanda.subtotal()
                    .multiply(new BigDecimal("0.10"))
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDenumire() {
        return "≥2 rulouri picante (-10%)";
    }
}
