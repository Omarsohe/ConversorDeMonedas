package Modelos;

import com.google.gson.annotations.SerializedName;

public class Moneda {

    @SerializedName("MXN")
    private double mexico;
    private double argentina;
    private double colombia;
    private double peru;
    private double usa;

    //Constructor
    // Constructor con usando nuestro Record
    public Moneda(Divisas misdivisas){
        this.mexico = misdivisas.mxn();
        this.argentina = misdivisas.ars();
        this.colombia = misdivisas.cop();
        this.peru = misdivisas.pen();
        this.usa = misdivisas.usd();
    }

   //Getters
    public double getMexico() {
        return mexico;
    }
     public double getArgentina(){
        return argentina;
     }

    public double getColombia() {
        return colombia;
    }

    public double getPeru() {
        return peru;
    }

    public double getUsa() {
        return usa;
    }

    //Métodos de operación
    //convirtiendo a moneda mexicana
    public double aMxn(double cantidadAConvertir, double mexico){
        double change = cantidadAConvertir * mexico;
        return change;
    }
    //convirtiendo a moneda argentina
    public double aArs(double cantidadAConvertir, double argentina){
        double change = cantidadAConvertir * argentina;
        return change;
    }
    public double aCop(double cantidadAConvertir, double colombia){
        double change = cantidadAConvertir * colombia;
        return change;
    }
    public double aPen(double cantidadAConvertir, double peru ){
        double change = cantidadAConvertir * peru;
        return change;
    }
    public double aUsd(double cantidadAConvertir, double usa){
        double change = cantidadAConvertir * usa;
        return change;
    }

    public String toString(String busqueda, String opcionDeConversion, double cantidadAConvertir, String formatoDouble){
        return "\nConversion de divisa \n{La divisa base es: " + busqueda +
                ", Divisa de conversion: " + opcionDeConversion +
                ", Monto: " + cantidadAConvertir +
                ", Conversion: " + formatoDouble + "}";
    }
}
