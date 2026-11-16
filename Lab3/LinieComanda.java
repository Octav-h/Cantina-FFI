package Lab3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Linie dintr-o comandă. Constructorul este package-private (nu public)
 * pentru a forța crearea exclusiv prin Comanda.adauga(...).
 */
class LinieComanda {
    private final Produs produs;
    private int cantitate;

    LinieComanda(Produs produs, int cantitate) {
        if (produs == null) {
            throw new IllegalArgumentException("Produsul nu poate fi null.");
        }
        if (cantitate <= 0) {
            throw new IllegalArgumentException("Cantitatea trebuie să fie pozitivă.");
        }
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public Produs getProdus() {
        return produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    void adaugaCantitate(int extra) {
        if (extra > 0) {
            this.cantitate += extra;
        }
    }

    public BigDecimal subtotal() {
        BigDecimal pretUnitar = BigDecimal.valueOf(produs.pretFinal());
        return pretUnitar.multiply(BigDecimal.valueOf(cantitate))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return String.format("%s x%d = %.2f MDL", produs.getDenumire(), cantitate, subtotal());
    }
}
