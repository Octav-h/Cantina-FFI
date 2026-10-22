import java.util.Locale;
import java.util.Scanner;

public class Lab2_A2 {

    static void afiseazaMeniu() {
        System.out.println("1. Zeama - 24.50");
        System.out.println("2. Piure - 46.00");
        System.out.println("3. Salata - 18.00");
        System.out.println("4. Compot - 12.00");
        System.out.println("0. Iesire");
    }

    static double pretProdus(int opt) {
        if (opt == 1) return 24.50;
        if (opt == 2) return 46.00;
        if (opt == 3) return 18.00;
        if (opt == 4) return 12.00;
        return 0; // optiune invalida
    }

    static double aplicaReducere(double total) {
        if (total > 100) {
            return total * 0.85;
        }
        return total;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        double total = 0;
        int opt;

        do {
            afiseazaMeniu();
            System.out.print("Optiune: ");
            opt = sc.nextInt();

            if (opt == 0) break;

            double pret = pretProdus(opt);
            if (pret == 0) {
                System.out.println("Optiune inexistenta");
            } else {
                total = total + pret;
            }
        } while (true);

        System.out.printf("Total: %.2f%n", total);
        System.out.printf("De plata: %.2f%n", aplicaReducere(total));
        System.out.println("Test pretProdus(9) = " + pretProdus(9));
    }
}