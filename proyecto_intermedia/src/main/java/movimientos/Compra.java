package movimientos;

import complementos.Fecha;
import entidades.Proveedor;
import inventario_productos.Producto;

public class Compra extends Movimiento {

    private Proveedor proveedor;

    public Compra(Fecha fecha, Producto producto, int cantidad, double importeTotal, Proveedor proveedor){
        super(fecha, producto, cantidad, importeTotal);
        setProveedor(proveedor); //se agrega proveedor
    }

    public Compra() {
        
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    @Override
    public double setImporteTotal() { //se calcula importe total considerando el precio de compra
        setImporteTotal(producto.getPrecioCompra() * cantidad);
        return importeTotal;
    }

    @Override
    public String toString() {
        return super.toString()+"\nProveedor: " + proveedor.getNombre(); //en el toString se agrega el proveedor

    }

}
