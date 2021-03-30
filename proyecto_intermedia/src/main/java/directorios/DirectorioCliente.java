package directorios;

import java.io.File; //imports necesarios
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import complementos.Fecha;
import complementos.Nombre;
import entidades.Cliente;

public class DirectorioCliente extends Directorio { //clase hija de directorio
    private ArrayList<Cliente> listaClientes; //lista de clientes como parametro

    public DirectorioCliente() { //Constructor vacio para incialziarlo con sets
        listaClientes = new ArrayList<Cliente>();
    }

    public DirectorioCliente(String descripcion, Fecha creacionDoc) { //Constructor con atributos heredados
        super(descripcion, creacionDoc);
        listaClientes = new ArrayList<Cliente>();
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) { //sets y gets pertinentes
        this.listaClientes = listaClientes;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void agregarCliente(Cliente c) { //Metodo para agrega un cliente
        listaClientes.add(c);
    }

    @Override
    public void generarArchivoCSV() { //Metodo para la generacion del CSV
        try {

            File f = new File(System.getProperty("user.dir") + "/" + getNombreArchivo() + ".csv");
            FileOutputStream fos = new FileOutputStream(f);
            PrintStream ps = new PrintStream(fos);

            ps.print("Nombre,F. Nacimiento,Mail,Telefono,Num. Compras");
            for (Cliente c : listaClientes)
                ps.print("\n" + c.getNombre() + "," + c.getFechaNacimiento() + "," + c.getMail() + "," + c.getTelefono()
                        + "," + c.getNumCompras());

            ps.close();

        } catch (IOException e) {
            System.out.println("\nNo se ha podido crear el archivo");
            System.err.println("ERROR: " + e.getMessage());
        }

    }

    @Override
    public void leerArchivoCSV(String nombreArchivo) { //Metodo para la elctura del CSV
        File f = new File(System.getProperty("user.dir") + "/" + nombreArchivo + ".csv"); // Se crea un archivo

        if (f.exists()) {
            try {

                Scanner sc = new Scanner(f); // Se le pasa a la Clase Scanner como parametro el archivo a leer
                while (sc.hasNextLine()) // Se separa por lineas
                {
                    String line = sc.nextLine();
                    if (!line.contains("Nombre")) {
                        String[] csvLine = line.split(",");
                        Cliente c = new Cliente();
                        c.setNombre(new Nombre(csvLine[0]));
                        c.setFechaNacimiento(new Fecha(csvLine[1]));
                        c.setMail(csvLine[2]);
                        c.setTelefono(Long.parseLong(csvLine[3]));
                        c.setNumCompras(Long.parseLong(csvLine[4]));
                        listaClientes.add(c);
                    }
                }

                sc.close();

            } catch (IOException e) {
                System.err.println("\nERROR: No se ha podido leer el registro");
                e.getMessage();
            }

        }
    }

    @Override
    public void imprimirArchivo() { //Metodo para la impresion del archivo
        System.out.println("\n" + getNombreArchivo());
        imprimirEncabezado();

        for (Cliente c : listaClientes)
            System.out.println(c.toString());

        System.out.println("");
    }

    public void imprimirEncabezado() { //Impresion del encabezado en consola
        System.out.println("\n" + setEspaciado("Nombre") + setEspaciadoP("F. Nacimiento") + setEspaciado("Correo electrónico")
                + setEspaciadoP("Teléfono") + setEspaciadoP("Num. Compras"));
    }

    @Override
    public void imprimirArchivo(byte campo, String parametro) { //Impresion de archivo segun parametro buscado en algun campo
        imprimirEncabezado();

        for (Cliente c : listaClientes) {
            boolean comparador = true;
            boolean estatus = false;
            switch (campo) {
                case 1:
                    comparador = (c.getNombre().toString().toLowerCase().contains(parametro.toLowerCase()));
                    estatus = true; //se compara el objeto con al búsqueda
                    break;
                case 2:
                    comparador = (c.getFechaNacimiento().toString().contains(parametro));
                    estatus = true;
                    break;
                case 3:
                    comparador = (c.getMail().contains(parametro));
                    estatus = true;
                    break;
                case 4:
                    comparador = (String.valueOf(c.getTelefono()).contains(parametro));
                    estatus = true;
                    break;
                case 5:
                    comparador = (String.valueOf(c.getNumCompras()).equals(parametro));
                    estatus = true;
                    break;
                default:
                    System.out.println("\nOpción no válida, no hay parámetros de búsqueda de ese tipo");
                    estatus = false;
            }

            if (estatus ? comparador : false)
                System.out.println(c); //si se se encuentra un dato se imprime
        }

        System.out.println("");
    }

    public Cliente buscarSencillo(String busqueda) { //Consulta y retorno de Cliente segun un paramétro de búsqueda en el mail 
        for (Cliente c : listaClientes) {
            if (c.getNombre().toString().toLowerCase().contains(busqueda.toLowerCase())
                    || c.getMail().contains(busqueda) || String.valueOf(c.getTelefono()).contains(busqueda))
                return c;
        }
        return null;
    }

    public Cliente buscarAvanzado(String busqueda) { //Consulta y retorno de cliente segun un paramétro de búsqueda en sus n atributos
        leerArchivoCSV("Directorio Clientes 2020");
        busqueda = busqueda.toLowerCase();

        for (Cliente c : listaClientes) {
            if (c.getNombre().toString().toLowerCase().contains(busqueda.toLowerCase())
                    || c.getMail().contains(busqueda) || String.valueOf(c.getTelefono()).contains(busqueda)
                    || c.getFechaNacimiento().toString().contains(busqueda)) {// checa para los atributos identifacdores
                                                                              // de cliente
                return c;
            }
        }
        return null;
    }
}