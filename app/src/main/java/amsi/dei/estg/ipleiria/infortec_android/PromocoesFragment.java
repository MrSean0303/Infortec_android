package amsi.dei.estg.ipleiria.infortec_android;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.Adapters.ListProductAdapter;
import amsi.dei.estg.ipleiria.infortec_android.listeners.ApiCallBack;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromocoesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ApiCallBack, ListProductAdapter.OnProdutoListener {
    private ArrayList<Produto> produtos;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SearchView searchView;


    public PromocoesFragment() {
        produtos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_promocoes, container, false);
        swipeRefreshLayout = viewRoot.findViewById(R.id.swipe);

        produtos = SingletonGestorTabelas.getInstance(getContext()).getProdutosPromocoesBD();

        System.out.println("---> REEEEEE: " + produtos);

        recyclerView = viewRoot.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ListProductAdapter(getContext(), produtos, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                SingletonGestorTabelas.getInstance(getContext()).getAllProdutosPromocoesAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if(produtos.size() == 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Sem Promoções disponíveis no momento, Desculpe o Incómodo.")
                    .setTitle("Atenção");

            AlertDialog dialog = builder.show();
        }

        SingletonGestorTabelas.getInstance(getContext()).setListener(this);
        SingletonGestorTabelas.getInstance(getContext()).getAllProdutosPromocoesAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()));

        return viewRoot;
    }

    @Override
    public void onProdutoClick(int id) {
        Intent intent = new Intent(getContext(), ProdutoActivity.class);
        intent.putExtra("ID_PRODUTO", id);
        startActivity(intent);
    }

    @Override
    public void onRefreshProdutos(ArrayList<Produto> produtos) {
        mAdapter = (new ListProductAdapter(getContext(), produtos, this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        SingletonGestorTabelas.getInstance(getContext()).getAllProdutosPromocoesAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()));
        swipeRefreshLayout.setRefreshing(false);
    }
}
