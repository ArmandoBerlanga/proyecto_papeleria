package entidades;

import java.util.Random;
import complementos.Fecha;
import complementos.Nombre;
import complementos.Lectura;

public class Empleado extends Persona {
    /**
     *
     */
    private static final long serialVersionUID = -225523350249259522L;
    private String RFC;
    private Fecha fechaContratacion;
    private int nomina;

    public Empleado ()
        {
        }

    public Empleado(Nombre nombre, Fecha fechaNacimiento, String mail, long telefono, int nomina){ // PARA CUANDO LA FECHA ES HOY
        super(nombre, fechaNacimiento, mail, telefono);
        String RFC = generarRFC();
        setRFC(RFC);
        Fecha fechaContratacion = Fecha.generarFechaHoy(); // genera la fecha de hoy
        setFechaContratacion(fechaContratacion);
        setNomina(nomina);
    }

    public Empleado(Nombre nombre, Fecha fechaNacimiento, String mail, long telefono, Fecha fechaContratacion, int nomina){ // PARA CUANDO LA FECHA ES OTRA QUE NO ES HOY
        super(nombre, fechaNacimiento, mail, telefono);
        String RFC = generarRFC();
        setRFC(RFC);
        setFechaContratacion(fechaContratacion);
        setNomina(nomina);
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public void setFechaContratacion(Fecha fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public void setNomina(int nomina) {
        this.nomina = nomina;
    }

    public String getRFC() {
        return RFC;
    }

    public Fecha getFechaContratacion() {
        return fechaContratacion;
    }

    public int getNomina() {
        return nomina;
    }

    public String generarRFC() { // dinámicamente genera tu RFC
        String str[] = nombre.toString().split(" ");
        String[] fechaStr = fechaNacimiento.toString().split("/");
        Random r1 = new Random();
        Random r2 = new Random();
        return str[1].substring(0, 2).toUpperCase() + str[2].substring(0, 1).toUpperCase()
                + str[0].substring(0, 1).toUpperCase() + fechaStr[2].substring(2) + fechaStr[1]
                + fechaStr[0] + String.valueOf((char) (r1.nextInt(26) + 'A'))
                + String.valueOf((char) (r2.nextInt(26) + 'A')) + Integer.toString((int) Math.round(Math.random() * 9));
    }

    @Override
    public void llenarInformacion(String tipo) //método para llenar los datos de los objetos empleados
        {
            super.llenarInformacion(tipo);
            byte clave = Lectura.inputByteOpcion(
                    "\nIngresa el número de la opción que deseas\n\n1. Hacer registro con la fecha de contratación actual (al día de hoy)\n2. Hacer registro con otra fecha\n");

            Fecha cont = new Fecha();
            cont.setMensaje("Contratación");
            cont = (clave == 1 ? Fecha.generarFechaHoy() : cont.llenarInformacion()); //ternaria para saber si incluir la fecha actual o una ingresada
            setFechaContratacion(cont);

            setMail(Lectura.inputMail("Ingresa el correo electrónico del " + tipo));
            setRFC(generarRFC());
        }

    public String toString() {
            return super.toString() +  setEspaciadoP(RFC) + setEspaciadoP(fechaContratacion.toString()) + setEspaciadoP(String.valueOf(nomina));
        }
}