package directorios;

import complementos.Fecha; //Unico import necesario

abstract class Directorio {
    protected String descripcion; //Atributos de la clase
    protected Fecha creacionDoc;
    protected String nombreArchivo;
    
    protected Directorio () //Construcitor vacio para la inicializacion de los atributos con sets
        {
        }

    protected Directorio (String descripcion, Fecha creacionDoc) //Constructor con parametros a pasar a sets
        {
            setDescripcion(descripcion);
            setCreacionDoc(creacionDoc);
            setNombreArchivo(getDescripcion() + " " + getCreacionDoc().getAnio()); //Creacion del nombre del archivo
        }

    public void setDescripcion(String descripcion)  //sets y gets pertinenetes
        {
            this.descripcion = descripcion;
        }

    public String getDescripcion() 
        {
            return descripcion;
        }

    public void setCreacionDoc(Fecha creacionDoc) 
        {
            this.creacionDoc = creacionDoc;
        }

    public Fecha getCreacionDoc() 
        {
            return creacionDoc;
        }

    public void setNombreArchivo(String nombreArchivo) 
    {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    protected String setEspaciado (String input) //Metodos de espaciado para el formato
        {
            String espaciado = "                                          ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    protected String setEspaciadoP (String input)
        {
            String espaciado = "                  ";
            input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

            return input;
        }

    abstract public void generarArchivoCSV (); //Metodos por completar por los hijos
    abstract public void leerArchivoCSV (String nombreArchivo);
    abstract public void imprimirArchivo ();
    abstract public void imprimirArchivo(byte campo, String parametro);
    

    
}
