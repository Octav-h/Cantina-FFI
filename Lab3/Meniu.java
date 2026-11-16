package Lab3;

import java.util.ArrayList;
import java.util.List;

/**
 * Agregare: meniul primește produse create în afara lui.
 * Produsele există independent de meniu.
 */
public class Meniu {
    private final String denumire;
    private final List<Produs> produse = new ArrayList<>();

    public Meniu(String denumire) {
        if (denumire == null || denumire.trim().isEmpty()) {
            throw new IllegalArgumentException("Denumirea meniului este obligatorie.");
        }
        this.denumire = denumire.trim();
    }

    public void adaugaProdus(Produs p) {
        if (p != null && !produse.contains(p)) {
            produse.add(p);
        }
    }

    public String getDenumire() {
        return denumire;
    }

    public List<Produs> getProduse() {
        return new ArrayList<>(produse);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Meniu \"" + denumire + "\":\n");
        for (Produs p : produse) {
            sb.append("  • ").append(p.getDenumire())
              .append(" (final: ").append(String.format("%.2f", p.pretFinal())).append(")\n");
        }
        return sb.toString();
    }
}
