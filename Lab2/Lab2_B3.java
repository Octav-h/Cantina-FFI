import java.util.Locale;
import java.util.Scanner;

public class Lab2_B3 {

    static double pretProdus(int opt) {
        if (opt == 1) return 24.50;
        if (opt == 2) return 46.00;
        if (opt == 3) return 18.00;
        if (opt == 4) return 12.00;
        return 0;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        double total = 0;

        // maximum 5 pozitii
        for (int i = 1; i <= 5; i++) {
            System.out.println("1. Zeama - 24.50");
            System.out.println("2. Piure - 46.00");
            System.out.println("3. Salata - 18.00");
            System.out.println("4. Compot - 12.00");
            System.out.println("0. Termina comanda");
            System.out.print("Optiune: ");

            int opt = sc.nextInt();

            if (opt == 0) {
                break; // iesim din for
            }

            double pret = pretProdus(opt);
            if (pret == 0) {
                System.out.println("Optiune inexistenta");
                continue; // trece la urmatoarea iteratie
            }

            total = total + pret;
            System.out.println("Adaugat. Total curent: " + total);
        }

        System.out.printf("Total final: %.2f lei%n", total);
    }
}