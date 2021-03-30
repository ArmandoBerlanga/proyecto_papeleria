package entidades;

import complementos.Fecha;
import complementos.Nombre;

public class Gerente extends Empleado {
    /**
     *
     */
    private static final long serialVersionUID = 6738323964782953038L;
    private String usuario;
    private String contrasena;

    public Gerente(){

    }

    public Gerente(Nombre nombre, Fecha fechaNacimiento, String mail, long telefono, int nomina, String usuario, String contrasena) {
        super(nombre, fechaNacimiento, mail, telefono, nomina);
        setUsuario(usuario);
        setContrasena(contrasena);
    }

    public Gerente(Nombre nombre, Fecha fechaNacimiento, String mail, long telefono, Fecha fechaContratacion, int nomina, String usuario, String contrasena){
        super(nombre, fechaNacimiento, mail, telefono, fechaContratacion, nomina);
        setUsuario(usuario); //se agrega su atributo distintivo de usuario
        setContrasena(contrasena); //se agrega su atributo distintivo de contraseña
    }

    public void setUsuario(String usuario) { 
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String toString() {
        return super.toString();
    }


}
