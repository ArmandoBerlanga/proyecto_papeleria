package complementos;

import java.io.Serializable;

public class Nombre implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3813980374795092570L;
    private String pNombre;
    private String apellidoP;
    private String apellidoM;

    public Nombre(String pNombre, String apellidoP, String apellidoM) {
        setpNombre(pNombre);
        setApellidoP(apellidoP);
        setApellidoM(apellidoM);
    }

    public Nombre(String nombre) {
        formatearNombre(nombre);
    }

    public void setpNombre(String pNombre) {
        this.pNombre = pNombre;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getpNombre() {
        return pNombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    private void formatearNombre(String nombreCompleto) // para nosotros hacer el trabajo de llenar el nombre de manera adecuada y el usuario no lo tenga que separar
        {
            String [] separaciones = nombreCompleto.split(" ");
            setpNombre(separaciones[0]);
            setApellidoP(separaciones[1]);
            setApellidoM(separaciones[2]);
        }

    public String toString() {
        return pNombre + " " + apellidoP + " " + apellidoM;
    }
}