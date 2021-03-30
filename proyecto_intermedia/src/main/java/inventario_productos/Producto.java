package inventario_productos;

import complementos.Fecha;
import complementos.Lectura;

public class Producto {

    private int codigo;
    private String nombre;
    private int cantidadStock;
    private double precioCompra;
    private double precioVenta;

    public Producto() {

    }

    public Producto(int codigo, String nombre, int cantidadStock, double precioCompra, double precioVenta) {

        setCodigo(codigo);
        setNombre(nombre);
        setCantidadStock(cantidadStock);
        setPrecioCompra(precioCompra);
        setPrecioVenta(precioVenta);
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    protected String setEspaciado(String input) { //método para imprimir con espaciado
        String espaciado = "                             ";
        input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

        return input;
    }

    protected String setEspaciadoP(String input) { //método para imprimir con espaciado más pequeño
        String espaciado = "                ";
        input += espaciado.substring(0, espaciado.length() - String.valueOf(input).length());

        return input;
    }

    @Override
    public String toString() {
        return setEspaciadoP(String.valueOf(codigo)) + setEspaciadoP(nombre)
                + setEspaciadoP(String.valueOf(cantidadStock)) + setEspaciadoP(String.valueOf(precioCompra))
                + setEspaciadoP(String.valueOf(precioVenta));
    }

    public void llenarProducto() {
        boolean salida = false;
        int codigoProducto = 0;
        do {

            codigoProducto = Lectura.inputInteger("Ingresa el código del producto", "-", 0);
            salida = validarCodigo(codigoProducto);

        } while (salida);

        setCodigo(codigoProducto);
        setNombre(Lectura.inputString("Ingresa el nombre del producto: "));
        setCantidadStock(Lectura.inputInteger("Ingresa la cantidad de stock del producto: ", "-", 0));
        setPrecioCompra(Lectura.inputDouble("Ingresa el precio de compra del producto: "));
        setPrecioVenta(Lectura.inputDouble("Ingresa el precio de venta del producto: "));

    }

    public boolean validarCodigo(int codigo) {
        Inventario i = new Inventario("Inventario", Fecha.generarFechaHoy());
        i.leerArchivoCSV(i.getNombreArchivo());
        for (Producto p : i.getListaProductos())
            if (codigo == p.getCodigo()) {
                System.out.println("\nYa existe este producto, por favor ingresa uno distinto ");
                return true;
            }
        return false;
    }
}