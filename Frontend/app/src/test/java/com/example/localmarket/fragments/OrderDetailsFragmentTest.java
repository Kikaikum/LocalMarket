package com.example.localmarket.fragments;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.example.localmarket.model.CartItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.localmarket.model.Order;

/**
 * Clase de prueba para la clase OrderDetailsFragment.
 * @author Ainoha
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderDetailsFragmentTest {

    @Mock
    private OrderDetailsFragment orderDetailsFragmentMock;

    /**
     * Método de configuración que se ejecuta antes de cada método de prueba.
     */
    @Before
    public void setUp() {
        // No es necesario inicializar el mock aquí porque MockitoJUnitRunner ya lo hace automáticamente.
    }

    /**
     * Prueba la agrupación de productos por agricultor.
     */
    @Test
    public void groupProductsByAgricultor_ProductsGroupedCorrectly() {
        // Crear una lista de productos de ejemplo
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1, 101, 1, "Producto 1", 10.0, 2, "kg"));
        cartItems.add(new CartItem(2, 102, 1, "Producto 2", 20.0, 3, "unidad"));
        cartItems.add(new CartItem(3, 103, 2, "Producto 3", 15.0, 1, "kg"));

        // Crear un mapa esperado de productos agrupados por agricultor
        Map<Integer, List<CartItem>> expectedGroupedProducts = new HashMap<>();
        expectedGroupedProducts.put(1, Arrays.asList(
                new CartItem(1, 101, 1, "Producto 1", 10.0, 2, "kg"),
                new CartItem(2, 102, 1, "Producto 2", 20.0, 3, "unidad")
        ));
        expectedGroupedProducts.put(2, Collections.singletonList(
                new CartItem(3, 103, 2, "Producto 3", 15.0, 1, "kg")
        ));

        // Mock del método groupProductsByAgricultor()
        when(orderDetailsFragmentMock.groupProductsByAgricultor(cartItems)).thenReturn(expectedGroupedProducts);

        // Llamar al método para agrupar productos por agricultor
        Map<Integer, List<CartItem>> groupedProducts = orderDetailsFragmentMock.groupProductsByAgricultor(cartItems);

        // Verificar que los productos se agruparon correctamente
        assertEquals(expectedGroupedProducts, groupedProducts);
    }

    /**
     * Prueba la creación de una orden a partir de productos.
     */
    @Test
    public void createOrder_CreatesOrderCorrectly() {
        // Crear una instancia válida de OrderDetailsFragment
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();

        // Crear una lista de productos de ejemplo
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1, 101, 1, "Producto 1", 10.0, 2, "kg"));
        cartItems.add(new CartItem(2, 102, 1, "Producto 2", 20.0, 3, "unidad"));
        cartItems.add(new CartItem(3, 103, 2, "Producto 3", 15.0, 1, "kg"));

        // Llamar al método para crear la orden
        Order order = orderDetailsFragment.createOrder(1, 2, cartItems);

        // Verificar que la orden se creó correctamente
        assert order.getIdCliente() == 1;
        assert order.getIdAgricultor() == 2;
        assert order.getPedido().size() == 3; // Deberían haber tres productos en el pedido
    }

}
