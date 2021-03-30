package movimientos;

import complementos.Fecha;
import inventario_productos.Producto;

import java.text.DecimalFormat;
import java.time.LocalDate;


abstract public class Movimiento {
    
    protected Fecha fecha;
    protected Producto producto;
    protected int cantidad;
    protected double importeTotal;

    public Movimiento () {

    }

    public Movimiento(Fecha fecha, Producto producto, int cantidad, double importeTotal) {

        setFecha(fecha);
        setProducto(producto);
        setCantidad(cantidad);
        setImporteTotal(importeTotal);
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    abstract public double setImporteTotal(); //el método se hace abstracto porque en venta y compra se implementan según el precio de compra/venta

    public Fecha getFecha() {
        return fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    
    public Fecha generarFechaMov() {
        String fecha = LocalDate.now().toString();
        String[] datos = fecha.split("-");
        return new Fecha(Integer.parseInt(datos[2]), Integer.parseInt(datos[1]), Integer.parseInt(datos[0]));
    }

    public String toString() {
        DecimalFormat y = new DecimalFormat("0.00");

        return "\nFecha: "+fecha+"\nProducto: "+producto.getNombre() +"\nPrecio unitario: " 
        + (this instanceof Venta ? producto.getPrecioVenta() : producto.getPrecioCompra())
        + "\nCantidad: "+cantidad+"\nImporte total: $"+y.format(setImporteTotal());

    }

}
