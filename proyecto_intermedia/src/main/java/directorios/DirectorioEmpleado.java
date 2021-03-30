package directorios;

import java.util.ArrayList; //imports necesarios
import entidades.Empleado;
import entidades.Gerente;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import complementos.Fecha;
import complementos.Lectura;
import complementos.Nombre;

public class DirectorioEmpleado extends Directorio { //clase hija de directorio
    private ArrayList <Empleado> listaEmpleados;

    public DirectorioEmpleado() { //Constructor vacio para la inicialziacion por medio de sets
        listaEmpleados = new ArrayList<Empleado>();
    }

    public DirectorioEmpleado(String descripcion, Fecha creacionDoc) { //Construcotr con atributos a inicialziar
        super(descripcion, creacionDoc);
        listaEmpleados = new ArrayList<Empleado>();
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) { //Sets y gets pertinenetes
        this.listaEmpleados = listaEmpleados;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void agregarEmpleado(Empleado e) {
        listaEmpleados.add(e);
    }

    @Override
    public void generarArchivoCSV() { //Generacion del archivo CSV
        try {

            File f = new File(System.getProperty("user.dir") + "/" + getNombreArchivo() + ".csv");
            FileOutputStream fos = new FileOutputStream(f);
            PrintStream ps = new PrintStream(fos);

            ps.print("Nombre,F. Nacimiento,Mail,Telefono,RFC,F. Contratacion,Nomina");
            for (Empleado e : listaEmpleados)
                ps.print("\n" + e.getNombre() + "," + e.getFechaNacimiento() + "," + e.getMail() + "," + e.getTelefono()
                        + "," + e.getRFC() + "," + e.getFechaContratacion() + "," + e.getNomina());

            ps.close();

        } catch (IOException e) {
   
            System.out.println("\nNo se ha podido crear el archivo");
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public void leerArchivoCSV(String nombreArchivo) { //Lectura del archivo csv
        File f = new File(System.getProperty("user.dir") + "/" + nombreArchivo + ".csv"); // Se crea un archivo

        if (f.exists()) {
            try {

                Scanner sc = new Scanner(f); // Se le pasa a la Clase Scanner como parametro el archivo a leer
                while (sc.hasNextLine()) // Se separa por lineas
                {
                    String line = sc.nextLine();
                    if (!line.contains("Nombre")) {
                        String[] csvLine = line.split(",");
                        Empleado e = new Empleado();
                        e.setNombre(new Nombre(csvLine[0]));
                        e.setFechaNacimiento(new Fecha(csvLine[1]));
                        e.setMail(csvLine[2]);
                        e.setTelefono(Long.parseLong(csvLine[3]));
                        e.setRFC(csvLine[4]);
                        e.setFechaContratacion(new Fecha(csvLine[5]));
                        e.setNomina(Integer.parseInt(csvLine[6]));
                        listaEmpleados.add(e);
                    }
                }

                sc.close();

            } catch (IOException e) {
                System.err.println("\nERROR: No se ha leer el registro");
                e.getMessage();
            }

        }
    }

    @Override
    public void imprimirArchivo() { //Impresion del archivo completo
        System.out.println("\n" + descripcion + " " + creacionDoc);
        imprimirEncabezado();

        for (Empleado e : listaEmpleados)
            System.out.println(e.toString());

        System.out.println("");
    }

    @Override
    public void imprimirArchivo(byte campo, String parametro) { //Impresion del archivo solo si cumplen con ciertos parametros
        System.out.println("\n" + descripcion + " " + creacionDoc);
        System.out.println("Datos encontrados del campo " + campo + " con el parámetro " + parametro);
        imprimirEncabezado();

        for (Empleado e : listaEmpleados) {
            boolean comparador = true;
            boolean estatus = false;
            switch (campo) {
                case 1:
                    comparador = (String.valueOf(e.getNomina()).equals(parametro));
                    estatus = true;
                break;
                case 2:
                    comparador = (e.getNombre().toString().toLowerCase().contains(parametro.toLowerCase()));
                    estatus = true;
                    break;
                case 3:
                    comparador = (e.getFechaNacimiento().toString().contains(parametro));
                    estatus = true;
                    break;
                case 4:
                    comparador = (e.getMail().contains(parametro));
                    estatus = true;
                    break;
                case 5:
                    comparador = (String.valueOf(e.getTelefono()).contains(parametro));
                    estatus = true;
                    break;
                case 6:
                    comparador = (e.getRFC().contains(parametro));
                    estatus = true;
                    break;
                case 7:
                    comparador = (e.getFechaContratacion().toString().contains(parametro));
                    estatus = true;
                    break;
                default:
                    System.out.println("\nOpción no válida, no hay parámetros de búsqueda de ese tipo");
                    estatus = false;
            }

            if (estatus ? comparador : false)
                System.out.println(e);
        }

        System.out.println("");
    }

    public Empleado buscarSencillo(int busqueda) { //Consulta y retorno de empleado al consultar por nomina

        for (Empleado e : listaEmpleados) {
            if (e.getNomina() == busqueda) // checa para solamente por nómina
                return e;
        }

        return null;
    }

    public Empleado buscarAvanzado(String busqueda) { //Consulta y retorno de un empleado por un parametro existente dentro de sus atributos

        busqueda = busqueda.toLowerCase();

        for (Empleado e : listaEmpleados) {
            if (e.getNombre().toString().toLowerCase().contains(busqueda)
                    || e.getMail().toLowerCase().contains(busqueda)
                    || String.valueOf(e.getTelefono()).contains(busqueda)
                    || e.getFechaNacimiento().toString().contains(busqueda)
                    || String.valueOf(e.getNomina()).contains(busqueda)) {// checa para los atributos identifacdores de
                                                                          // empleado
                return e;
            }
        }
        return null;
    }

    public int validarRepeticion() { //Metodo para validar que no se repita el ingresar una nomina de empleado

        int dato = 0;
        boolean valid = true;
        while (valid) {
            int cont = 0;
            dato = Lectura.inputInteger("Ingresa la nómina del empleado");

            for (Empleado e : listaEmpleados) {

                if (e.getNomina() == dato) {
                    System.out.println("\nYa hay un empleado con esa nómina");
                    cont++;
                }
            }

            if (cont == 0)
                valid = false;
            else
                valid = true;
        }
        return dato;
    }

    public void imprimirEncabezado() { //Impresion de encabezados
        System.out.println("\n" + setEspaciado("Nombre") + setEspaciadoP("F. Nacimiento") + setEspaciado("Correo electrónico")
                + setEspaciadoP("Teléfono") + setEspaciadoP("RFC") + setEspaciadoP("F. Contratación")
                + setEspaciadoP("Nómina"));
    }

    public ArrayList <Gerente> leerArchivoObjeto(String fileName) { //Lectura de archivos de Objetos
        ArrayList<Gerente> gerentes = new ArrayList<Gerente>();

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {

            File f = new File(System.getProperty("user.dir") + "/" + fileName + ".dat");
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            System.out.println("");
            while (true) {
                Gerente cc = (Gerente) ois.readObject();
                gerentes.add(cc);
            }

        } catch (EOFException e) {

        }
        catch (IOException e) {
            System.out.println("\nNo se ha podido leer el archivo de objetos");
            System.err.println("ERROR: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("\nClase no encontrada");
            e.printStackTrace();
        }

        finally {

            try {

                ois.close();
                fis.close();

            } catch (IOException e) {
                
            }

        }
        return gerentes;
    }

    public void crearArchivoObjeto() { //Creacion de archivo de objetos para almacenar a los Gerentes que ocupan confidencialidad en usuarios y contras
        File f = new File (getNombreArchivo() + ".dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        ArrayList <Gerente> gerentesPasados = null; //Se inicializa null el arreglo de los posibles gerentes
        if(f.exists()) //Si el archivo existe
            gerentesPasados = leerArchivoObjeto(getNombreArchivo()); //Los guarda
        
        try { 

            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);

            for (Empleado e : listaEmpleados) { //escribe los nuevos que se hayan ingresado
                if(e instanceof Gerente)
                    oos.writeObject(e);
            }

            if (gerentesPasados != null) //Se agregan los pasados
                for (Gerente g: gerentesPasados)
                    oos.writeObject(g);

            oos.close(); //Cierre de objetos
            fos.close();

        } catch (IOException e) { //Excepcion final de la falla en creacion de objetos
            System.err.println("\nERROR: no se ha podido crear el archivo");
            System.out.println(e.getMessage());
        }
    }
}