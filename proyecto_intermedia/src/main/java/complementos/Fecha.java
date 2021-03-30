package complementos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Fecha implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6844084711607252019L;
    private int dia; // Se inicializan los atributos
    private int mes;
    private int anio;
    private String mensaje;

    public Fecha() // Constuctores segun diferentes situaciones que puedan presentarse
    {
    }

    public Fecha(String fecha) {
        formatearFecha(fecha);
    }

    public Fecha(int dia, int mes, int anio) {
        setDia(dia);
        setMes(mes);
        setAnio(anio);
    }

    public Fecha(int dia, int mes, int anio, String mensaje) {
        setDia(dia);
        setMes(mes);
        setAnio(anio);
        setMensaje(mensaje);
    }

    public void setDia(int dia) // Sets y gets respectivos de cada atributo
    {
        this.dia = dia;
    }

    public int getDia() {
        return dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getMes() {
        return mes;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getAnio() {
        return anio;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    private boolean esBisiesto() // metodo para calcular si es bisiesto
    {
        if (anio % 400 == 0 || (anio % 4 == 0 && anio % 100 != 0))
            return true;
        else
            return false;
    }

    private byte devolverDiaMax() // metod para calcular cual es el dia maximo del mes, del 1-12
    {
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;

            case 2:
                if (esBisiesto())
                    return 29;
                else
                    return 28;

            case 6:
            case 9:
            case 11:
                return 30;

            default:
                return 0;
        }
    }

    public double calcularPlazo(Fecha posterior) // Metodo para el calculao de la diferentcia entre 2 fechas
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInicial = null;
        Date fechaFinal = null;

        try {
            fechaInicial = sdf.parse(this.toString());
            fechaFinal = sdf.parse(posterior.toString());
        } catch (ParseException e) {
            System.err.println("ERROR: No se ha podido crear la fecha");
            e.printStackTrace();
        }

        long transcurrido = fechaFinal.getTime() - fechaInicial.getTime();
        double difDias = (transcurrido / (double) (24 * 60 * 60 * 1000));

        return difDias;
    }

    private void formatearFecha(String fecha) {

        if (!fecha.contains("/") && fecha.length() != 11)
            System.out.println("Formato incorrecto de fecha");

        else {
            String[] valores = fecha.split("/");
            setDia(Integer.parseInt(valores[0]));
            setMes(Integer.parseInt(valores[1]));
            setAnio(Integer.parseInt(valores[2]));
        }
    }

    public Fecha llenarInformacion() // Llenado de infomracion dinamica y validada
    {
        System.out.println(mensaje == null ? "\nFECHA: " : "\nFECHA DE " + mensaje.toUpperCase() + ": ");

        this.anio = Lectura.inputInteger("Ingresa el año: [Formato: YYYY] Ej. 2001", "<=", 2020);
        this.mes = Lectura.inputInteger("Ingresa el mes: [Formato: MM Ej. 08]", "<=", 12);
        this.dia = Lectura.inputInteger("Ingresa el dia: [Formato: dd Ej. 27]", "<=", devolverDiaMax());

        return this;
    }

    public String toString() // to string formateado
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = sdf.parse(dia + "/" + mes + "/" + anio);
        } catch (ParseException e) {
            System.err.println("ERROR: No se ha podido crear la fecha");
            e.printStackTrace();
        }

        return sdf.format(date);
    }

    public static Fecha generarFechaHoy() {
        String fecha = LocalDate.now().toString();
        String[] datos = fecha.split("-");
        return new Fecha(Integer.parseInt(datos[2]), Integer.parseInt(datos[1]), Integer.parseInt(datos[0]));
    }
}