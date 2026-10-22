import java.util.Locale;

public class Lab2_B2 {

    // constanta TVA
    static final double TVA = 0.20;

    // varianta 1: pret + portii (cu TVA)
    static double calculeazaCost(double pret, int portii) {
        return pret * portii * (1 + TVA);
    }

    // varianta 2: pret + portii + bursier (reducere 15% inainte de TVA)
    static double calculeazaCost(double pret, int portii, boolean bursier) {
        double baza = pret * portii;
        if (bursier) {
            baza = baza * 0.85; // reducere 15%
        }
        return baza * (1 + TVA);
    }

    // varianta 3: primeste un obiect Produs (folosim pretul din el)
    // (daca nu ai clasa Produs aici, lasam simplu cu double)
    static double calculeazaCost(double pret, int portii, String produs) {
        return calculeazaCost(pret, portii);
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Test 1: fara reducere
        double cost1 = calculeazaCost(46.00, 2);
        System.out.printf("calculeazaCost(46.00, 2) = %.2f%n", cost1); // 110.40

        // Test 2: cu reducere bursier
        double cost2 = calculeazaCost(46.00, 2, true);
        System.out.printf("calculeazaCost(46.00, 2, true) = %.2f%n", cost2); // 93.84

        // Test 3: apel cu int (functioneaza datorita conversiei int -> double)
        double cost3 = calculeazaCost(46, 2);
        System.out.printf("calculeazaCost(46, 2) = %.2f%n", cost3); // 110.40
    }
}