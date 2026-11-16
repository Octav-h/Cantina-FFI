package Lab3;

public class Extra extends Produs {
    private final boolean esteVegetarian;

    public Extra(String denumire, double pretBaza, int stoc, boolean esteVegetarian) {
        super(denumire, pretBaza, stoc);
        this.esteVegetarian = esteVegetarian;
    }

    public boolean isVegetarian() {
        return esteVegetarian;
    }

    @Override
    public double pretFinal() {
        // extrasurile vegetariene sunt puțin mai ieftine în acest stand
        return super.pretFinal() * (esteVegetarian ? 0.95 : 1.0);
    }

    @Override
    public String toString() {
        return super.toString() + (esteVegetarian ? " | vegetarian" : " | non-veg");
    }
}
