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

        Exception exception = assertThrows(YaHayUnPedidoEnCursoException.class, () -> {restaurante.iniciarPedido("Sebas","Diag 89b");});
        assertTrue(exception.getMessage().contains("Ya existe un pedido en curso, para el cliente"));	
	}
	
	@Test
	public void testCerrarYGuardarPedido() throws NoHayPedidoEnCursoException, IOException, YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Laura", "Cra 47a");
        Pedido pedido = restaurante.getPedidoEnCurso();
        ProductoMenu producto = new ProductoMenu("Corral", 14000);
        pedido.agregarProducto(producto);

        assertDoesNotThrow(() -> restaurante.cerrarYGuardarPedido());
        assertNull(restaurante.getPedidoEnCurso());
        Exception exception = assertThrows(NoHayPedidoEnCursoException.class, () -> {restaurante.cerrarYGuardarPedido();});
        assertTrue(exception.getMessage().contains("Actualmente no hay un pedido en curso"));
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
	public void testGetPedidos()  { 
		ArrayList<Pedido> pedidos = restaurante.getPedidos();
		assertNotNull(pedidos);
	    assertEquals(0, pedidos.size());
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
	
	/*@Test
	public void testCargarCombos() throws ProductoRepetidoException, ProductoFaltanteException, IOException {
		
		restaurante.cargarCombos(combos);
		assertFalse(restaurante.getMenuCombos().isEmpty());
	}*/
	
	@Test
	public void testIngredienteRepetidoException() {
		File repetidos = new File("./data/ingredientes2.txt"); 
        File combos = new File("./data/combos.txt");
        File menu = new File("./data/menu.txt");
        Exception exception = assertThrows(IngredienteRepetidoException.class, () -> {restaurante.cargarInformacionRestaurante(repetidos,menu,combos);});
        assertTrue(exception.getMessage().contains(" está repetido"));
	}
	@Test
	public void testProductoRepetidoException() {
		File ingredientes = new File("./data/ingredientes.txt"); 
        File combos = new File("./data/combos.txt");
        File repetidos = new File("./data/menu2.txt");
        Exception exception = assertThrows(ProductoRepetidoException.class, () -> {restaurante.cargarInformacionRestaurante(ingredientes,repetidos,combos);});
        assertTrue(exception.getMessage().contains(" está repetido"));
	}
	@Test
	public void testProductoFaltanteException() {
		File ingredientes = new File("./data/ingredientes.txt"); 
        File combosFaltantes = new File("./data/combos2.txt");
        File menu = new File("./data/menu.txt");
        Exception exception = assertThrows(ProductoFaltanteException.class, () -> {restaurante.cargarInformacionRestaurante(ingredientes, menu, combosFaltantes);});
        assertTrue(exception.getMessage().contains("no aparece en la información del restaurante"));
	}
}
