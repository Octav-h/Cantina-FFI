import java.util.Locale;

public class Lab2_C1 {

    // ========== Clasa Produs ==========
    static class Produs {
        String denumire;
        double pret;
        static int numarProduse = 0; // camp static

        Produs(String denumire, double pret) {
            this.denumire = denumire;
            this.pret = pret;
            numarProduse++; // creste la fiecare produs creat
        }

        static int getNumarProduse() {
            return numarProduse;
        }
    }

    // ========== Clasa Cantina ==========
    static class Cantina {
        static int numarBonuri = 0; // camp static

        static void emiteBon(double total) {
            numarBonuri++;
            System.out.println("Bon nr. " + numarBonuri);
            System.out.printf("Total: %.2f lei%n", total);
            System.out.println("-------------------");
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Cream 4 produse
        Produs p1 = new Produs("Zeama", 24.50);
        Produs p2 = new Produs("Piure", 46.00);
        Produs p3 = new Produs("Salata", 18.00);
        Produs p4 = new Produs("Compot", 12.00);

        System.out.println("Numar produse create: " + Produs.getNumarProduse()); // trebuie 4

        // Emitem 3 bonuri
        Cantina.emiteBon(70.50);
        Cantina.emiteBon(110.00);
        Cantina.emiteBon(46.00);
    }
}