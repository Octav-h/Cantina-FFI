import java.util.Locale;
import java.util.Scanner;

public class Lab2_C2 {

    // Genereaza un numar aleator intre min si max
    static int genereazaNumar(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        double total = 0;

        System.out.println("1. Zeama - 24.50");
        System.out.println("2. Piure - 46.00");
        System.out.println("3. Salata - 18.00");
        System.out.println("4. Compot - 12.00");
        System.out.println("0. Termina");

        while (true) {
            System.out.print("Optiune: ");
            int opt = sc.nextInt();
            if (opt == 0) break;

            if (opt == 1) total += 24.50;
            else if (opt == 2) total += 46.00;
            else if (opt == 3) total += 18.00;
            else if (opt == 4) total += 12.00;
            else System.out.println("Optiune inexistenta");
        }

        // Ziua norocoasa
        int noroc = genereazaNumar(1, 10);
        System.out.println("Numarul norocos: " + noroc);

        if (noroc == 7) {
            System.out.println("Felicitari! Compotul e GRATUIT!");
            total = total - 12.00;
        }

        // Suma nu poate fi negativa
        total = Math.max(0, total);

        // Rotunjire
        total = Math.round(total * 100.0) / 100.0;

        System.out.printf("Total final: %.2f lei%n", total);

        // ========== Test genereazaNumar (cerinta) ==========
        System.out.println("\n--- Test 1000 de apeluri ---");
        int min = 10, max = 1, count7 = 0;
        for (int i = 0; i < 1000; i++) {
            int n = genereazaNumar(1, 10);
            if (n < min) min = n;
            if (n > max) max = n;
            if (n == 7) count7++;
        }
        System.out.println("Minim: " + min);      // trebuie 1
        System.out.println("Maxim: " + max);      // trebuie 10
        System.out.println("De cate ori a iesit 7: " + count7); // 70-130
    }
}