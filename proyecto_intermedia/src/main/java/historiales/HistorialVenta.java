package historiales;

import java.io.File; //imports necesarios
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import complementos.Fecha;
import directorios.DirectorioCliente;
import directorios.DirectorioEmpleado;
import graficas.GraficaPie;
import inventario_productos.Inventario;
import movimientos.Venta;

public class HistorialVenta extends Historial { //clase hija de la clase abstacta historial
    private ArrayList <Venta> listaVentas; //lista de ventas registradas
    
    public HistorialVenta (String descripcion, Fecha fechaCreacion) //constructor para la inicializacion de atributos padres
        {
           super(descripcion, fechaCreacion);
           listaVentas = new ArrayList <Venta> ();
        }

    public void setListaVentas(ArrayList<Venta> listaVentas) //sets y gets pertinentes
        {
            this.listaVentas = listaVentas;
        }

    public ArrayList<Venta> getListaVentas() 
        {
            return listaVentas;
        }

    public void agregarVenta (Venta v)
        {
            listaVentas.add(v);
        }

    @Override
    public void generarArchivoCSV () //Metodo para la impresion del archivo completo
        {
            DecimalFormat df = new DecimalFormat(".0");
            try {

                File f = new File(System.getProperty("user.dir") + "/" + getNombreArchivo() + ".csv");
                FileOutputStream fos = new FileOutputStream(f);
                PrintStream ps = new PrintStream(fos);

                ps.print("Fecha,Producto,Cantidad,Importe total,Empleado,Nomina,Cliente,Telefono");
                for (Venta v : listaVentas)
                    ps.print("\n"+v.getFecha() + "," + v.getProducto().getNombre() + "," +  v.getCantidad() 
                    + "," + df.format(v.getImporteTotal()) + "," + v.getEmpleado().getNombre() + "," + v.getEmpleado().getNomina() 
                    + "," + v.getCliente().getNombre() + "," + v.getCliente().getTelefono());

                ps.close();

            } catch (IOException e) 
                {
                    System.out.println("\nNo se ha podido crear el archivo");
                    System.err.println("ERROR: " + e.getMessage());
                }
        }

    public void leerArchivoCSV(String nombreArchivo) //Metodo para la lectura del archivo
        {
            File f = new File (System.getProperty("user.dir") + "/" + nombreArchivo + ".csv"); //Se crea un archivo 

            DirectorioEmpleado de = new DirectorioEmpleado ("Directorio Empleados", new Fecha(11, 11 ,2020));
            de.leerArchivoCSV(de.getNombreArchivo());

            Inventario i = new Inventario("Inventario", new Fecha(12,12, 2020));
            i.leerArchivoCSV(i.getNombreArchivo());
            
            DirectorioCliente dc = new DirectorioCliente("Directorio Clientes", new Fecha(11, 11, 2020));
            dc.leerArchivoCSV(dc.getNombreArchivo());
                
            if (f.exists()) 
                {
                    try {
                            
                        Scanner sc = new Scanner(f); //Se le pasa a la Clase Scanner como parametro el archivo a leer
                        while (sc.hasNextLine()) //Se separa por lineas
                            {
                                String line = sc.nextLine();
                                if (!line.contains("Cantidad"))
                                    {
                                        String [] csvLine = line.split(",");
                                        Venta v = new Venta();
                                        v.setFecha(new Fecha(csvLine[0]));
                                        v.setProducto(i.buscarAvanzado(csvLine[1]));
                                        v.setCantidad(Integer.parseInt(csvLine[2]));
                                        v.setImporteTotal(Double.parseDouble(csvLine[3]));
                                        v.setEmpleado(de.buscarAvanzado(csvLine[5]));
                                        v.setCliente(dc.buscarAvanzado(csvLine[7]));

                                        listaVentas.add(v);
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
    public void graficarContenido() //Graficacion de contenido de la lista en forma de grafica de pie
        {
            GraficaPie gp = new GraficaPie("Ventas por producto", "Proporción de ventas por producto");

            HashMap <String, Double> datos = new HashMap <String, Double> ();
            for (Venta v : getListaVentas())
                {
                    if (v.getProducto().getNombre() != null && datos.containsKey(v.getProducto().getNombre()))
                        datos.put(v.getProducto().getNombre(), datos.get(v.getProducto().getNombre()) + v.getImporteTotal());

                    else
                        datos.put(v.getProducto().getNombre(), v.getImporteTotal());
                }

            for (String key : datos.keySet())
                gp.agregarDato(key , datos.get(key));

            gp.generarGrafica();
        }

    @Override
    public void imprimirArchivo() //Impresion de todos los elementos registrados en la lista
        {               
            System.out.println("\n" + getNombreArchivo());
            System.out.println("\n" + setEspaciadoP("Fecha") + setEspaciado("Producto") + setEspaciadoP("Cantidad") 
            + setEspaciadoP("Importe total") + setEspaciado("Empleado") + setEspaciadoP("Nómina") + setEspaciado("Cliente") + setEspaciadoP("Teléfono"));
        
            for (Venta v : listaVentas)
                System.out.println(setEspaciadoP(v.getFecha().toString()) + setEspaciado(v.getProducto().getNombre()) 
                +  setEspaciadoP(String.valueOf(v.getCantidad())) + setEspaciadoP(String.valueOf(v.getImporteTotal())) 
                + setEspaciado(v.getEmpleado().getNombre().toString()) + setEspaciadoP(String.valueOf(v.getEmpleado().getNomina()))
                + setEspaciado(v.getCliente().getNombre().toString()) + setEspaciadoP(String.valueOf(v.getCliente().getTelefono())));

            System.out.println("\n");
        }   
}