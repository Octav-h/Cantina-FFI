package Lab3;

import java.math.BigDecimal;

public class Bon {
    private static int contorBonuri = 0;

    private final int numar;
    private final Comanda comanda; // agregare sau compoziție – aici referință (agregare ușoară)
    private final String cadou;     // null dacă nu e al k-lea

    public Bon(Comanda comanda, String cadou) {
        if (comanda == null || comanda.esteGoala()) {
            throw new IllegalArgumentException("Nu se poate emite bon pentru o comandă goală.");
        }
        this.numar = ++contorBonuri;
        this.comanda = comanda;
        this.cadou = cadou;
    }

    public int getNumar() {
        return numar;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public String getCadou() {
        return cadou;
    }

    public BigDecimal getTotal() {
        return comanda.total();
    }

    public static int getTotalBonuriEmise() {
        return contorBonuri;
    }

    public static void reseteazaContor() {
        contorBonuri = 0; // util pentru teste
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════╗\n");
        sb.append(String.format("║  BON nr. %-28d ║\n", numar));
        sb.append("╠══════════════════════════════════════╣\n");
        sb.append(comanda.toString()).append("\n");
        if (cadou != null && !cadou.isEmpty()) {
            sb.append("╠══════════════════════════════════════╣\n");
            sb.append("║  ★ CADOU: ").append(cadou);
            // padding roughly
            int pad = 28 - cadou.length();
            if (pad > 0) {
                sb.append(" ".repeat(pad));
            }
            sb.append("║\n");
        }
        sb.append("╚══════════════════════════════════════╝");
        return sb.toString();
    }
}
