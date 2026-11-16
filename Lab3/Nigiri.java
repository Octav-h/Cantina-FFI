package Lab3;

public class Nigiri extends Produs {
    private final String tipPeste;
    private final boolean esteSashimi; // true = fără orez

    public Nigiri(String denumire, double pretBaza, int stoc, String tipPeste, boolean esteSashimi) {
        super(denumire, pretBaza, stoc);
        if (tipPeste == null || tipPeste.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipul de pește este obligatoriu.");
        }
        this.tipPeste = tipPeste.trim();
        this.esteSashimi = esteSashimi;
    }

    public String getTipPeste() {
        return tipPeste;
    }

    public boolean isSashimi() {
        return esteSashimi;
    }

    @Override
    public double pretFinal() {
        // Sashimi e puțin mai scump (doar peștele, dar porție mai mare de pește)
        double coef = esteSashimi ? 1.25 : 1.0;
        return super.pretFinal() * coef;
    }

    @Override
    public String toString() {
        String tip = esteSashimi ? "Sashimi" : "Nigiri";
        return super.toString() + " | " + tip + " " + tipPeste;
    }
}
