package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
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
	public void testGetPedidosEnCurso() throws YaHayUnPedidoEnCursoException {
		assertNull(restaurante.getPedidoEnCurso());
		String nombreCliente = "Laura";
        String direccionCliente = "Cra47a";
        restaurante.iniciarPedido(nombreCliente, direccionCliente);
        
        Pedido pedidoEnCurso = restaurante.getPedidoEnCurso();
        assertNotNull(pedidoEnCurso, "Debería haber un pedido en curso.");
        assertEquals(nombreCliente, pedidoEnCurso.getNombreCliente(), "El nombre del cliente no es");
	}
	
	@Test
	public void testGetPedidos() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException { 
		restaurante.iniciarPedido("Laura", "Cra 47a");
	    restaurante.cerrarYGuardarPedido();
	    
	    restaurante.iniciarPedido("Sebastian", "Diag 89b");
	    restaurante.cerrarYGuardarPedido();
	    
	    ArrayList<Pedido> pedidos = restaurante.getPedidos();
	    assertEquals(2, pedidos.size());
	    assertEquals("Laura", pedidos.get(0).getNombreCliente());
	    assertEquals("Sebastian", pedidos.get(1).getNombreCliente());
	}
	
	@Test
	public void testGetMenuBase() {
		assertTrue(restaurante.getMenuBase().isEmpty());
        ProductoMenu producto = new ProductoMenu("Corral", 14000);
        restaurante.getMenuBase().add(producto);
        assertEquals(1, restaurante.getMenuBase().size());
        assertEquals("Corral", restaurante.getMenuBase().get(0).getNombre());
  
	}
	
	@Test
	public void testGetMenuCombos() {
		assertTrue(restaurante.getMenuCombos().isEmpty());
        Combo combo = new Combo("Combo Corral", 0.10, new ArrayList<>());
        restaurante.getMenuCombos().add(combo);
        assertEquals(1, restaurante.getMenuCombos().size());
        assertEquals("Combo Corral", restaurante.getMenuCombos().get(0).getNombre());
   
	}
	
	@Test
	public void testGetIngredientes() {
		assertTrue(restaurante.getIngredientes().isEmpty());
        Ingrediente ingrediente = new Ingrediente("Tomate", 1000);
        restaurante.getIngredientes().add(ingrediente);
        assertEquals(1, restaurante.getIngredientes().size());
        assertEquals("Tomate", restaurante.getIngredientes().get(0).getNombre());
	}
	
	@Test
	public void testCargarInformacionRestaurante() throws NumberFormatException, HamburguesaException, IOException {
		restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
		assertFalse(restaurante.getIngredientes().isEmpty());
	    assertFalse(restaurante.getMenuBase().isEmpty());
	    assertFalse(restaurante.getMenuCombos().isEmpty());	
	}

	@Test
	public void testCargarIngredientes() throws IngredienteRepetidoException, IOException {
		restaurante.cargarIngredientes(ingredientes);
		assertFalse(restaurante.getIngredientes().isEmpty());
	}

	@Test
	public void testCargarMenu() throws ProductoRepetidoException, IOException {
		restaurante.cargarMenu(menu);
		assertFalse(restaurante.getMenuBase().isEmpty());
	}
	@Test
	public void testCargarCombos() throws ProductoRepetidoException, ProductoFaltanteException, IOException {
		restaurante.cargarCombos(combos);
		assertFalse(restaurante.getMenuCombos().isEmpty());
	}

}
