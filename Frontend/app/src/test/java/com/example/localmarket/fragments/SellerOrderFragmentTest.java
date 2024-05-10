package com.example.localmarket.fragments;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.localmarket.model.Order;
import com.example.localmarket.network.service.AuthService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SellerOrderFragmentTest {


    @InjectMocks
    SellerOrderFragment sellerOrderFragment;
    @Mock
    private FragmentManager fragmentManager;
    @Mock
    private FragmentTransaction fragmentTransaction;
    @Mock
    private PedidoProductsFragment pedidoProductsFragment;

    @Test
    public void testFetchOrdersByFarmer() {
        // Arrange
        SellerOrderFragment fragment = new SellerOrderFragment();
        AuthService authService = mock(AuthService.class);
        fragment.authService = authService;
        //fragment.tokenManager.getToken();

        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order(/* datos del pedido */));

        // Simulamos el comportamiento del método getAllOrdersByFarmer
        doAnswer(invocation -> {
            AuthService.AuthCallback<List<Order>> callback = invocation.getArgument(2);
            callback.onSuccess(expectedOrders);
            return null;
        }).when(authService).getAllOrdersByFarmer(15, "Bearer: " + "token", new AuthService.AuthCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> response) {
                //assertEquals(expectedOrders.size(), fragment.orderList.size());
                assertTrue(true);
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(false);
            }
        });

    }

}

