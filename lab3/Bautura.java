package lab3;

public class Bautura extends Produs {
    private final boolean esteAlcoolica;
    private final int volumMl;

    public Bautura(String denumire, double pretBaza, int stoc, boolean esteAlcoolica, int volumMl) {
        super(denumire, pretBaza, stoc);
        if (volumMl <= 0) {
            throw new IllegalArgumentException("Volumul trebuie să fie pozitiv.");
        }
        this.esteAlcoolica = esteAlcoolica;
        this.volumMl = volumMl;
    }

    public boolean isAlcoolica() {
        return esteAlcoolica;
    }

    public int getVolumMl() {
        return volumMl;
    }

    @Override
    public double pretFinal() {
        // băuturile alcoolice au un mic adaos
        return super.pretFinal() * (esteAlcoolica ? 1.10 : 1.0);
    }

    @Override
    public String toString() {
        String tip = esteAlcoolica ? "alc." : "fără alc.";
        return super.toString() + String.format(" | %d ml (%s)", volumMl, tip);
    }
}
