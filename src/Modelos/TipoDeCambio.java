package Modelos;

public class TipoDeCambio extends Moneda{


    public TipoDeCambio(Divisas misdivisas) {
        super(misdivisas);
    }

    public String toString(String busqueda){
        return "Tipo de cambio de su divisa " + "'" + busqueda + "'" +
            "\n{Moneda mexicana: " + getMexico() +
            ", Moneda argentino: " + getArgentina() +
            ", Moneda colombiana: " + getColombia() +
            ", Moneda peruana: " + getPeru() +
            ", -moneda estadounidense: " + getUsa() + "}";

    }
}
