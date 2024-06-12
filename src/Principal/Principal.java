package Principal;

import Modelos.Divisas;
import Modelos.Moneda;
import com.google.gson.*;
import com.google.gson.stream.MalformedJsonException;

import javax.swing.*;
import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.MalformedInputException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static void main (String[]args) throws IOException, InterruptedException {
        //Usando StringFormat para reducirl los decimales de un double

        boolean salida = false;
        List<String> lista = new ArrayList<>();

        do {

            //Solictamos al usuario la moneda/divisa/currency que desea visualizar
            String busqueda = JOptionPane.showInputDialog("Digite la divisa base que desea convertir 'USD', 'PEN', 'COP' 'MXN', 'ARS' : ");
            //Modificamos la dirección
            String direccion = "https://v6.exchangerate-api.com/v6/005b14c41d25f3112797ba03/latest/" + busqueda;

            try {
                //Creamos el cliente Http
                HttpClient client = HttpClient.newHttpClient();
                //Creamos la solicitud
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                //Solicitamos el response al servidor
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();
                //System.out.println(json);

                //Parseamos la cadena Json a un JsonElement
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(json);

                //convertimos el JsonElement a un JsonObject
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                //creamos una isntancia que nos devuelva como un JsonObject el elemento conversion_rates
                JsonObject conversionDeMonedas = jsonObject.getAsJsonObject("conversion_rates");

                //Obtener las tasas especificas
                double usdRate = conversionDeMonedas.getAsJsonPrimitive("USD").getAsDouble();
                double penRate = conversionDeMonedas.getAsJsonPrimitive("PEN").getAsDouble();
                double copRate = conversionDeMonedas.getAsJsonPrimitive("COP").getAsDouble();
                double mxnRate = conversionDeMonedas.getAsJsonPrimitive("MXN").getAsDouble();
                double argRate = conversionDeMonedas.getAsJsonPrimitive("ARS").getAsDouble();

                //Crear el record de divisas
                Divisas misDivisas = new Divisas(mxnRate, argRate, copRate, penRate, usdRate);


                //Convertir el record a una instancia de la clase propia
                Moneda moneda = new Moneda(misDivisas);
                //System.out.println(moneda);

                //variabale para repetir
                boolean repetir = false;
                do {
                    //conversiones
                    double conversion = 0;
                    ;

                    //Se hace esl cast porque se recibe un String pero nuestra variable que alamcena espera un doouble
                    double cantidadAConvertir = Double.parseDouble(JOptionPane.showInputDialog("Cantiidad que desea convertir: "));

                    //eleccion de divisa a convertir
                    String opcionDeConversion = JOptionPane.showInputDialog("Divisa a la que desea converitr: ");

                    switch (opcionDeConversion) {
                        case "mxn", "MXN":
                            conversion = moneda.aMxn(cantidadAConvertir, mxnRate);
                            System.out.println(conversion);
                            String formatoDouble = String.format("%.2f", conversion);
                            //De esta manera hacemos un cast de un double a un String  System.out.println(conversion + "");
                            lista.add(moneda.toString(busqueda, opcionDeConversion, cantidadAConvertir, formatoDouble));
                            break;
                        case "ars", "ARS":
                            conversion = moneda.aArs(cantidadAConvertir, argRate);
                            System.out.println(conversion);
                            formatoDouble = String.format("%.2f", conversion);
                            lista.add(moneda.toString(busqueda, opcionDeConversion, cantidadAConvertir, formatoDouble));
                            break;
                        case "cop", "COP":
                            conversion = moneda.aCop(cantidadAConvertir, copRate);
                            System.out.println(conversion);
                            formatoDouble = String.format("%.2f", conversion);
                            lista.add(moneda.toString(busqueda, opcionDeConversion, cantidadAConvertir, formatoDouble));
                            break;
                        case "pen", "PEN":
                            conversion = moneda.aPen(cantidadAConvertir, penRate);
                            System.out.println(conversion);
                            formatoDouble = String.format("%.2f", conversion);
                            lista.add(moneda.toString(busqueda, opcionDeConversion, cantidadAConvertir, formatoDouble));
                            break;
                        case "usd", "USD":
                            conversion = moneda.aUsd(cantidadAConvertir, usdRate);
                            System.out.println(conversion);
                            formatoDouble = String.format("%.2f", conversion);
                            lista.add(moneda.toString(busqueda, opcionDeConversion, cantidadAConvertir, formatoDouble));

                            break;
                        default:
                            System.out.println("Lo siento no se ha encontrado esa divisa");
                            break;
                    }

                    int opcionDos = Integer.parseInt(JOptionPane.showInputDialog("Digite 1.Si desea convertir otra cantidad " +
                            "\n2. Si desea cambair de divisa base" +
                            "\n3. Si desea salir del la aplicación"));
                    if (opcionDos == 1) {
                        repetir = true;
                    } else if (opcionDos == 2) {
                        repetir = false;
                    } else if (opcionDos == 3) {
                        salida = true;
                        repetir = false;
                    }

                } while (repetir == true);

                }catch (NullPointerException e) {
                    System.out.println("Ocurrrio un error al introducir la divisa");
                    System.out.println("Error enontrado: " + e.getMessage());
                }catch (NumberFormatException e){
                System.out.println("Ocurrio un error al introducir la cantidad a convertir");
                System.out.println("Error enontrado: " + e.getMessage());
                }

            } while (salida == false) ;

            JOptionPane.showMessageDialog(null, "Gracias por usar nuestro servicio. A continuación le mostramos un resumen de sus conversiones de divisas \n" + lista);
    }
}
