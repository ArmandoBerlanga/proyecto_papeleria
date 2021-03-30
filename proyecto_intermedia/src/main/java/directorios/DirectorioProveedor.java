package directorios;

import java.io.File; //imports pertinenetes
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import complementos.Fecha;
import entidades.Proveedor;

public class DirectorioProveedor extends Directorio { //clase hija de directorio
    private ArrayList <Proveedor> listaProveedores; //atributo lista de proveedores

    public DirectorioProveedor() //Construcor vacio para poder usar sets
        {
            listaProveedores = new ArrayList <Proveedor> ();
        }

    public DirectorioProveedor(String descripcion, Fecha creacionDoc) //Constructor con parametros de super
        {
            super(descripcion, creacionDoc);
            listaProveedores = new ArrayList <Proveedor> ();
        }

    public void setListaProveedores(ArrayList<Proveedor> listaProveedores) //sets y gets pertinentes
        {
            this.listaProveedores = listaProveedores;
        }

    public ArrayList<Proveedor> getListaProveedores() 
        {
            return listaProveedores;
        }

    public void agregararProveedor (Proveedor p) //Metodo para agregar un proeveedor
        {
            listaProveedores.add(p);
        }

    @Override
    public void generarArchivoCSV() //Metodo sobreescrito para la creacion de un archivo
        {
            try {

                File f = new File(System.getProperty("user.dir") + "/" + getNombreArchivo() + ".csv");
                FileOutputStream fos = new FileOutputStream(f);
                PrintStream ps = new PrintStream(fos);

                ps.print("Codigo,Nombre,Giro,Telefono,Mail,Dirección");
                for (Proveedor p : listaProveedores) //Iteracion por medio de un for
                    ps.print("\n" + p.getCodigo() + "," + p.getNombre() + ","+ p.getGiro() 
                    + "," + p.getTelefono() + "," + p.getMail() + "," + p.getDireccion());
                    
                ps.close();

            } catch (IOException e) 
                {
                    System.out.println("\nNo se ha podido crear el archivo");
                    System.err.println("ERROR: " + e.getMessage());
                }
        }

    public void leerArchivoCSV(String nombreArchivo) //Lectura de archivos
        {
            File f = new File (System.getProperty("user.dir") + "/" + nombreArchivo + ".csv"); //Se crea un archivo 
			
			if (f.exists()) 
				{
					try {
						
						Scanner sc = new Scanner(f); //Se le pasa a la Clase Scanner como parametro el archivo a leer
						while (sc.hasNextLine()) //Se separa por lineas
							{
                                String line = sc.nextLine();
                                if (!line.contains("Codigo"))
                                    {
                                        String [] csvLine = line.split(",");
                                        Proveedor p = new Proveedor();
                                        p.setCodigo(Integer.parseInt(csvLine[0]));
                                        p.setNombre(csvLine[1]);            
                                        p.setGiro(csvLine[2]);
                                        p.setTelefono(Long.parseLong(csvLine[3]));
                                        p.setMail(csvLine[4]);
                                        p.setDireccion(csvLine[5]);
                                        listaProveedores.add(p);
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

    public void imprimirArchivo() //Impresion de archivos
        {            
            System.out.println("\n" + getNombreArchivo());   
            imprimirEncabezado();
            for (Proveedor p : listaProveedores)
                System.out.println(p.toString());

            System.out.println("");
        }

    public void imprimirArchivo(byte campo, String parametro) //Impresion de archivos por parametro
        {               
            System.out.println("\n" + getNombreArchivo());
            System.out.println("Datos encontrados del campo " + campo + " con el parámetro " + parametro);
            imprimirEncabezado();
            
            for (Proveedor p : listaProveedores)
                {
                    boolean comparador = true;
                    boolean estatus = false;
                    switch (campo)
                        {
                            case 1: 
                                comparador = (String.valueOf(p.getCodigo()).equals(parametro));
                                estatus = true; //compara los atributos los objetos con la búsqueda
                                break;
                            case 2: 
                                comparador = (p.getNombre().toLowerCase().contains(parametro.toLowerCase()));
                                estatus = true;
                                break;
                            case 3: 
                                comparador = (p.getGiro().toLowerCase().contains(parametro.toLowerCase()));
                                estatus = true;
                                break;
                            case 4: 
                                comparador = (String.valueOf(p.getTelefono()).contains(parametro));
                                estatus = true;
                                break;
                            case 5:
                                comparador = (String.valueOf(p.getMail()).contains(parametro));
                                estatus = true;
                                break;
                            case 6:
                                comparador = (p.getDireccion().toLowerCase().contains(parametro.toLowerCase()));
                                estatus = true;
                                break;
                            default: 
                                System.out.println("\nOpcián no válida, no hay parámetros de búsqueda de ese tipo");
                                estatus = false;   
                        }

                    if (estatus ? comparador : false)
                        System.out.println(p);
                }
                
            System.out.println("");
        }

    

    public void imprimirEncabezado(){ //Impresion de encabezado
        System.out.println("\n" + setEspaciadoP("Código") + setEspaciado("Nombre") + setEspaciadoP("Giro")
                + setEspaciadoP("Teléfono") + setEspaciado("Correo electrónico") + setEspaciado("Dirección"));
    }
    
    public Proveedor buscarSencillo(int busqueda) //Busqueda de un solo objeto por codigo del proveedor
        {          
            for (Proveedor p : listaProveedores)
            {
                if (p.getCodigo()==busqueda) //checa para los atributos identifacdores de proveedor
                    return p;
            }
            return null;
        }  
    
    public Proveedor buscarAvanzado(String busqueda) //Consulta por parametro dentro de todos los objetos
        {
            busqueda = busqueda.toLowerCase();
            
            for (Proveedor p : listaProveedores)
            {
                if (String.valueOf(p.getCodigo()).contains(busqueda)||p.getNombre().toLowerCase().contains(busqueda)||
                p.getMail().contains(busqueda)|| Long.toString(p.getTelefono()).contains(busqueda)||p.getDireccion().toLowerCase().contains(busqueda)) {
                //se revista para los atributos identifacdores de proveedor
                    
                    return p;
                }
            }
            return null;
        }       
}