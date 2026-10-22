import java.util.Locale;
import java.util.Scanner;

public class Lab2_A1 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        double total = 0;
        int opt;

        do {
            System.out.println("1. Zeama - 24.50");
            System.out.println("2. Piure - 46.00");
            System.out.println("3. Salata - 18.00");
            System.out.println("4. Compot - 12.00");
            System.out.println("0. Iesire");
            System.out.print("Optiune: ");
            opt = sc.nextInt();

            switch (opt) {
                case 1: total = total + 24.50; break;
                case 2: total = total + 46.00; break;
                case 3: total = total + 18.00; break;
                case 4: total = total + 12.00; break;
                case 0: break;
                default: System.out.println("Optiune inexistenta");
            }
        } while (opt != 0);

        System.out.printf("Total: %.2f lei%n", total);

        if (total > 100) {
            total = total * 0.85; // reducere 15%
        }
        System.out.printf("De plata: %.2f lei%n", total);
    }
}