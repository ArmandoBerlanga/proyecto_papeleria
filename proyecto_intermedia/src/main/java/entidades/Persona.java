package entidades;

import java.io.Serializable;

import complementos.Fecha;
import complementos.Lectura;
import complementos.Nombre;

class Persona implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4541275781368203315L;
    protected Nombre nombre;
    protected Fecha fechaNacimiento;
    protected String mail;
    protected long telefono;

    public Persona ()
        {
            
        }

    public Persona(Nombre nombre, Fecha fechaNacimiento, String mail, long telefono){
        setNombre(nombre);
        setFechaNacimiento(fechaNacimiento);
        setMail(mail);
        setTelefono(telefono);
    }

    public void setNombre(Nombre nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(Fecha fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public Nombre getNombre() {
        return nombre;
    }

    public Fecha getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getMail() {
        return mail;
    }

    public long getTelefono() {
        return telefono;
    }

    protected static String setEspaciado (String input) //método para imprimir con un espaciado estético
        {
            String espaciado = "                             ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    protected static String setEspaciadoP (String input) //método para imprimir con un espaciado más pequeño que el anterior
        {
            String espaciado = "                  ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    public void llenarInformacion(String tipo) //método para llenar los datos de los objetos persona
        {
            Nombre n = new Nombre(Lectura.inputNombre("Ingresa tu primer nombre y dos apellidos completos"));
            setNombre(n);
            
            Fecha nac = new Fecha();
            nac.setMensaje("Nacimiento");
            setFechaNacimiento(nac.llenarInformacion());
            
            setTelefono(Lectura.inputTelefono("Ingresa el teléfono del " + tipo));
        }

    public String toString() {
        return setEspaciado(nombre.toString()) + setEspaciadoP(fechaNacimiento.toString()) 
        + setEspaciado(mail) + setEspaciadoP(String.valueOf(telefono));
    }
}
