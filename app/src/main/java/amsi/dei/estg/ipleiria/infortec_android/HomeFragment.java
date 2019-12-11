package amsi.dei.estg.ipleiria.infortec_android;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.Adapters.ListProductAdapter;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1, 2, 3, 1, 1, "TESTE", "NSEI", "É FIXE", "É FIXE, MESMO BACANO", 123.12, 0));
        produtos.add(new Produto(2, 2, 3, 1, 1, "Ree", "NSEI", "É FIXE", "É FIXE, MESMO BACANO", 123.12, 0));



        recyclerView = viewRoot.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ListProductAdapter(getContext(), produtos);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        return viewRoot;
    }

}
