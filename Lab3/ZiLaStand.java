package Lab3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Simulează o zi de lucru la standul Sakura Sushi.
 * N = 10 → min produse = 7, reducere combo = 14%, k = 5 (cadou la al 5-lea bon)
 */
public class ZiLaStand {

    // Constante personale (N = 10)
    public static final int N = 10;
    public static final int MIN_PRODUSE = 6 + N % 3;          // 7
    public static final int REDUCERE_COMBO = 10 + N % 6;      // 14
    public static final int K_CADOU = 3 + N % 4;              // 5

    private final List<Produs> toateProdusele = new ArrayList<>();
    private final List<Bon> bonuriEmise = new ArrayList<>();
    private final Map<Produs, Integer> vanzari = new HashMap<>();

    public void ruleaza() {
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   SAKURA SUSHI – Food Court FFI");
        System.out.println("   N = " + N + " | Min produse: " + MIN_PRODUSE
                + " | Reducere combo: " + REDUCERE_COMBO + "%"
                + " | Cadou la fiecare al " + K_CADOU + "-lea bon");
        System.out.println("════════════════════════════════════════════════════════════\n");

        // ---------- Creare produse (cel puțin 7) ----------
        Rulou california = new Rulou("California Roll", 65.0, 20, NivelPicanterie.NORMAL, 8);
        Rulou spicyTuna = new Rulou("Spicy Tuna Roll", 72.0, 15, NivelPicanterie.PICANT, 8);
        RulouSpecial dragon = new RulouSpecial("Dragon Roll", 95.0, 10,
                NivelPicanterie.PICANT, 8, "anghilă + avocado", 12.0);
        Nigiri salmonNigiri = new Nigiri("Salmon Nigiri", 28.0, 30, "somon", false);
        Nigiri tunaSashimi = new Nigiri("Tuna Sashimi", 45.0, 12, "ton", true);
        BauturaCalda ceaiVerde = new BauturaCalda("Ceai Verde Sencha", 25.0, 40, 250, 75, true);
        Bautura apa = new Bautura("Apă plată", 12.0, 50, false, 500);
        Extra edamame = new Extra("Edamame", 30.0, 25, true);
        Extra gyoza = new Extra("Gyoza (6 buc)", 48.0, 18, false);

        toateProdusele.addAll(Arrays.asList(
                california, spicyTuna, dragon, salmonNigiri, tunaSashimi,
                ceaiVerde, apa, edamame, gyoza
        ));

        System.out.println(">>> Produsele standului (" + toateProdusele.size() + "):\n");
        for (Produs p : toateProdusele) {
            System.out.println(p);
        }

        // ---------- Experiment BigDecimal ----------
        System.out.println("\n========== EXPERIMENT BigDecimal ==========");
        double s = 0;
        for (int i = 0; i < 10; i++) s += 0.10;
        System.out.println("Suma double 0.10 de 10 ori: " + s);

        BigDecimal b = BigDecimal.ZERO;
        for (int i = 0; i < 10; i++) {
            b = b.add(new BigDecimal("0.10"));
        }
        System.out.println("Suma BigDecimal(\"0.10\") de 10 ori: " + b);
        System.out.println("Motiv: 0.10 nu poate fi reprezentat exact în virgulă mobilă binară (double),");
        System.out.println("iar BigDecimal cu String păstrează valoarea zecimală exactă.\n");

        // ---------- Meniuri (agregare) ----------
        System.out.println("========== AGREGARE – Meniuri ==========");
        Meniu meniuDimineata = new Meniu("Meniul dimineții");
        meniuDimineata.adaugaProdus(california);
        meniuDimineata.adaugaProdus(ceaiVerde);
        meniuDimineata.adaugaProdus(edamame);

        Meniu meniuZilei = new Meniu("Meniul zilei");
        meniuZilei.adaugaProdus(california); // același obiect!
        meniuZilei.adaugaProdus(dragon);
        meniuZilei.adaugaProdus(salmonNigiri);
        meniuZilei.adaugaProdus(apa);

        System.out.println(meniuDimineata);
        System.out.println(meniuZilei);

        System.out.println(">>> Demonstrație: același obiect California Roll este în ambele meniuri.");
        System.out.println("    Preț actual California: " + california.pretFinal());
        california.setPretBaza(70.0); // modificare
        System.out.println("    După setPretBaza(70): " + california.pretFinal());
        System.out.println("    Se vede în ambele meniuri (agregare – obiecte partajate).\n");

        System.out.println(">>> Setăm meniuDimineata = null ...");
        meniuDimineata = null;
        System.out.println("    Produsele există în continuare și pot fi comandate:");
        System.out.println("    " + california);
        System.out.println("    " + ceaiVerde + "\n");

        // ---------- Combo ----------
        System.out.println("========== MENIU COMBO ==========");
        List<Produs> compCombo = Arrays.asList(spicyTuna, salmonNigiri, ceaiVerde);
        MeniuCombo comboStudent = new MeniuCombo("Combo Student", compCombo);
        toateProdusele.add(comboStudent);

        System.out.println(comboStudent);
        System.out.println("Preț calculat manual:");
        double suma = spicyTuna.pretFinal() + salmonNigiri.pretFinal() + ceaiVerde.pretFinal();
        double dupaRed = suma * (100 - REDUCERE_COMBO) / 100.0;
        System.out.printf("  %.2f + %.2f + %.2f = %.2f → după -%d%% = %.2f%n%n",
                spicyTuna.pretFinal(), salmonNigiri.pretFinal(), ceaiVerde.pretFinal(),
                suma, REDUCERE_COMBO, dupaRed);

        // ---------- Simulare comenzi ----------
        System.out.println("========== SIMULARE ZI (5+ comenzi) ==========\n");

        // Comanda 1: normală
        proceseazaComanda("Client 1 – clasic", c -> {
            c.adauga(california, 2);
            c.adauga(apa, 1);
        });

        // Comanda 2: cu regula proprie activă (vezi mai jos)
        proceseazaComanda("Client 2 – regulă proprie", c -> {
            c.adauga(spicyTuna, 1);
            c.adauga(dragon, 1);          // 2 rulouri picante → regulă
            c.adauga(salmonNigiri, 3);
            c.adauga(ceaiVerde, 1);
        });

        // Comanda 3: combo
        proceseazaComanda("Client 3 – combo", c -> {
            c.adauga(comboStudent, 1);
            c.adauga(edamame, 1);
        });

        // Comanda 4: refuz parțial din stoc
        // Forțăm stoc mic la tunaSashimi
        tunaSashimi.setStoc(1);
        proceseazaComanda("Client 4 – stoc limitat", c -> {
            c.adauga(tunaSashimi, 1); // ok
            c.adauga(tunaSashimi, 2); // refuz
            c.adauga(gyoza, 2);
            c.adauga(apa, 2);
        });

        // Comanda 5: încă una normală (pentru a ajunge la bonul 5 = cadou)
        proceseazaComanda("Client 5 – pentru cadou", c -> {
            c.adauga(california, 1);
            c.adauga(ceaiVerde, 2);
            c.adauga(edamame, 1);
        });

        // Comanda 6 extra
        proceseazaComanda("Client 6 – final", c -> {
            c.adauga(dragon, 1);
            c.adauga(salmonNigiri, 2);
        });

        // ---------- Raport de zi ----------
        afiseazaRaportZi();
    }

    /**
     * Regula proprie a standului Sakura Sushi:
     * Dacă în comandă există cel puțin 2 rulouri cu picanterie PICANT sau EXTRA_PICANT,
     * se acordă o reducere de 10% din subtotal.
     */
    private void aplicaRegulaProprie(Comanda c) {
        int rulouriPicante = 0;
        for (LinieComanda l : c.getLinii()) {
            Produs p = l.getProdus();
            if (p instanceof Rulou) {
                NivelPicanterie niv = ((Rulou) p).getPicanterie();
                if (niv == NivelPicanterie.PICANT || niv == NivelPicanterie.EXTRA_PICANT) {
                    rulouriPicante += l.getCantitate();
                }
            }
        }
        if (rulouriPicante >= 2) {
            BigDecimal sub = c.subtotal();
            BigDecimal red = sub.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
            c.setReducereExtra(red);
            System.out.println("  ★ Regulă proprie activată: ≥2 rulouri picante → -10% (" + red + " MDL)");
        }
    }

    private void proceseazaComanda(String eticheta, java.util.function.Consumer<Comanda> builder) {
        System.out.println("--- " + eticheta + " ---");
        Comanda c = new Comanda();
        builder.accept(c);
        if (c.esteGoala()) {
            System.out.println("  (nimic de facturat)\n");
            return;
        }
        aplicaRegulaProprie(c);

        // Cadou la fiecare al k-lea bon
        String cadou = null;
        int urmatorulNumar = Bon.getTotalBonuriEmise() + 1;
        if (urmatorulNumar % K_CADOU == 0) {
            cadou = "Wasabi + ghimbir gratis + betișoare premium";
        }

        Bon bon = new Bon(c, cadou);
        bonuriEmise.add(bon);

        // Actualizează statistici vânzări
        for (LinieComanda l : c.getLinii()) {
            vanzari.merge(l.getProdus(), l.getCantitate(), Integer::sum);
        }

        System.out.println(bon);
        System.out.println();
    }

    private void afiseazaRaportZi() {
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   RAPORT DE ZI – Sakura Sushi");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("Bonuri emise: " + bonuriEmise.size());

        BigDecimal incasari = BigDecimal.ZERO;
        for (Bon b : bonuriEmise) {
            incasari = incasari.add(b.getTotal());
        }
        System.out.println("Încasări totale: " + incasari.setScale(2, RoundingMode.HALF_UP) + " MDL");

        // Produsul cel mai vândut
        Produs top = null;
        int maxCant = 0;
        for (Map.Entry<Produs, Integer> e : vanzari.entrySet()) {
            if (e.getValue() > maxCant) {
                maxCant = e.getValue();
                top = e.getKey();
            }
        }
        if (top != null) {
            System.out.println("Produsul cel mai vândut: " + top.getDenumire() + " (" + maxCant + " buc.)");
        }

        System.out.println("\nStoc rămas:");
        for (Produs p : toateProdusele) {
            if (!(p instanceof MeniuCombo)) { // combo-ul e virtual
                System.out.printf("  %-25s : %d%n", p.getDenumire(), p.getStoc());
            }
        }
        System.out.println("════════════════════════════════════════════════════════════");
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        new ZiLaStand().ruleaza();
    }
}
