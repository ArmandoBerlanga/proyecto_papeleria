package historiales;

import complementos.Fecha; //Unico import necesario

abstract class Historial {
    protected String descripcion; //Atributos de la clase
    protected Fecha fechaCreacion;
    protected String nombreArchivo;

    protected Historial (String descripcion, Fecha fechaCreacion) //Constructor
        {
            setDescripcion(descripcion);
            setFechaCreacion(fechaCreacion);
            setNombreArchivo(getDescripcion() + " " + getFechaCreacion().getAnio()); //Creacion del nombre del archivo
        }

    public void setDescripcion(String descripcion)  //sets y gets necesarios
        {
            this.descripcion = descripcion;
        }

    public String getDescripcion() 
        {
            return descripcion;
        }

    public void setFechaCreacion(Fecha fechaCreacion) 
        {
            this.fechaCreacion = fechaCreacion;
        }

    public Fecha getFechaCreacion() 
        {
            return fechaCreacion;
        }

    public void setNombreArchivo(String nombreArchivo) 
        {
            this.nombreArchivo = nombreArchivo;
        }
    
    public String getNombreArchivo() 
        {
            return nombreArchivo;
        }

    protected String setEspaciado (String input) //Metodos de especiado para el formato
        {
            String espaciado = "                             ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    protected String setEspaciadoP (String input)
        {
            String espaciado = "                  ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    public abstract void generarArchivoCSV(); //Metodos abstactos a completar en las clases hijas
    abstract public void leerArchivoCSV(String nombreArchivo) ;
    public abstract void imprimirArchivo();
    public abstract void graficarContenido();

}