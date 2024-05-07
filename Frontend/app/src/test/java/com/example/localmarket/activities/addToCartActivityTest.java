package com.example.localmarket.activities;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;
import android.widget.TextView;

import org.junit.Test;

/**
 * Clase de prueba para el OnClickListener del botón de añadir al carrito.
 * @author  Ainoha
 */
public class addToCartActivityTest {

    /**
     * Prueba unitaria para verificar si al hacer clic en el botón se incrementa la cantidad correctamente.
     */
    @Test
    public void onClick_ShouldIncrementQuantity() {
        // Mock del TextView
        TextView textViewquantity = mock(TextView.class);

        // Simula el texto actual del TextView
        when(textViewquantity.getText()).thenReturn("0");

        // Crea el OnClickListener
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el valor actual del TextView
                String quantityText = textViewquantity.getText().toString();

                // Convertir el valor actual a un número entero
                int currentQuantity = Integer.parseInt(quantityText);

                // Sumar 1 al valor actual
                int newQuantity = currentQuantity + 1;

                // Establecer el nuevo valor en el TextView
                textViewquantity.setText(String.valueOf(newQuantity));
            }
        };

        // Simula el clic en el botón
        onClickListener.onClick(mock(View.class));

        // Verifica que el texto del TextView se haya actualizado correctamente
        verify(textViewquantity).setText("1");
    }
}
