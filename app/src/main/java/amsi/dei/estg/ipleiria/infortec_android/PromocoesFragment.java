package amsi.dei.estg.ipleiria.infortec_android;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.models.Produto;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromocoesFragment extends Fragment {
    private ArrayList<Produto> produtos;


    public PromocoesFragment() {
        produtos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promocoes, container, false);
    }

}
