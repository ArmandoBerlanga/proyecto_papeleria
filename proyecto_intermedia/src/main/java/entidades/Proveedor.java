package entidades;

import complementos.Lectura;

public class Proveedor {
    
    private int codigo;
    private String nombre;
    private String giro;
    private long telefono;
    private String mail; 
    private String direccion;


    public Proveedor() {

    }

    public Proveedor(int codigo, String nombre, String giro, long telefono, String mail, String direccion) {

        setCodigo(codigo);
        setNombre(nombre);
        setGiro(giro);
        setTelefono(telefono);
        setMail(mail);
        setDireccion(direccion);
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGiro() {
        return giro;
    }

    public long getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public String getDireccion() {
        return direccion;
    }

    private static String setEspaciado (String input)
        {
            String espaciado = "                             ";
            input += espaciado.substring(0, espaciado.length() - input.length());

            return input;
        }

    private static String setEspaciadoP (String input)
        {
            String espaciado = "                  ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    public void llenarInformacion()
        {
            setNombre(Lectura.inputString("Ingresa el nombre del proveedor"));
            setGiro(Lectura.inputString("Ingresa el giro del prooveedor"));
            setMail(Lectura.inputMail("Ingresa el correo electrónico del proveedor"));
            setTelefono(Lectura.inputTelefono("Ingresa el teléfono del proveedor"));
            setDireccion(Lectura.inputString("Ingresa la dirección del proveedor"));
        }

    public String toString() {
        return setEspaciadoP(String.valueOf(getCodigo())) + setEspaciado(getNombre()) 
        + setEspaciadoP(getGiro()) + setEspaciadoP(Long.toString(getTelefono())) + setEspaciado(getMail()) 
        + setEspaciado(getDireccion());
    }

}