package Lab3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * MeniuCombo este un Produs (moștenire) și totodată ARE produse (compoziție/agregare).
 * Preț final = suma prețurilor finale ale componentelor minus reducerea personală.
 */
public class MeniuCombo extends Produs {
    // Reducerea personală: 10 + N%6  → pentru N=10 → 14%
    public static final int REDUCERE_COMBO_PROCENT = 14;

    private final List<Produs> componente = new ArrayList<>();

    public MeniuCombo(String denumire, List<Produs> componenteInitiale) {
        // stocul combo-ului = minimul stocurilor componentelor (semnificație: câte combo-uri se pot face)
        super(denumire, 0.0, calculeazaStocMinim(componenteInitiale));
        if (componenteInitiale == null || componenteInitiale.isEmpty()) {
            throw new IllegalArgumentException("Un combo trebuie să aibă cel puțin o componentă.");
        }
        for (Produs p : componenteInitiale) {
            if (p != null) {
                this.componente.add(p);
            }
        }
        // actualizăm prețul de bază ca sumă a prețurilor de bază (doar informativ)
        double sumaBaza = 0;
        for (Produs p : this.componente) {
            sumaBaza += p.getPretBaza();
        }
        this.pretBaza = sumaBaza;
    }

    private static int calculeazaStocMinim(List<Produs> lista) {
        if (lista == null || lista.isEmpty()) return 0;
        int min = Integer.MAX_VALUE;
        for (Produs p : lista) {
            if (p != null && p.getStoc() < min) {
                min = p.getStoc();
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public List<Produs> getComponente() {
        return new ArrayList<>(componente);
    }

    /**
     * Verifică dacă toate componentele mai au stoc.
     */
    public boolean esteDisponibil() {
        for (Produs p : componente) {
            if (p.getStoc() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double pretFinal() {
        double suma = 0;
        for (Produs p : componente) {
            suma += p.pretFinal();
        }
        // aplică reducerea personală
        double dupaReducere = suma * (100 - REDUCERE_COMBO_PROCENT) / 100.0;
        return BigDecimal.valueOf(dupaReducere)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * La vânzarea unui combo scădem stocul fiecărei componente cu 1.
     */
    @Override
    public boolean scadeStoc(int cantitate) {
        if (!esteDisponibil() || cantitate <= 0) {
            return false;
        }
        // Verificăm din nou stocurile
        for (Produs p : componente) {
            if (p.getStoc() < cantitate) {
                return false;
            }
        }
        for (Produs p : componente) {
            p.scadeStoc(cantitate);
        }
        // actualizăm și stocul „virtual” al combo-ului
        this.stoc = calculeazaStocMinim(componente);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format(" | COMBO (-%d%%)", REDUCERE_COMBO_PROCENT));
        sb.append("\n    Componente:");
        for (Produs p : componente) {
            sb.append("\n      → ").append(p.getDenumire())
              .append(" (").append(String.format("%.2f", p.pretFinal())).append(")");
        }
        return sb.toString();
    }
}
