package lab4;

import lab3.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Lab 4 – Ziua la stand cu clase abstracte, interfețe, polimorfism și reguli interschimbabile.
 * N = 10 → procent fix 5%, prag 120 lei, top k = 4
 */
public class ZiLaStandLab4 {

    public static final int N = 10;
    public static final int PROCENT_FIX = 5 + N % 5;          // 5
    public static final int PRAG = 100 + 10 * (N % 4);         // 120
    public static final int TOP_K = 3 + N % 3;                 // 4
    public static final int K_CADOU = 3 + N % 4;               // 5 (din Lab 3)

    private final Catalog catalog = new Catalog();
    private final List<Bon> bonuri = new ArrayList<>();
    private final Map<String, BigDecimal> reduceriPeRegula = new LinkedHashMap<>();

    public void ruleaza() {
        Locale.setDefault(Locale.US);
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   SAKURA SUSHI – Lab 4 (abstract, interfețe, polimorfism)");
        System.out.println("   N=" + N + " | Procent fix=" + PROCENT_FIX + "% | Prag=" + PRAG
                + " lei | Top k=" + TOP_K);
        System.out.println("════════════════════════════════════════════════════════════\n");

        // ---------- Creare produse ----------
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

        // Personalizare (interfață)
        spicyTuna.adaugaIngredient("wasabi extra");
        spicyTuna.adaugaSosStandard(); // metoda default
        salmonNigiri.adaugaIngredient("ghimbir");

        List<Produs> toate = Arrays.asList(california, spicyTuna, dragon, salmonNigiri,
                tunaSashimi, ceaiVerde, apa, edamame, gyoza);
        for (Produs p : toate) {
            catalog.adauga(p);
        }

        // Combo
        MeniuCombo comboStudent = new MeniuCombo("Combo Student",
                Arrays.asList(spicyTuna, salmonNigiri, ceaiVerde));
        catalog.adauga(comboStudent);

        // ---------- Etapa 1+2: afișare cu categorie + capacități (instanceof doar pe interfețe) ----------
        System.out.println(">>> Produse + categorii + capacități (polimorfism):\n");
        for (Produs p : catalog.getProduse()) {
            System.out.println(p);
            if (p instanceof Personalizabil pers) {
                System.out.println("    → Personalizabil: " + pers.getIngredienteExtra());
            }
            if (p instanceof Proaspat pro) {
                System.out.println("    → Proaspăt: valabil " + pro.getZileValabilitate() + " zile");
            }
        }

        // ---------- Catalog ----------
        System.out.println();
        catalog.afiseaza();
        System.out.println("Valoare stoc totală: " + catalog.valoareStoc() + " MDL\n");

        // ---------- Ordonare ----------
        System.out.println(">>> Ordonare (Comparable + Comparator):\n");
        List<Produs> dupaNume = new ArrayList<>(catalog.getProduse());
        Collections.sort(dupaNume); // Comparable – alfabetic
        System.out.println("După denumire (Comparable):");
        dupaNume.forEach(p -> System.out.println("  " + p.getDenumire()));

        System.out.println("\nDupă preț final crescător:");
        catalog.top(TOP_K, Comparator.comparingDouble(Produs::pretFinal))
                .forEach(p -> System.out.printf("  %s (%.2f)%n", p.getDenumire(), p.pretFinal()));

        // ---------- Regulile ----------
        RegulaReducere regulaProcent = new ReducereProcentFix();
        RegulaReducere regulaPrag = new ReducerePestePrag();
        RegulaReducere regulaPicant = new ReducereRulouriPicante();
        // Lambda: reducere 8 lei dacă sunt cel puțin 3 produse în comandă
        RegulaReducere regulaLambda = new RegulaReducere() {
            @Override
            public BigDecimal calculeazaReducere(Comanda c) {
                int totalBuc = 0;
                for (LinieComanda l : c.getLinii()) totalBuc += l.getCantitate();
                return totalBuc >= 3 ? new BigDecimal("8.00") : BigDecimal.ZERO;
            }
            @Override
            public String getDenumire() { return "Lambda ≥3 produse (-8 lei)"; }
        };
        // sau mai scurt cu lambda reală (dacă interfața e funcțională – am făcut-o)
        RegulaReducere regulaLambda2 = c -> {
            int totalBuc = 0;
            for (LinieComanda l : c.getLinii()) totalBuc += l.getCantitate();
            return totalBuc >= 3 ? new BigDecimal("8.00") : BigDecimal.ZERO;
        };
        // (pentru getDenumire folosim clasa anonimă de mai sus)

        System.out.println("\n========== SIMULARE ZI (6+ comenzi, reguli diferite) ==========\n");

        // Comanda 1 – procent fix
        proceseaza("Client 1 – procent fix", c -> {
            c.adauga(california, 2);
            c.adauga(apa, 1);
        }, regulaProcent);

        // Comanda 2 – regula picantă (2+ rulouri picante)
        proceseaza("Client 2 – rulouri picante", c -> {
            c.adauga(spicyTuna, 1);
            c.adauga(dragon, 1);
            c.adauga(salmonNigiri, 2);
            c.adauga(ceaiVerde, 1);
        }, regulaPicant, regulaProcent);

        // Comanda 3 – combo + prag
        proceseaza("Client 3 – combo + prag", c -> {
            c.adauga(comboStudent, 1);
            c.adauga(edamame, 1);
            c.adauga(gyoza, 1);
        }, regulaPrag, regulaProcent);

        // Comanda 4 – lambda
        proceseaza("Client 4 – lambda ≥3", c -> {
            c.adauga(california, 1);
            c.adauga(salmonNigiri, 1);
            c.adauga(apa, 1);
        }, regulaLambda);

        // Comanda 5 – stoc limitat + cadou (bon 5)
        tunaSashimi.setStoc(1);
        proceseaza("Client 5 – stoc + cadou", c -> {
            c.adauga(tunaSashimi, 1);
            c.adauga(tunaSashimi, 2); // refuz
            c.adauga(gyoza, 1);
        }, regulaProcent);

        // Comanda 6
        proceseaza("Client 6 – final", c -> {
            c.adauga(dragon, 1);
            c.adauga(ceaiVerde, 2);
            c.adauga(edamame, 1);
        }, regulaPrag, regulaPicant);

        // ---------- Raport final ----------
        afiseazaRaport();
    }

    private void proceseaza(String eticheta, java.util.function.Consumer<Comanda> builder,
                            RegulaReducere... reguli) {
        System.out.println("--- " + eticheta + " ---");
        Comanda c = new Comanda();
        builder.accept(c);
        if (c.esteGoala()) {
            System.out.println("  (nimic)\n");
            return;
        }

        // actualizează vânzări
        for (LinieComanda l : c.getLinii()) {
            l.getProdus().adaugaVanzari(l.getCantitate());
        }

        String cadou = null;
        int urm = Bon.getTotalBonuriEmise() + 1;
        if (urm % K_CADOU == 0) {
            cadou = "Wasabi + ghimbir + betișoare premium";
        }

        Bon bon = new Bon(c, cadou, reguli);
        bonuri.add(bon);

        // statistici reduceri
        if (bon.getRegulaAplicata() != null) {
            String nume = bon.getRegulaAplicata().getDenumire();
            reduceriPeRegula.merge(nume, bon.getValoareReducere(), BigDecimal::add);
        }

        System.out.println(bon);
        System.out.println();
    }

    private void afiseazaRaport() {
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   RAPORT DE ZI – Sakura Sushi (Lab 4)");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("Bonuri emise: " + bonuri.size());

        BigDecimal incasari = BigDecimal.ZERO;
        BigDecimal totalReduceri = BigDecimal.ZERO;
        for (Bon b : bonuri) {
            incasari = incasari.add(b.getTotal());
            totalReduceri = totalReduceri.add(b.getValoareReducere());
        }
        System.out.println("Încasări totale: " + incasari.setScale(2, RoundingMode.HALF_UP) + " MDL");
        System.out.println("Total reduceri acordate: " + totalReduceri.setScale(2, RoundingMode.HALF_UP) + " MDL");

        System.out.println("\nReduceri pe reguli:");
        reduceriPeRegula.forEach((k, v) -> System.out.println("  " + k + ": " + v + " MDL"));

        System.out.println("\nTop " + TOP_K + " produse după vânzări:");
        catalog.top(TOP_K, Comparator.comparingInt(Produs::getUnitatiVandute).reversed())
                .forEach(p -> System.out.printf("  %s – %d buc.%n", p.getDenumire(), p.getUnitatiVandute()));

        System.out.println("\nValoare stoc rămas: " + catalog.valoareStoc() + " MDL");
        System.out.println("════════════════════════════════════════════════════════════");
    }

    public static void main(String[] args) {
        new ZiLaStandLab4().ruleaza();
    }
}
