package Lab3;

public class Rulou extends Produs {
    private final NivelPicanterie picanterie;
    private final int nrBucati; // câte bucăți într-un set

    public Rulou(String denumire, double pretBaza, int stoc, NivelPicanterie picanterie, int nrBucati) {
        super(denumire, pretBaza, stoc);
        if (picanterie == null) {
            throw new IllegalArgumentException("Nivelul de picanterie este obligatoriu.");
        }
        if (nrBucati <= 0) {
            throw new IllegalArgumentException("Numărul de bucăți trebuie să fie pozitiv.");
        }
        this.picanterie = picanterie;
        this.nrBucati = nrBucati;
    }

    public NivelPicanterie getPicanterie() {
        return picanterie;
    }

    public int getNrBucati() {
        return nrBucati;
    }

    @Override
    public double pretFinal() {
        return super.pretFinal() * picanterie.getCoeficient();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | %d buc.", picanterie, nrBucati);
    }
}
