// Creado el 07 de Diciembre de 2020
// Creado por Elizabeth Treviño 554199, Melania Hernández 554592 y Armando Berlanga 553406
// PROYECTO FINAL DE PROGRAMACIÓN INTERMEDIA
/*  Nuestra solución consiste en crear un sistema computacional para una papelería, en donde se puedan registrar todas las compras 
y ventas que se hacen, para poder actualizar el inventario en ese mismo momento y poder crear gráficas para analizar los datos. 
Además, se podrá tener un historial de todos los movimientos, al igual que un directorio de todos los empleados y clientes. 
También se manejará un control de acceso en donde solamente los gerentes tendrán su usuario y contraseña para poder tener 
acceso a la plataforma. */ 
// Damos nuestra palabra que hemos realizado esta actividad con integridad académica.

import entidades.Empleado; //imports necesarios
import entidades.Gerente;
import movimientos.Compra;
import movimientos.Venta;
import entidades.Cliente;
import complementos.Lectura;
import directorios.DirectorioCliente;
import directorios.DirectorioEmpleado;
import directorios.DirectorioProveedor;
import inventario_productos.Inventario;
import inventario_productos.Producto;
import entidades.Proveedor;
import graficas.GraficaBarras;
import historiales.HistorialCompra;
import historiales.HistorialVenta;
import java.util.HashMap;
import complementos.Fecha;

class Principal {
    static boolean salir = false; //variables estaticas para el funcionamiento de las clases
    static DirectorioCliente dc = new DirectorioCliente("Directorio Clientes", Fecha.generarFechaHoy()); //archivos a leer dentro del programa
    static DirectorioEmpleado de = new DirectorioEmpleado("Directorio Empleados", Fecha.generarFechaHoy());
    static DirectorioProveedor dp = new DirectorioProveedor("Directorio Proveedores", Fecha.generarFechaHoy());
    static HistorialCompra hc = new HistorialCompra("Historial de compras", Fecha.generarFechaHoy());
    static HistorialVenta hv = new HistorialVenta("Historial de ventas", Fecha.generarFechaHoy());
    static Inventario i = new Inventario("Inventario", Fecha.generarFechaHoy());

    public static void imprimirMenu(String[] opciones, String tipo) { //metodo para le impresión del menu
        System.out.println("\n" + tipo + " --------------");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        System.out.println("-------------------");
    }

    public static byte validarMenu(String[] opciones, String tipo) { //metodo para la validacion del menu
        boolean valido = false;
        byte eleccion;

        do {

            imprimirMenu(opciones, tipo);

            eleccion = Lectura.inputByte("Ingresa el número de la opción del menú que deseas");

            if (eleccion >= 1 && eleccion <= opciones.length)
                valido = true;
            else
                System.out.println("Porfavor ingresa solamente un número del 1 al " + opciones.length
                        + ", según la opción que deseas.");

        } while (!valido);
        return eleccion;
    }

    //Métodos para agregar objetos
    public static void agregarVenta() //Método para agregar una venta
        {
            Venta v = new Venta(); //primero se crea el objeto venta vacío
            Empleado e = null;  //se crea el objeto empleado vacío
            boolean fin = true;
            while (fin) {
                de.imprimirArchivo();
                int datoEmpleado = Lectura.inputInteger("Ingresa la nómina del empleado");
                e = de.buscarSencillo(datoEmpleado); 
                if (e==null) 
                    System.out.println("\nNo se ha registrado ese empleado."); //se valida que la nómina ingresa sea de un empleado
                else 
                    fin = false;
            }

            Cliente c = null; //se crea el objeto cliente
            boolean fin2 = true;
            while (fin2) {
                dc.imprimirArchivo();
                String datoCliente = Lectura.inputString("Ingresa el correo electrónico del cliente");
                c = dc.buscarSencillo(datoCliente); //se valida que exista ese cliente
                if (c==null)
                    System.out.println("\nNo se ha registrado ese cliente.");
                else 
                    {
                        fin2 = false;
                        c.setNumCompras(c.getNumCompras() + 1); //con esto se incrementa el número de compras del cliente
                    }
                    
            }

            Producto prod = null; //se crea el objeto producto vacío
            boolean fin3 = true;
            while (fin3) {
                i.imprimirArchivo();
                int datoProducto = Lectura.inputInteger("Ingresa el código del producto");
                prod = i.buscarSencillo(datoProducto); //se valida que exista 
                if (prod == null)
                    System.out.println("\nNo se ha registrado ese producto.");
                else 
                    fin3 = false;
            }

            v.setFecha(v.generarFechaMov()); //llena el objeto venta 
            v.setEmpleado(e);
            v.setCliente(c);
            v.setProducto(prod);
                        
            boolean salida = true;
                 
            do {
                    v.setCantidad(Lectura.inputInteger("Ingresa la cantidad del producto")); //ahora para la cantidad
                            
                    for(Producto p : i.getListaProductos())
                        if(v.getProducto().getCodigo() == p.getCodigo()) //se valida que haya suficiente cantidad en el stock
                            {
                                if (v.getCantidad() > p.getCantidadStock()) //si no hay no se permite hacer la venta
                                    System.out.println("\nNo puede relizarse la venta, el stock es menor a lo ingresado");

                                else 
                                    {
                                        p.setCantidadStock(p.getCantidadStock() - v.getCantidad());
                                        salida = false; //si si hay, se reduce el número de productos en stock
                                    }
                            }

            } while(salida);

            v.setImporteTotal();
            hv.agregarVenta(v); //se agrega la venta en el historial

            System.out.println("\nResumen de venta");
            System.out.println(v);

            hv.generarArchivoCSV(); //se actualiza el csv del historial de ventas
            i.generarArchivoCSV(); //se actualiza el csv del inventario
            dc.generarArchivoCSV(); //se actualiza el csv del historial del cliente
            System.out.println("\nSe han actulizado todos los archivos referidos a venta");
        }

    public static void agregarCompra() //Método para agregar una compra
        {
            Compra comp = new Compra(); //primero se crea el objeto compra vacío
                
            Proveedor prov = null;
            boolean fin4 = true;
            while (fin4) {
                dp.imprimirArchivo();
                int datoProveedor = Lectura.inputInteger("Ingresa el código del proveedor");
                prov = dp.buscarSencillo(datoProveedor);
                if (prov==null) //se valida que exista el proveedor
                    System.out.println("\nNo se ha registrado ese proveedor.");
                else 
                    fin4 = false;
            }

            comp.setFecha(comp.generarFechaMov());
            comp.setProveedor(prov);

            i.imprimirArchivo();
            
            char resp = Lectura.inputCharLowercase("¿Se comprara un nuevo producto o uno ya existente?"
                + "\n[N] Nuevo\n[E] Existente", new char [] {'N', 'E'}); //por si vuelve a comprar un producto existente o uno nuevo
            Producto prod2 = new Producto();
            if (resp == 'e')
                {
                    boolean fin5 = true;
                    while (fin5) {
    
                        int datoProducto2 = Lectura.inputInteger("Ingresa el código del producto");
                        prod2 = i.buscarSencillo(datoProducto2); //se es existente, se valida que esté en el inventario
                        if (prod2==null)
                            System.out.println("\nNo se ha registrado ese producto.");
                        else 
                            fin5 = false;
                    }
                    comp.setProducto(prod2);
                    comp.setCantidad(Lectura.inputInteger("Ingresa la cantidad del producto"));
                    for(Producto p : i.getListaProductos())
                        if(comp.getProducto().getCodigo() == p.getCodigo())
                            p.setCantidadStock(p.getCantidadStock() + comp.getCantidad()); //se aumenta la cantidad en stock
                }

            else 
                {
                    prod2.llenarProducto(); //si es un producto nuevo, se permite crear un nuevo objeto de producto
                    comp.setCantidad(prod2.getCantidadStock());
                    comp.setProducto(prod2);
                    i.agregarProducto(prod2);
                    i.imprimirArchivo();
                }

            comp.setImporteTotal();
            hc.agregarCompra(comp);
            System.out.println("\nResumen de compra");
            System.out.println(comp); //se imprime la compra

            i.generarArchivoCSV(); //se actualiza el inventario
            hc.generarArchivoCSV(); //se actualiza el historial de compras
        }
    
    public static void agregarCliente() 
        {
            Cliente c1 = new Cliente();
            boolean fin6 = false;
            do {

                String email = Lectura.inputMail("Ingresa el correo eléctronico del cliente");
                Cliente c2 = dc.buscarSencillo(email);

                if (c2 != null) { // valida si el cliente existe
                    char decision = Lectura.inputCharLowercase(
                            "Ese cliente ya está registrado. ¿Quieres agregar otro? S/N",
                            new char[] { 'S', 'N' });
                    if (decision == 'n')
                        fin6 = true;
                } else {

                    c1.llenarInformacion("cliente");
                    c1.setMail(email);
                    dc.agregarCliente(c1);
                    dc.imprimirEncabezado();

                    System.out.println(c1);
                    fin6 = true;
                }

            } while (!fin6);

            dc.generarArchivoCSV();
        }

    public static void agregarEmpleado(Empleado e) //Método para agregar un empleado
        {
            int nuevoDato = de.validarRepeticion(); //se pide la nómina y se valida que no exista
            e.setNomina(nuevoDato);
            
            e.llenarInformacion("empleado"); //en este método se pregunta por todos los datos de empleado

            de.agregarEmpleado(e);
            de.imprimirEncabezado(); // se imprime
            System.out.println(e);
        }

    public static void agregararProveedor() 
        { 
            Proveedor p1 = new Proveedor();
            boolean fin7 = false;

            do {
                dp.imprimirArchivo();
                int codigo = Lectura.inputInteger("Ingresa el código del proveedor");
                Proveedor p2 = dp.buscarSencillo(codigo);

                if (p2 != null) {
                    System.out.println("Ese código ya está en uso. Intenta de nuevo."); //primero se valida que el código no esté en uso
                } else {
                    p1.setCodigo(codigo);
                    p1.llenarInformacion(); //se llama a un método
                    dp.agregararProveedor(p1);

                    dp.imprimirEncabezado();
                    System.out.println(p1);
                    fin7 = true;
                }
            } while (!fin7);
            
            dp.generarArchivoCSV();
        }

    public static void agregarProducto()
        {
            i.imprimirArchivo();
            
            Producto p = new Producto();
            p.llenarProducto();
            i.agregarProducto(p);
            i.imprimirEncabezado();
            System.out.println(p);

            i.generarArchivoCSV();
        }

    public static void consultarCliente() { //Método de consulta para cliente

        boolean fin = true;
        while (fin) { //se incializa un while para ciclar al usuario hasta que se encuentre el dato espeerado
            byte atributoCliente = validarMenu(new String[] {"Nombre", "Fecha de nacimiento", "Correo electrónico", "Teléfono", "Número de compras"}, 
                "MENÚ DE ATRIBUTOS A CONSULTAR"); //se le pide al usuario el tipo de dato por el que desea consultar
            String datoCliente = Lectura.inputString(
                    "Ingresa el cliente que deseas buscar [o escribe x para regresar al menú]"); //ahora se pide el dato en sí
            
            if (datoCliente.equalsIgnoreCase("x")) //por si el usuario quiere salir de la consulta
                fin = false;
            else {
                dc.imprimirArchivo(atributoCliente,datoCliente); //se llama a un método que busca el dato en el atributo correspondiente
                fin = false;
            }
        }
        
    }

    public static void consultarEmpleado() { //Método de consulta para empleado

        boolean fin2 = true;
        while (fin2) {
            byte atributoEmpleado = validarMenu(new String[] {"Nómina", "Nombre", "Fecha de nacimiento", "Correo electrónico", "Teléfono", "RFC",
                    "Fecha de contratación"}, "MENÚ DE ATRIBUTOS A CONSULTAR"); //hay otros atributos para empleado
            String datoEmpleado = Lectura.inputString(
                    "Ingresa el empleado que deseas buscar [o escribe x para regresar al menú]");
            if (datoEmpleado.equalsIgnoreCase("x"))
                fin2 = false;
            else {
                de.imprimirArchivo(atributoEmpleado, datoEmpleado);
                fin2 = false;
            }
        }
        
    }

    public static void consultarProveedor() { //Método de consulta para proveedor

        boolean fin3 = true;
        while (fin3) {
            byte atributoProveedor = validarMenu(new String[] {"Código", "Nombre", "Giro", "Teléfono", "Correo eléctronico", "Dirección"},
                    "MENÚ DE ATRIBUTOS A CONSULTAR"); //hay otros atributos para proveedor
            String datoProveedor = Lectura.inputString(
                    "Ingresa el proveedor que deseas buscar [o escribe x para regresar al menú]");
            if (datoProveedor.equalsIgnoreCase("x"))
                fin3 = false;
            else {
                dp.imprimirArchivo(atributoProveedor, datoProveedor);
                fin3 = false;
            }
        }
        
    }

    public static void consultarProducto() { //Método de consulta para producto

        boolean fin4 = true;
        while (fin4) {
            byte atributoProducto = validarMenu(new String[] {"Código", "Nombre", "Cantidad en stock", "Precio de compra", "Precio de venta"},
                    "MENÚ DE ATRIBUTOS A CONSULTAR"); //hay otros atributos para producto
            String datoProducto = Lectura.inputString(
                    "Ingresa el producto que deseas buscar [o escribe x para regresar al menú]");
            if (datoProducto.equalsIgnoreCase("x"))
                fin4 = false;
            else {
                i.imprimirArchivo(atributoProducto, datoProducto);
                fin4 = false;
            }
        }
        
    }
    
    public static void generarGraficoComparativo() { //Metodo para la graficacion del contenido en forma de grafica de pie
    
            GraficaBarras gb = new GraficaBarras("Comparativa", "Comparativa de ventas y compras", "Compras y Ventas",
                    "Pesos MX");

            for (Venta v : hv.getListaVentas())
                gb.agregarDato("Ventas", v.getProducto().getNombre(), v.getImporteTotal());

            for (Compra c : hc.getListaCompras())
                gb.agregarDato("Compras", c.getProducto().getNombre(), c.getImporteTotal());
                
            gb.generarGrafica();
     }

    public static void cicloMenu(String[] opciones, String tipo) { //Menu recursivo para la selección de acciones a realizar

        byte eleccionMenu = 0;
        eleccionMenu = validarMenu(opciones, tipo);

        switch (tipo) {
            case "MENÚ PRINCIPAL":
                switch (eleccionMenu) {
                    case 1: //AGREGAR
                        cicloMenu(new String[] { "Venta", "Compra", "Cliente", "Empleado", "Proveedor", "Producto",
                                "Regresar" }, "MENÚ DE DATOS A AGREGAR");
                        return;

                    case 2: //CONSULTAR
                        cicloMenu(new String[] { "Cliente", "Empleado", "Proveedor", "Producto", "Regresar" },
                                "MENÚ DE DATOS A CONSULTAR");
                        return;

                    case 3: //GRAFICAR
                        cicloMenu(new String[] { "Ventas", "Compras", "Comparativa", "Regresar" },
                                "MENÚ DE GRÁFICAS A GENERAR");
                        return;

                    case 4: // IMPRIMIR
                        cicloMenu(new String[] { "Imprimir historial de Ventas", "Imprimir historial de Compras",
                                "Imprimir directorio de Clientes", "Imprimir directorio de Empleados", "Imprimir directorio de Proveedores",
                                "Imprimir inventario de Productos", "Regresar" }, "MENÚ DE ARCHIVOS A IMPRIMIR");
                        return;

                    default:
                        salir = true;
                        return;
                }
            case "MENÚ DE DATOS A AGREGAR":
                switch (eleccionMenu) {
                    case 1: //VENTA
                        agregarVenta(); //se llama al método donde se crean los objetos venta
                        return;  

                    case 2: //COMPRA
                        agregarCompra(); //se llama al método donde se crean los objetos compra
                        return;

                    case 3: //CLIENTE
                        agregarCliente(); //se llama al método donde se crean los objetos cliente
                        return;

                    case 4: //EMPLEADO
                        char decision = Lectura.inputCharLowercase("¿Es un gerente? S/N", new char[] { 'S', 'N' });
                        if (decision == 'n') { //aquí se hace la divergencia entre empleado y gerente
                            Empleado e1 = new Empleado();
                            agregarEmpleado(e1); //si es un empleado, simplemente se crea el objeto 

                        } else {
                            Gerente e1 = new Gerente();
                            boolean validoUsuario = false;
                            String usuario;

                            do{
                            int contUsuario = 0;
                            usuario = Lectura.inputString("Ingresa el usuario para el gerente"); //si es gerente se le pregunta por usuario
                            
                            for (Gerente g : de.leerArchivoObjeto(de.getNombreArchivo())) { //para validar que no exista 
                                if (g.getUsuario().equals(usuario)) {
                                    contUsuario++;
                                }
                            }
                            if(contUsuario>0) System.out.println("Ese usuario ya existe"); 
                            else validoUsuario = true;

                            }while(!validoUsuario);

                            e1.setUsuario(usuario);
                            e1.setContrasena(Lectura.inputString("Ingresa la contraseña para el gerente")); //se pregunta la contraseña
                            agregarEmpleado(e1);
                            de.crearArchivoObjeto();
                        }

                        de.generarArchivoCSV(); //se actualiza el archivo csv con la información del empleado
                        return;

                    case 5: //PROVEEDOR
                        agregararProveedor();
                        return;

                    case 6: //PRODUCTO
                        agregarProducto();
                        return;

                    default:
                        cicloMenu(new String[] { "Agregar datos", "Consultar datos", "Generar gráfica",
                                "Imprimir archivo", "Salir" }, "MENÚ PRINCIPAL");
                        return;
                }

            case "MENÚ DE DATOS A CONSULTAR":
                switch (eleccionMenu) {
                    case 1: //CLIENTE
                        consultarCliente(); //se llama al método de consultar un cliente
                        return;

                    case 2: //EMPLEADO
                        consultarEmpleado(); //se llama al método de consultar un empleado
                        return;

                    case 3:  //PROVEEDOR
                        consultarProveedor(); //se llama al método de consultar un proveedor
                        return;

                    case 4: //PRODUCTO
                        consultarProducto(); //se llama al método de consultar un producto
                        return;

                    default:
                        cicloMenu(new String[] { "Agregar datos", "Consultar datos", "Generar gráfica",
                                "Imprimir archivo", "Salir" }, "MENÚ PRINCIPAL");
                        return;
                }

            case "MENÚ DE GRÁFICAS A GENERAR":
                switch (eleccionMenu) {
                    case 1:
                        hv.graficarContenido();
                        return;
                    case 2:
                        hc.graficarContenido();
                        return;
                    case 3:
                        generarGraficoComparativo();
                        return;
                    default:
                        cicloMenu(new String[] { "Agregar datos", "Consultar datos", "Generar gráfica",
                                "Imprimir archivo", "Salir" }, "MENÚ PRINCIPAL");
                        return;
                }

            case "MENÚ DE ARCHIVOS A IMPRIMIR":
                switch (eleccionMenu) {
                    case 1: // Historial de ventas
                        hv.imprimirArchivo();
                        return;

                    case 2: // Historial de compras
                        hc.imprimirArchivo();
                        return;

                    case 3: // Directorio de clientes
                        dc.imprimirArchivo();
                        return;

                    case 4: // Directorio de empleados
                        de.imprimirArchivo();
                        return;

                    case 5: // Directorio de proveedores
                        dp.imprimirArchivo();
                        return;

                    case 6: // Inventario de productos
                        i.imprimirArchivo();
                        return;

                    default:
                        cicloMenu(new String[] { "Agregar datos", "Consultar datos", "Generar gráfica",
                                "Imprimir archivo", "Salir" }, "MENÚ PRINCIPAL");
                        return;
                }
            default:
                return;
        }

    }

    public static void main(String[] args) { //Metodo main para el manejo de los casos
        int contIntentos = 0; //contador de los intentos validos antes de que saque el usuario del programa
        boolean accesoValido = false; //saca al usuario despues de 3 intentos, o si ya consiguió acceso
        boolean acceso = false; //permite al usuario entrar al menu
        boolean fin = true; //termina el programa, pero es una variable active-low (i.e. se termina cuando fin es false)

        do {
            String usuario = Lectura.inputString("USUARIO:"); //Lectura para el control de acceso
            String contrasena = Lectura.inputString("CONTRASEÑA:");

            for (Gerente g : de.leerArchivoObjeto(de.getNombreArchivo())) { //De la lista de gerentes se lee usuarios y contraseña
                if (g.getUsuario().equals(usuario) && g.getContrasena().equals(contrasena)){ //si coincide
                    acceso = true;  //el acceso es permitido
                    accesoValido = true;
                }
            } 

            if (acceso) { //si el acceso es permitido
                
                dc.leerArchivoCSV(dc.getNombreArchivo()); //se lee el contenido de todos los archivos
                de.leerArchivoCSV(de.getNombreArchivo());
                dp.leerArchivoCSV(dp.getNombreArchivo());
                hc.leerArchivoCSV(hc.getNombreArchivo());
                hv.leerArchivoCSV(hv.getNombreArchivo());
                i.leerArchivoCSV(i.getNombreArchivo());
                
                do {
                    cicloMenu(new String[] { "Agregar datos", "Consultar datos", "Generar gráfica", "Imprimir archivo", 
                            "Salir" }, "MENÚ PRINCIPAL"); //se pasa al menu principal
                    if (!salir) {
                        char resp = Lectura.inputCharLowercase("\n¿Quieres hacer otra acción? S/N", //se regunta si se realizara alguna otra accion
                                new char[] { 'S', 'N' });

                        if (resp == 'n')
                            fin = false;
                    } else
                        fin = false;

                } while (fin);
            
            } else{ //validacion de usuario o contraseña no valido
                System.out.println("Usuario y/o contraseña incorrectos.");
                contIntentos++;
            }
            if(contIntentos == 3){ //maximo de intentos = 3; termina el programa
                System.out.println("\nNúmero de intentos excedidos.");
                accesoValido = true;
            }
        } while (!accesoValido); //validacion final
    }
}