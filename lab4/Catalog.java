package lab4;

import lab3.Produs;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Catalog polimorf: lucrează doar cu tipul Produs (sau interfețe).
 * Nu apare niciun nume de subclasă concretă.
 */
public class Catalog {
    private final List<Produs> produse = new ArrayList<>();

    public void adauga(Produs p) {
        if (p != null) {
            produse.add(p);
        }
    }

    public List<Produs> getProduse() {
        return new ArrayList<>(produse);
    }

    /** Afișează toate produsele: categorie, preț bază, preț final. */
    public void afiseaza() {
        System.out.println("--- Catalog stand ---");
        for (Produs p : produse) {
            System.out.printf("  [%s] %s | bază: %.2f | final: %.2f | stoc: %d%n",
                    p.categorie(), p.getDenumire(), p.getPretBaza(), p.pretFinal(), p.getStoc());
        }
    }

    /** Suma (preț final × stoc) calculată cu BigDecimal. */
    public BigDecimal valoareStoc() {
        BigDecimal total = BigDecimal.ZERO;
        for (Produs p : produse) {
            BigDecimal linie = BigDecimal.valueOf(p.pretFinal())
                    .multiply(BigDecimal.valueOf(p.getStoc()));
            total = total.add(linie);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /** Produsele dintr-o categorie dată (folosește metoda abstractă categorie()). */
    public List<Produs> dinCategoria(String categorie) {
        List<Produs> rezultat = new ArrayList<>();
        for (Produs p : produse) {
            if (p.categorie().equalsIgnoreCase(categorie)) {
                rezultat.add(p);
            }
        }
        return rezultat;
    }

    /**
     * Prime co k produse după criteriul dat.
     * Nu depinde de clase concrete.
     */
    public List<Produs> top(int k, Comparator<Produs> criteriu) {
        return produse.stream()
                .sorted(criteriu)
                .limit(k)
                .collect(Collectors.toList());
    }
}
