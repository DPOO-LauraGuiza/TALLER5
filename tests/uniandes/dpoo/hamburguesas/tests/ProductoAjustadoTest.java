package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {
	private ProductoMenu productoBase;
	ArrayList<Ingrediente> agregados = new ArrayList<>();
	ArrayList<Ingrediente> eliminados = new ArrayList<>();

	@BeforeEach
	public void start() {
		productoBase = new ProductoMenu("Corral", 14000);
		agregados.add(new Ingrediente("huevo", 2500));
		eliminados.add(new Ingrediente("lechuga", 1000));
		eliminados.add(new Ingrediente("tomate", 1000));
	}

	@Test
    public void testProductoAjustado() {
		assertEquals("Corral", productoBase.getNombre(), "El nombre del producto no es");
	    assertEquals(14000, productoBase.getPrecio(), "El precio del producto no es");
	    Ingrediente agregadoEsperado = new Ingrediente("huevo", 2500);
	    assertEquals(agregadoEsperado, agregados.get(0), "El ingrediente no esta");
	    List<Ingrediente> expected = new ArrayList<>();
        expected.add(new Ingrediente("lechuga", 1000));
        expected.add(new Ingrediente("tomate", 1000));
        assertEquals(expected, eliminados);
	}
	
	@Test
	public void testGetNombre() {
		assertEquals("Corral", productoBase.getNombre(), "El nombre del producto no es");
	}
	
	@Test 
	public void testGetPrecio() {
		ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
		int precioEsperado= 2500+14000;
		assertEquals(precioEsperado, productoAjustado.getPrecio(),"El precio no es");
	}

}
