package com.example.localmarket.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.ProductRequest;
import com.example.localmarket.model.ProductResponse;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductSpinnerAdapter;
import com.example.localmarket.utils.TokenManager;
import com.google.android.material.textfield.TextInputEditText;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddProductFragmentTest {

    @Mock
    private FragmentActivity mockActivity;
    @Mock
    private AuthService mockAuthService;
    @Mock
    private TokenManager mockTokenManager;
    @Mock
    private LayoutInflater mockInflater;
    @Mock
    private ViewGroup mockContainer;
    @Mock
    private View mockView;
    @Mock
    private Spinner mockSpinner;
    @Mock
    private Switch mockSwitch;
    @Mock
    private EditText mockTextView;
    @Mock
    private TextInputEditText mockTextInputEditText;
    @Mock
    private Button mockButton;
    @Mock
    private Bundle mockBundle;
    @Mock
    private Context mockContext;
    @Mock
    private ProductSpinnerAdapter mockAdapter;
    @Mock
    private FragmentManager mockFragmentManager;
    @Mock
    private TextInputEditText itDescription; // Mock for TextInputEditText
    @Mock
    private TextInputEditText itPrice;
    @Mock
    private TextInputEditText itStock;
    @Mock
    private TextInputEditText etSwitch;

    private AddProductFragment addProductFragment;

    private Product mockProduct;
    private Editable mockEditableDescription;
    private Editable mockEditablePrice;
    private Editable mockEditableStock;
    private TokenManager tokenManager;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        addProductFragment = new AddProductFragment();

        // Initialize mockProduct with default behavior
        mockProduct = Mockito.mock(Product.class);
        when(mockProduct.getName()).thenReturn("Apple");
        //when(mockProduct.getCategoriaId()).thenReturn(1);

        // Initialize other mocks
        //Editable editableDescription = Mockito.mock(Editable.class);
        //Editable editableUnit = Mockito.mock(Editable.class);
        //Editable editablePrice = Mockito.mock(Editable.class);
        //Editable editableStock = Mockito.mock(Editable.class);

        //Mockito.when(editableDescription.toString()).thenReturn("Sample description");
        //Mockito.when(editableUnit.toString()).thenReturn("kg");
        //Mockito.when(editablePrice.toString()).thenReturn("20.0");
        //Mockito.when(editableStock.toString()).thenReturn("15.0");

        //Mockito.when(itDescription.getText()).thenReturn(editableDescription);
        //Mockito.when(etSwitch.getText()).thenReturn(editableUnit);
        //Mockito.when(itPrice.getText()).thenReturn(editablePrice);
        //Mockito.when(itStock.getText()).thenReturn(editableStock);


        mockContext = Mockito.mock(Context.class);
        Mockito.when(mockContext.getApplicationContext()).thenReturn(mockContext);
        tokenManager = TokenManager.getInstance(mockContext);


        // Configuración de la actividad y el contexto
        addProductFragment.setActivity(mockActivity);
        addProductFragment.setContext(mockContext);
        addProductFragment.setAuthService(mockAuthService);
        addProductFragment.setTokenManager(mockTokenManager);
        addProductFragment.setSpinnerImages(mockSpinner);
        addProductFragment.setDescriptionEditText(mockTextInputEditText);
        addProductFragment.setSwitchEditText(mockTextView);


        when(mockInflater.inflate(R.layout.fragment_add_product, mockContainer, false)).thenReturn(mockView);
        //when(mockActivity.getSupportFragmentManager()).thenReturn(mockFragmentManager);

        // Configuración de la inflación de la vista y las llamadas a findViewById
        when(mockView.findViewById(R.id.spinnerProduct)).thenReturn(mockSpinner);
        when(mockView.findViewById(R.id.switchPesoUnidad)).thenReturn(mockSwitch);
        when(mockView.findViewById(R.id.btnCancel)).thenReturn(mockButton);
        when(mockView.findViewById(R.id.btnAcept)).thenReturn(mockButton);
        when(mockView.findViewById(R.id.tvUnidadMedida)).thenReturn(mockTextView);
        when(mockView.findViewById(R.id.tvUnidadMedida2)).thenReturn(mockTextView);
        when(mockView.findViewById(R.id.tvPriceCurrency)).thenReturn(mockTextView);
        when(mockView.findViewById(R.id.itPrice)).thenReturn(mockTextInputEditText);
        when(mockView.findViewById(R.id.etDescription)).thenReturn(mockTextInputEditText);
        when(mockView.findViewById(R.id.itStock)).thenReturn(mockTextInputEditText);


        when(mockTextInputEditText.getText()).thenReturn(mockEditableDescription, mockEditablePrice, mockEditableStock);
        //when(mockActivity.getSupportFragmentManager()).thenReturn(mockFragmentManager);
        when(mockSpinner.getSelectedItem()).thenReturn(mockProduct);
        when(mockProduct.getName()).thenReturn("Apple");
        //when(mockProduct.getCategoriaId()).thenReturn(1);
        when(mockContext.getString(anyInt())).thenReturn("Sample string");
        when(mockActivity.getApplicationContext()).thenReturn(mockContext);
        //when(mockTokenManager.getInstance(any())).thenReturn(mockTokenManager);
        //when(mockTokenManager.getUserId()).thenReturn(123);
        //when(mockTokenManager.getToken()).thenReturn("token");
    }

    @Test
    public void testOnCreateView() {
        View view = addProductFragment.onCreateView(mockInflater, mockContainer, mockBundle);
        // Verifica la correcta inflación y configuración de los componentes de la vista
        verify(mockInflater).inflate(R.layout.fragment_add_product, mockContainer, false);
        verify(mockView).findViewById(R.id.spinnerProduct);
        verify(mockView).findViewById(R.id.switchPesoUnidad);
        verify(mockView).findViewById(R.id.btnCancel);
        verify(mockView).findViewById(R.id.btnAcept);

        assertNotNull(view);
    }

    @Test
    public void testButtonCancel() {
        // Configura la vista y simula un clic en el botón Cancelar
        addProductFragment.onCreateView(mockInflater, mockContainer, mockBundle);
        mockButton.performClick();
        //verify(mockFragmentManager).popBackStack();
    }

    @Test
    public void testButtonAccept() {
        addProductFragment.onCreateView(mockInflater, mockContainer, mockBundle);
        mockButton.performClick();

        // Add assertions or verify depending on the expected behavior of sendProduct method.
    }

    @Test
    public void testSwitchMeasurementChange() {
        addProductFragment.onCreateView(mockInflater, mockContainer, mockBundle);
        addProductFragment.switchMeasurement.setOnCheckedChangeListener(null);  // Use real switch change listener if implemented

        verify(mockTextView, atLeastOnce()).setText(anyString());
    }
    @Test
    public void testSendProduct_Success() {
        // Simula la acción de enviar un producto correctamente
        System.out.println("Antes de sendProduct: ");
        System.out.println("itDescription getText(): " + (itDescription.getText() != null ? itDescription.getText().toString() : "null"));


        addProductFragment.sendProduct();
        System.out.println("Después de sendProduct: ");
        System.out.println("itDescription getText(): " + (itDescription.getText() != null ? itDescription.getText().toString() : "null"));
    }

    @Test
    public void testSendProduct_Failure_InvalidInput() {
        // Configura el mock para simular una entrada inválida
        //when(mockTextView.getText().toString()).thenReturn("Unidad inválida");


        // Ejecuta el método sendProduct del fragmento
        addProductFragment.sendProduct();

        // Verifica que no se haya realizado ninguna solicitud de red debido a la entrada inválida
        verify(mockAuthService, never()).addProduct(anyInt(), any(ProductRequest.class), anyString(), any());

        // Verifica que no se haya llamado a popBackStack ya que solo debería ocurrir en la presentación exitosa o la cancelación
        verify(mockFragmentManager, never()).popBackStack();
    }
}
