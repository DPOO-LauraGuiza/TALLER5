package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {

	private Pedido pedido;
	private String nombreCliente;
	private String direccion;
    
    @BeforeEach
    public void testPedido() {
    	nombreCliente = "Laura";
    	direccion = "Cra 47a";
    	pedido = new Pedido(nombreCliente, direccion);
    }

    @Test
    public void testGetIdPedido() {
        int id = pedido.getIdPedido();
        Pedido nuevoPedido = new Pedido("Sebastian", "Diag 20b");
        assertEquals(id + 1, nuevoPedido.getIdPedido());
    }

    @Test
    public void testGetNombreCliente() {
        assertEquals("Laura", pedido.getNombreCliente());
    }

    @Test
    public void testAgregarProductoYPrecioTotalPedido() {
        ProductoMenu producto1 = new ProductoMenu("Corral", 14000);
        ProductoMenu producto2 = new ProductoMenu("Papas Medianas", 5500);

        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        
        
        double precio = (((14000 + 5500) * 0.19) + (14000 + 5500));
        assertEquals(precio, pedido.getPrecioTotalPedido());
    }
    
    @Test
    public void testPrecioNetoPedido() {
        ProductoMenu producto1 = new ProductoMenu("Corral", 14000);
        ProductoMenu producto2 = new ProductoMenu("Papas Medianas", 5500);

        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        
        
        double precio = ((14000 + 5500));
        assertEquals(precio, pedido.getPrecioNetoPedido());
    }
    

    @Test
    public void testCalcularPrecioIVAPedido() {
        ProductoMenu producto1 = new ProductoMenu("Corral", 14000);
        ProductoMenu producto2 = new ProductoMenu("Papas Medianas", 55000);

        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);

        int ivaEsperado = (int) ((14000 + 5500) * 0.19);
        assertEquals(ivaEsperado, pedido.getPrecioIVAPedido());
    }

    @Test
    public void testGenerarTextoFactura() {
        ProductoMenu producto1 = new ProductoMenu("Corral", 14000);
        ProductoMenu producto2 = new ProductoMenu("Papas Medianas", 5500);

        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);

        String expectedFactura = "Cliente: Laura\n" +
                                 "Dirección: Cra 47a\n" +
                                 "----------------\n" +
                                 producto1.generarTextoFactura() +
                                 producto2.generarTextoFactura() +
                                 "----------------\n" +
                                 "Precio Neto:  19500\n" +
                                 "IVA:          " + (int)(19500 * 0.19) + "\n" +
                                 "Precio Total: " + (int)(19500 * 1.19) + "\n";

        assertEquals(expectedFactura, pedido.generarTextoFactura());
    }

    @Test
    public void testGuardarFactura() throws FileNotFoundException {
        ProductoMenu producto1 = new ProductoMenu("Corral", 14000);
        ProductoMenu producto2 = new ProductoMenu("Papas Medianas", 5500);

        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);

        File archivoFactura = new File("factura_test.txt");
        pedido.guardarFactura(archivoFactura);

        Scanner scanner = new Scanner(archivoFactura);
        StringBuilder contenidoArchivo = new StringBuilder();
        while (scanner.hasNextLine()) {
            contenidoArchivo.append(scanner.nextLine()).append("\n");
        }
        scanner.close();

        assertEquals(pedido.generarTextoFactura(), contenidoArchivo.toString());
        archivoFactura.delete(); 
    }

    @Test
    public void testGuardarFacturaFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> {
            File archivoInvalido = new File("/ruta_invalida/factura_test.txt");
            pedido.guardarFactura(archivoInvalido);
        });
    }

}
