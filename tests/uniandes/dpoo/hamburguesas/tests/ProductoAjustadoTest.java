package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {
	private ProductoMenu productoBase;
	private ProductoAjustado producto;
	
	ArrayList<Ingrediente> agregados = new ArrayList<>();
	ArrayList<Ingrediente> eliminados = new ArrayList<>();

	@Test
    public void testProductoAjustado() {
		productoBase = new ProductoMenu("Corral", 14000);
		producto = new ProductoAjustado(productoBase);
		Ingrediente huevo = new Ingrediente("huevo", 2500);
		Ingrediente lechuga = new Ingrediente("lechuga", 1000);
		Ingrediente tomate = new Ingrediente("tomate", 1000);
		agregados.add(huevo);
		eliminados.add(tomate);
		eliminados.add(lechuga);
		assertEquals("Corral", productoBase.getNombre(), "El nombre del producto no es");
	    assertEquals(14000, productoBase.getPrecio(), "El precio del producto no es");
	    assertEquals(huevo,agregados.get(0), "El ingrediente no esta");
	    List<Ingrediente> expectedE = new ArrayList<>();
        expectedE.add(tomate);
        expectedE.add(lechuga);
        assertEquals(expectedE, eliminados);
	}
	
	@Test
	public void testGetNombre() {
		productoBase = new ProductoMenu("Corral", 14000);
		producto = new ProductoAjustado(productoBase);
		assertEquals("Corral", producto.getNombre(), "El nombre del producto no es");
	}
	
	@Test 
	public void testGetPrecio() {
		productoBase = new ProductoMenu("Corral", 14000);
		Ingrediente huevo = new Ingrediente("huevo", 2500);
		ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
		productoAjustado.getAgregados().add(huevo);
		int precioEsperado= 2500+14000;
		
		assertEquals(precioEsperado, productoAjustado.getPrecio());
	}
	
	@Test
	public void testGenerarTextoFactura() {
		productoBase = new ProductoMenu("Corral", 14000);
		producto = new ProductoAjustado(productoBase);
		Ingrediente huevo = new Ingrediente("huevo", 2500);
		Ingrediente lechuga = new Ingrediente("lechuga", 1000);
		Ingrediente tomate = new Ingrediente("tomate", 1000);
		agregados.add(huevo);
		eliminados.add(tomate);
		eliminados.add(lechuga);
		producto.getAgregados().add(huevo);
		producto.getEliminados().add(tomate);
		producto.getEliminados().add(lechuga);
		
	    String expectedFactura = "Corral"+"\n"
	    						+ "    +" + huevo.getNombre( ) + "                " + huevo.getCostoAdicional( )
	    						+"    -"+ tomate.getNombre( )
	    						+"    -" + lechuga.getNombre( )
	    						+"            " + producto.getPrecio( ) 
	    						+"\n";
	    assertEquals(expectedFactura, producto.generarTextoFactura());		
	}
}
