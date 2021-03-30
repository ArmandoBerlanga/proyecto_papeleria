package entidades;

import complementos.Fecha;
import complementos.Nombre;

public class Cliente extends Persona {
    /**
     *
     */
    private static final long serialVersionUID = -5570998550502207711L;
    private long numCompras;

    public Cliente() {
    }

    public Cliente(Nombre nombre, Fecha fechaNacimiento, String mail, long telefono, long numCompras) {
        super(nombre, fechaNacimiento, mail, telefono);
        setNumCompras(numCompras); //se agrega su atributo distintivo de número de compras
    }

    public void setNumCompras(long numCompras) { 
        this.numCompras = numCompras;
    }

    public long getNumCompras() {
        return numCompras;
    }

    @Override
    public void llenarInformacion(String tipo)
        {
            super.llenarInformacion(tipo);
            setNumCompras(0);
        }


    public String toString() {
        return super.toString() + setEspaciadoP(String.valueOf(numCompras));
    }

}