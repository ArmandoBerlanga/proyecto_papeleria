package inventario_productos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import complementos.Fecha;

public class Inventario {
    private String descripcion; //Atributos a utilizar dentro del programa
    private Fecha fechaCreacion;
    private String nombreArchivo;
    private ArrayList<Producto> listaProductos;

    public Inventario() {
        listaProductos = new ArrayList<Producto>(); //Lista de productos
    }

    public Inventario(String descripcion, Fecha fechaCreacion) {
        setDescripcion(descripcion);
        setFechaCreacion(fechaCreacion);
        setNombreArchivo(descripcion + " " + fechaCreacion.getAnio());
        listaProductos = new ArrayList<Producto>();
    }

    public void setDescripcion(String descripcion) { //Respectivos sets y gets
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaCreacion(Fecha fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Fecha getFechaCreacion() {
        return fechaCreacion;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void agregarProducto(Producto p) { //Metodo para agregar un prodcuto y no tener que importar el Arraylist
        listaProductos.add(p);
    }

    private String setEspaciadoP(String input) { //Metodo de formato
        String espaciado = "                ";
        input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

        return input;
    }

    public void generarArchivoCSV() { //Metodo de generacion de archivo
      
        try {
            File f = new File(System.getProperty("user.dir") + "/" + getNombreArchivo() + ".csv");
            FileOutputStream fos = new FileOutputStream(f);
            PrintStream ps = new PrintStream(fos);

            ps.print("Codigo,Nombre,Cantidad,Precio compra,Precio venta");
            for (Producto p : listaProductos)
                ps.print("\n" + p.getCodigo() + "," + p.getNombre() + "," + p.getCantidadStock() + ","
                        + p.getPrecioCompra() + "," + p.getPrecioVenta());

            ps.close();

        } catch (IOException e) {
            System.out.println("\nNo se ha podido crear el archivo");
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    public void leerArchivoCSV(String nombreArchivo) {
        File f = new File(System.getProperty("user.dir") + "/" + nombreArchivo + ".csv"); // Se crea un archivo

        if (f.exists()) {
            try {

                Scanner sc = new Scanner(f); // Se le pasa a la Clase Scanner como parametro el archivo a leer
                while (sc.hasNextLine()) // Se separa por lineas
                {
                    String line = sc.nextLine();
                    if (!line.contains("Codigo") || line.isEmpty()) {
                        String[] csvLine = line.split(",");
                        Producto p = new Producto();
                        p.setCodigo(Integer.parseInt(csvLine[0]));
                        p.setNombre(csvLine[1]);
                        p.setCantidadStock(Integer.parseInt(csvLine[2]));
                        p.setPrecioCompra(Double.parseDouble(csvLine[3]));
                        p.setPrecioVenta(Double.parseDouble(csvLine[4]));
                        listaProductos.add(p);
                    }
                }

                sc.close();

            } catch (IOException e) {
                System.err.println("\nERROR: No se ha leer el registro");
                e.getMessage();
            }

        }
    }

    public void imprimirArchivo() { //Impresion de archivo 
        System.out.println("\n" + getNombreArchivo());
        imprimirEncabezado();

        for (Producto p : listaProductos)
            System.out.println(p.toString());

        System.out.println("");
    }

    public void imprimirEncabezado() { //Impesion de encabezado de archivo
        System.out.println("\n" + setEspaciadoP("Código") + setEspaciadoP("Nombre") + setEspaciadoP("En Stock")
                + setEspaciadoP("P. Compra ($)") + setEspaciadoP("P. Venta ($)"));
    }

    public void imprimirArchivo(byte campo, String parametro) { //Impresion de archivo segun parametros
        System.out.println("\n" + getNombreArchivo());
        System.out.println("Datos encontrados del campo " + campo + " con el parámetro " + parametro);
        imprimirEncabezado();

        for (Producto p : listaProductos) {
            boolean comparador = true;
            boolean estatus = false;
            switch (campo) {
                case 1:
                    comparador = (String.valueOf(p.getCodigo()).equals(parametro));
                    estatus = true; //se revisan los datos del atributo y se comparan con la búsqueda
                    break;
                case 2:
                    comparador = (p.getNombre().toLowerCase().contains(parametro.toLowerCase()));
                    estatus = true;
                    break;
                case 3:
                    comparador = (String.valueOf(p.getCantidadStock()).equals(parametro));
                    estatus = true;
                    break;
                case 4:
                    comparador = (String.valueOf(p.getPrecioCompra()).equals(parametro));
                    estatus = true;
                    break;
                case 5:
                    comparador = (String.valueOf(p.getPrecioVenta()).equals(parametro));
                    estatus = true;
                    break;
                default:
                    System.out.println("\nOpción no válida, no hay parámetros de búsqueda de ese tipo");
                    estatus = false;
            }

            if (estatus ? comparador : false)
                System.out.println(p);
        }

        System.out.println("");
    }

    public Producto buscarSencillo(int busqueda) { //Busqueda sencilla que retorno un productos

        for (Producto prod : listaProductos) {
            if (prod.getCodigo() == busqueda) // checa solo para el código del producto
                return prod;
        }
        return null;
    }

    public Producto buscarAvanzado(String busqueda) { //Busqueda en todos sus atributos
        leerArchivoCSV(getNombreArchivo());
        busqueda = busqueda.toLowerCase();

        for (Producto prod : listaProductos) {
            if (String.valueOf(prod.getCodigo()).contains(busqueda)
                    || prod.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {// checa para los atributos
                                                                                         // identifacdores de producto

                return prod;
            }
        }
        return null;
    }

}