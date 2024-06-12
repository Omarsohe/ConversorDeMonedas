package Excepciones;

public class ErrorDeNombreDivisa extends RuntimeException{
    private String mensaje;
    public ErrorDeNombreDivisa(String mensaje){
        this.mensaje = mensaje;
    }

    public String getMensaje(){
        return this.mensaje;
    }
}
