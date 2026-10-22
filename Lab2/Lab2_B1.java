import java.util.Locale;
import java.util.Scanner;

public class Lab2_B1 {

    // Returneaza true daca cantina e deschisa
    static boolean esteDeschis(int ziua, int ora) {
        // luni-vineri (1-5) intre 8 si 16
        // sambata (6) intre 9 si 13
        return (ziua >= 1 && ziua <= 5 && ora >= 8 && ora < 16)
                || (ziua == 6 && ora >= 9 && ora < 13);
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Ziua (1=luni ... 7=duminica): ");
        int ziua = sc.nextInt();
        System.out.print("Ora (0-23): ");
        int ora = sc.nextInt();

        if (esteDeschis(ziua, ora)) {
            System.out.println("Cantina este DESCHISA. Puteti comanda.");
        } else {
            System.out.println("Cantina este INCHISA. Nu se accepta comenzi.");
        }

        // Teste cerute
        System.out.println("\n--- Teste ---");
        System.out.println("(3, 8)  = " + esteDeschis(3, 8));   // true
        System.out.println("(6, 12) = " + esteDeschis(6, 12));  // true
        System.out.println("(3, 16) = " + esteDeschis(3, 16));  // false
        System.out.println("(6, 13) = " + esteDeschis(6, 13));  // false
        System.out.println("(7, 10) = " + esteDeschis(7, 10));  // false
    }
}