package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {
	
	private Restaurante restaurante;
	File combos = new File("data/combos.txt");
	File ingredientes = new File("data/ingredientes.txt");
	File menu = new File("data/menu.txt");
	
	@BeforeEach
	public void construct() {
		restaurante = new Restaurante();
	}
	
	@Test
	public void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Laura", "Cra 47a");
        assertNotNull(restaurante.getPedidoEnCurso());
        assertEquals("Laura", restaurante.getPedidoEnCurso().getNombreCliente());

        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Sebas", "Diag 89b");
        });
		
	}
	
	@Test
	public void testCerrarYGuardarPedido() throws NoHayPedidoEnCursoException, IOException, YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Laura", "Cra 47a");
        Pedido pedido = restaurante.getPedidoEnCurso();
        ProductoMenu producto = new ProductoMenu("Corral", 14000);
        pedido.agregarProducto(producto);

        restaurante.cerrarYGuardarPedido();
        assertNull(restaurante.getPedidoEnCurso());
        
        File archivoFactura = new File("./facturas/factura_" + pedido.getIdPedido() + ".txt");
        assertTrue(archivoFactura.exists());
        archivoFactura.delete();
	}
	
	@Test
	public void x() {
		
	}
	
	@Test
	public void testCargarInformacionRestaurante() throws NumberFormatException, HamburguesaException, IOException {
		restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
	}

	@Test
	public void testCargarIngredientes() throws IngredienteRepetidoException, IOException {
		restaurante.cargarIngredientes(ingredientes);
	}

	@Test
	public void testCargarMenu() throws ProductoRepetidoException, IOException {
		restaurante.cargarMenu(menu);
	}
	@Test
	public void testCargarCombos() throws ProductoRepetidoException, ProductoFaltanteException, IOException {
		restaurante.cargarCombos(combos);
	}

}
