package lab3;

public enum NivelPicanterie {
    NORMAL(1.0),
    PICANT(1.15),
    EXTRA_PICANT(1.30);

    private final double coeficient;

    NivelPicanterie(double coeficient) {
        this.coeficient = coeficient;
    }

    public double getCoeficient() {
        return coeficient;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace('_', ' ');
    }
}
