package Lab3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private final List<LinieComanda> linii = new ArrayList<>();
    private BigDecimal reducereExtra = BigDecimal.ZERO; // pentru regula proprie

    public boolean adauga(Produs p, int cantitate) {
        if (p == null || cantitate <= 0) {
            System.out.println("  [REFUZ] Date invalide pentru adăugare.");
            return false;
        }
        if (p.getStoc() < cantitate) {
            System.out.println("  [REFUZ] Stoc insuficient pentru \"" + p.getDenumire()
                    + "\" (disponibil: " + p.getStoc() + ", cerut: " + cantitate + ")");
            return false;
        }

        // Caută dacă produsul există deja (folosește equals pe id)
        for (LinieComanda linie : linii) {
            if (linie.getProdus().equals(p)) {
                // Mărește cantitatea existentă
                if (!p.scadeStoc(cantitate)) {
                    System.out.println("  [REFUZ] Nu s-a putut scădea stocul.");
                    return false;
                }
                linie.adaugaCantitate(cantitate);
                return true;
            }
        }

        // Linie nouă
        if (!p.scadeStoc(cantitate)) {
            System.out.println("  [REFUZ] Nu s-a putut scădea stocul.");
            return false;
        }
        linii.add(new LinieComanda(p, cantitate));
        return true;
    }

    public List<LinieComanda> getLinii() {
        return new ArrayList<>(linii); // copie defensivă
    }

    public void setReducereExtra(BigDecimal reducereExtra) {
        this.reducereExtra = reducereExtra != null ? reducereExtra : BigDecimal.ZERO;
    }

    public BigDecimal getReducereExtra() {
        return reducereExtra;
    }

    public BigDecimal subtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (LinieComanda l : linii) {
            total = total.add(l.subtotal());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal total() {
        BigDecimal t = subtotal().subtract(reducereExtra);
        if (t.compareTo(BigDecimal.ZERO) < 0) {
            t = BigDecimal.ZERO;
        }
        return t.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean esteGoala() {
        return linii.isEmpty();
    }

    @Override
    public String toString() {
        if (linii.isEmpty()) {
            return "(comandă goală)";
        }
        StringBuilder sb = new StringBuilder();
        for (LinieComanda l : linii) {
            sb.append("  - ").append(l).append("\n");
        }
        sb.append(String.format("  Subtotal: %.2f MDL", subtotal()));
        if (reducereExtra.compareTo(BigDecimal.ZERO) > 0) {
            sb.append(String.format("\n  Reducere: -%.2f MDL", reducereExtra));
        }
        sb.append(String.format("\n  TOTAL: %.2f MDL", total()));
        return sb.toString();
    }
}
