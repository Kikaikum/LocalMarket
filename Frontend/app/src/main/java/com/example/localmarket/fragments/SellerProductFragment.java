package com.example.localmarket.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;
import com.example.localmarket.utils.ProductList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerProductFragment extends Fragment implements ProductAdapter.OnProductClickListener{

    private AuthService authService;
    private Button btnAdd;
    private List<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;

    public SellerProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerProfuctFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerProductFragment newInstance(String param1, String param2) {
        SellerProductFragment fragment = new SellerProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configuración del RecyclerView

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_product, container, false);
        productList = ProductList.getProducts();
        adapter = new ProductAdapter(productList, this);
        recyclerView = view.findViewById(R.id.reciclerViewProducts);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        authService = AuthService.getInstance();

        btnAdd = view.findViewById(R.id.addProduct); // Asegúrate de que este ID exista en tu layout activity_seller_lobby.xml
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container2, new AddProductFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onProductClick(Product product) {

    }
}