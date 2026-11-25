package lab4;

/**
 * Capacitate: produsul poate fi personalizat cu ingrediente suplimentare.
 * Interfață (nu clasă abstractă) pentru că descrie un comportament opțional,
 * nu o relație „este-un”.
 */
public interface Personalizabil {
    void adaugaIngredient(String ingredient);

    /** Metodă default – disponibilă pentru toate clasele care implementează interfața. */
    default void adaugaSosStandard() {
        adaugaIngredient("sos de soia");
    }

    String getIngredienteExtra();
}
