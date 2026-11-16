package lab3;

/**
 * Al doilea nivel pe ramura Bautura → BauturaCalda
 */
public class BauturaCalda extends Bautura {
    private final int temperaturaC;
    private final boolean areMiere;

    public BauturaCalda(String denumire, double pretBaza, int stoc, int volumMl,
                        int temperaturaC, boolean areMiere) {
        super(denumire, pretBaza, stoc, false, volumMl); // ceaiul/cafeaua nu e alcoolică
        if (temperaturaC < 40 || temperaturaC > 100) {
            throw new IllegalArgumentException("Temperatura trebuie să fie între 40 și 100°C.");
        }
        this.temperaturaC = temperaturaC;
        this.areMiere = areMiere;
    }

    public int getTemperaturaC() {
        return temperaturaC;
    }

    public boolean isAreMiere() {
        return areMiere;
    }

    @Override
    public double pretFinal() {
        double pret = super.pretFinal();
        if (areMiere) {
            pret += 3.0; // miere +3 MDL
        }
        return pret;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %d°C%s", temperaturaC, areMiere ? " + miere" : "");
    }
}
