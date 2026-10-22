import java.util.Locale;

public class Lab2_A3 {

    static void afiseazaTabelCosturi(double pret) {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("%d portii = %.2f lei%n", i, pret * i);
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("Costuri pentru Zeama (24.50):");
        afiseazaTabelCosturi(24.50);
    }
}