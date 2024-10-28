package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	
    @Test
    public void testCombo() {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("Corral", 14000));
        items.add(new ProductoMenu("Papas Medianas", 5500));
        items.add(new ProductoMenu("Gaseosa", 5000));

        Combo combo = new Combo("Combo Corral", 0.9, items);

        assertEquals("Combo Corral", combo.getNombre());
        int precioEsperado = (int)((14000 + 5500 + 5000) * 0.9);
        assertEquals(precioEsperado, combo.getPrecio());
    }

    @Test
    public void testGenerarTextoFactura() {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("Corral", 14000));
        items.add(new ProductoMenu("Papas Medianas", 5500));
        items.add(new ProductoMenu("Gaseosa", 5000));

        Combo combo = new Combo("Combo Corral Queso", 0.9, items);

        String expectedFactura = "Combo Combo Corral Queso\n"+"             " + combo.getPrecio() + " Descuento: 0.9\n"+ "\n";
        assertEquals(expectedFactura, combo.generarTextoFactura());
    }


    

}
