import java.util.Locale;

public class Lab2_C3 {

    // Clasa Produs simplificata
    static class Produs {
        String denumire;
        double pret;

        Produs(String denumire, double pret) {
            this.denumire = denumire;
            this.pret = pret;
        }

        void setPret(double pret) {
            if (pret > 0) {
                this.pret = pret;
            }
        }

        double getPret() {
            return pret;
        }
    }

    // Varianta 1: primeste un double (tip primitiv)
    static void majoreazaPret(double pret) {
        pret = pret * 1.10; // +10%
        // modifica DOAR copia locala, nu variabila originala
    }

    // Varianta 2: primeste un obiect Produs
    static void majoreazaPret(Produs p) {
        p.setPret(p.getPret() * 1.10); // +10%
        // modifica obiectul real
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Test cu double (tip primitiv)
        double pret = 24.50;
        System.out.println("Inainte (double): " + pret);
        majoreazaPret(pret);
        System.out.println("Dupa (double):    " + pret);  // ramane 24.50

        System.out.println();

        // Test cu obiect Produs
        Produs zeama = new Produs("Zeama", 24.50);
        System.out.println("Inainte (Produs): " + zeama.getPret());
        majoreazaPret(zeama);
        System.out.println("Dupa (Produs):    " + zeama.getPret());  // devine 26.95
    }
}