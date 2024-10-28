package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

	
	private ProductoMenu producto;

	@BeforeEach
	public void antesQue() {
		producto = new ProductoMenu("Corral", 14000);
	}

	@Test
	public void testProductoMenu() {
		assertEquals("Corral", producto.getNombre(), "El nombre no es.");
	    assertEquals(14000, producto.getPrecio(), "El precio del producto no es");
	}

	@Test
	void testGetNombre() {
		assertEquals("Corral", producto.getNombre(), "El nombre devuelto no es ");
	}

	@Test
	public void testGetPrecio() {
		assertEquals(14000, producto.getPrecio(), "El precio no es");
	}

	@Test
	void testGenerarTextoFactura() {
		String facturaEsperada = "Corral\n            14000\n";
		assertEquals(facturaEsperada, producto.generarTextoFactura(), "El texto de factura no es.");
	}
}
