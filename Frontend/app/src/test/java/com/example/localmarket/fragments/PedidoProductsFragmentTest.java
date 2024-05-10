package com.example.localmarket.fragments;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Order;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PedidoProductsFragmentTest {

    @Mock
    private FragmentActivity mockActivity;
    @Mock
    private LayoutInflater mockInflater;
    @Mock
    private ViewGroup mockContainer;
    @Mock
    private View mockView;
    @Mock
    private RecyclerView mockRecyclerView;
    @Mock
    private AuthService mockAuthService;
    @Mock
    private TokenManager mockTokenManager;
    @Mock
    private FragmentManager mockFragmentManager;

    private PedidoProductsFragment fragment;
    private Bundle mockArgs;
    @Mock
    Bundle args;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new PedidoProductsFragment();
        when(mockActivity.getSupportFragmentManager()).thenReturn(mockFragmentManager);
        when(mockInflater.inflate(R.layout.fragment_pedido_products, mockContainer, false)).thenReturn(mockView);
        when(mockView.findViewById(R.id.recycler_view_products)).thenReturn(mockRecyclerView);

        mockArgs = new Bundle();
        fragment.setArguments(mockArgs);
        fragment.authService = mockAuthService; // Inyectar el mock de AuthService.
    }

    @Test
    public void testOnCreateView() {
        // Simular la obtención de un token
        when(mockTokenManager.getToken()).thenReturn("fake_token");
        fragment.onCreate(mockArgs);

        // Ejecutar onCreateView
        View result = fragment.onCreateView(mockInflater, mockContainer, mockArgs);

        // Verificaciones
        verify(mockRecyclerView, times(1)).setAdapter(any());
        verify(mockRecyclerView, times(1)).setHasFixedSize(true);
        verify(mockRecyclerView, times(1)).setLayoutManager(any());
        assertSame(result, mockView);
    }

    @Test
    public void testChangeOrderStatus_NullOrder() {
        mockArgs.putSerializable("order", null);
        fragment.changeOrderStatus(mockArgs);
        verify(mockAuthService, never()).updateOrderStatus(anyInt(), anyString(), anyMap(), any());
    }

    @Test
    public void testChangeOrderStatus_ValidOrder() {
        Order mockOrder = mock(Order.class);
        mockArgs.putSerializable("order", mockOrder);
        when(mockOrder.getId()).thenReturn(1);
        fragment.changeOrderStatus(mockArgs);
        verify(mockAuthService, times(1)).updateOrderStatus(eq(1), eq("fake_token"), anyMap(), any());
    }



    @Test
    public void onCreateTODO() {
        PedidoProductsFragment p = new PedidoProductsFragment();
        Bundle savedInstanceState = null;
        p.onCreate(savedInstanceState);
    }

    @Test
    public void PedidoProductsFragment() {
        PedidoProductsFragment expected = new PedidoProductsFragment();
        PedidoProductsFragment actual = new PedidoProductsFragment();

        assertEquals(expected, actual);
    }

    @Test
    public void changeOrderStatus() {
        PedidoProductsFragment p = new PedidoProductsFragment();
        Bundle args = null;
        p.changeOrderStatus(args);
    }

    @Test
    public void newInstance() {
        Bundle args = null;
        PedidoProductsFragment expected = new PedidoProductsFragment();
        PedidoProductsFragment actual = PedidoProductsFragment.newInstance(args);

        assertEquals(expected, actual);
    }

    @Test
    public void onCreate() {
        PedidoProductsFragment p = new PedidoProductsFragment();
        Bundle savedInstanceState = null;
        p.onCreate(savedInstanceState);
        
    }

    @Test
    public void onCreateView() {
        PedidoProductsFragment p = new PedidoProductsFragment();
        LayoutInflater inflater = null;
        ViewGroup container = null;
        Bundle savedInstanceState = null;
        View expected = null;
        View actual = p.onCreateView(inflater, container, savedInstanceState);

        assertEquals(expected, actual);
    }

    @Test
    public void changeOrderStatusTODO() {
        PedidoProductsFragment p = new PedidoProductsFragment();
        args= new Bundle();
        p.changeOrderStatus(args);
    }
}
