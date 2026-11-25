package lab4;

/**
 * Capacitate: produsul are termen de prospețime / valabilitate.
 * Interfață pentru că e un comportament comun la produse din ramuri diferite
 * (rulouri, nigiri, extra), fără a forța o legătură de moștenire.
 */
public interface Proaspat {
    int getZileValabilitate();

    default boolean esteIncaProaspat(int zileDeLaPreparare) {
        return zileDeLaPreparare <= getZileValabilitate();
    }
}
