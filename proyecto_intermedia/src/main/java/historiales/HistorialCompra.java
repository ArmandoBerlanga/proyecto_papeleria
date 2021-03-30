package historiales;

import java.io.File; //Imports necesarios para la clase
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import complementos.Fecha;
import directorios.DirectorioProveedor;
import graficas.GraficaPie;
import inventario_productos.Inventario;
import movimientos.Compra;

public class HistorialCompra extends Historial { //clase hija de la clase abstacta Historial
    private ArrayList <Compra> listaCompras; //Lista de compras registradas

    public HistorialCompra(String descripcion, Fecha fechaCreacion) //Constructor para la incializacion de atributos
        {
            super(descripcion, fechaCreacion);
            listaCompras = new ArrayList <Compra>();
        }

    public void setListaCompras(ArrayList <Compra> listaCompras) //sets y gets pertinentes
        {
            this.listaCompras = listaCompras;
        }

    public ArrayList <Compra> getListaCompras() 
        {
            return listaCompras;
        }

    public void agregarCompra(Compra c) 
        {
            listaCompras.add(c);
        }

    @Override
    public void generarArchivoCSV() //Metodo para la generacion de archivos
        {
            DecimalFormat df = new DecimalFormat(".0");
            try {

                File f = new File(System.getProperty("user.dir") + "/" + getNombreArchivo() + ".csv");
                FileOutputStream fos = new FileOutputStream(f);
                PrintStream ps = new PrintStream(fos);

                ps.print("Fecha,Producto,Cantidad,Importe total,Proveedor,ID proveedor");
                for (Compra c : listaCompras)
                    ps.print("\n"+c.getFecha() + "," + c.getProducto().getNombre() + "," + 
                    c.getCantidad() + "," + df.format(c.getImporteTotal()) + "," + c.getProveedor().getNombre() 
                    + "," + c.getProveedor().getCodigo());

                ps.close();

            } catch (IOException e) 
                {
                    System.out.println("\nNo se ha podido crear el archivo");
                    System.err.println("ERROR: " + e.getMessage());
                }
        }

    @Override
    public void leerArchivoCSV(String nombreArchivo) //Metodo para la elctura de archivos
        {
            File f = new File (System.getProperty("user.dir") + "/" + nombreArchivo + ".csv"); //Se crea un archivo 

            DirectorioProveedor dp = new DirectorioProveedor("Directorio Proveedores", new Fecha (11,11,2020));
            dp.leerArchivoCSV(dp.getNombreArchivo());

            Inventario i = new Inventario("Inventario", new Fecha(12,12, 2020));
            i.leerArchivoCSV(i.getNombreArchivo());
                
            if (f.exists()) 
                {
                    try {
                            
                        Scanner sc = new Scanner(f); //Se le pasa a la Clase Scanner como parametro el archivo a leer
                        while (sc.hasNextLine()) //Se separa por lineas
                            {
                                String line = sc.nextLine();
                                if (!line.contains("ID proveedor"))
                                    {
                                        String [] csvLine = line.split(",");
                                        Compra c = new Compra();
                                        c.setFecha(new Fecha (csvLine[0]));
                                        c.setProducto(i.buscarAvanzado(csvLine[1]));
                                        c.setCantidad(Integer.parseInt(csvLine[2]));
                                        c.setImporteTotal(Double.parseDouble(csvLine[3]));
                                        c.setProveedor(dp.buscarAvanzado(csvLine[5]));
                                        listaCompras.add(c);
                                    }
                             }
                        
                        sc.close();
                            
                    } catch (IOException e) 
                        {
                            System.err.println("\nERROR: No se ha leer el registro");
                            e.getMessage(); 
                        }
                }
        }

    @Override
    public void graficarContenido() //Metodo para graficar el contenido de la lista en grafica de pie
        {
            GraficaPie gp = new GraficaPie("Compras por producto", "Proporción de compras por producto");


            HashMap <String, Double> datos = new HashMap <String, Double> ();
            for (Compra c : getListaCompras())
                {
                    if (c.getProducto().getNombre() != null && datos.containsKey(c.getProducto().getNombre()))
                        datos.put(c.getProducto().getNombre(), datos.get(c.getProducto().getNombre()) + c.getImporteTotal());

                    else
                        datos.put(c.getProducto().getNombre(), c.getImporteTotal());
                }

            for (String key : datos.keySet())
                gp.agregarDato(key , datos.get(key));

            gp.generarGrafica();
        }

    @Override
    public void imprimirArchivo() //Metodo de impresion de archivo completo
        {               
            System.out.println("\n" + getNombreArchivo());
            System.out.println("\n" + setEspaciadoP("Fecha") + setEspaciado("Producto") + setEspaciadoP("Cantidad") 
            + setEspaciadoP("Importe total") + setEspaciadoP("Proveedor") + setEspaciadoP("ID proveedor"));
            
            for (Compra c : listaCompras)
                System.out.println(setEspaciadoP(c.getFecha().toString()) + setEspaciado(c.getProducto().getNombre()) 
                +  setEspaciadoP(String.valueOf(c.getCantidad())) + setEspaciadoP(String.valueOf(c.getImporteTotal())) 
                + setEspaciadoP(c.getProveedor().getNombre()) + setEspaciadoP(String.valueOf(c.getProveedor().getCodigo())));

            System.out.println("\n");
        }
}