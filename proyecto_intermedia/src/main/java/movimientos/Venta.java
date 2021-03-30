package movimientos;

import complementos.Fecha;
import entidades.Cliente;
import entidades.Empleado;
import inventario_productos.Producto;

public class Venta extends Movimiento {

    private Cliente cliente;
    private Empleado empleado;

    public Venta() {

    }

    public Venta(Fecha fecha, Producto producto, int cantidad, double importeTotal, Empleado empleado, Cliente cliente) {
        super(fecha, producto, cantidad, importeTotal);
        setCliente(cliente); //se incluye el cliente y el empleado
        setEmpleado(empleado);
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String toString() { //en el toString se agrega el empleado y el cliennte
        return super.toString()+"\nEmpleado que atendió: "+empleado.getNombre()+"\nCliente: "+cliente.getNombre()+"\n";

    }

    @Override
    public double setImporteTotal() { //se calcula importe total considerando el precio de venta
        setImporteTotal(producto.getPrecioVenta() *cantidad);
        return importeTotal;
    }

}
