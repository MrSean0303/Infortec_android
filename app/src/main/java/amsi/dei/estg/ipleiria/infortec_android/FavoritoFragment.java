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

import java.util.ArrayList;
import java.util.Objects;

import amsi.dei.estg.ipleiria.infortec_android.Adapters.ListProductAdapter;
import amsi.dei.estg.ipleiria.infortec_android.listeners.ApiCallBack;
import amsi.dei.estg.ipleiria.infortec_android.models.Favorito;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.FavoritosJsonParser;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ApiCallBack, ListProductAdapter.OnProdutoListener {
    private ArrayList<Produto> produtos, produtosFav;
    private ArrayList<Favorito> favoritos;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FavoritoFragment() {
        produtos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_favorito, container, false);
        swipeRefreshLayout = viewRoot.findViewById(R.id.swipe);

        produtosFav = produtosFavoritos();

        recyclerView = viewRoot.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ListProductAdapter(getContext(), produtosFav, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                SingletonGestorTabelas.getInstance(getContext()).getAllProdutosAPI(getContext(), ProdutoJsonParser.isConnectionInternet(Objects.requireNonNull(getContext())));
                favoritos = SingletonGestorTabelas.getInstance(getContext()).getFavoritosDB();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        SingletonGestorTabelas.getInstance(getContext()).setListener(this);

        if (produtosFav.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setMessage("Sem Favoritos Adicionados.")
                    .setTitle("Atenção");

            builder.show();
        }

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
    }

    @Override
    public void onRefresh() {
        produtosFav = produtosFavoritos();
        mAdapter = new ListProductAdapter(getContext(), produtosFav, this);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    private ArrayList<Produto> produtosFavoritos() {
        produtosFav = new ArrayList<>();
        SingletonGestorTabelas.getInstance(getContext()).getAllProdutosAPI(getContext(), ProdutoJsonParser.isConnectionInternet(Objects.requireNonNull(getContext())));
        produtos = SingletonGestorTabelas.getInstance(getContext()).getProdutosBD();

        SingletonGestorTabelas.getInstance(getContext()).getAllFavoritosAPI(getContext(), FavoritosJsonParser.isConnectionInternet(getContext()));
        favoritos = SingletonGestorTabelas.getInstance(getContext()).getFavoritosDB();

        for (Produto produto : produtos) {
            for (Favorito favorito : favoritos) {
                if (favorito.getProduto_id() == produto.getId())
                    produtosFav.add(produto);
            }
        }

        return produtosFav;
    }
}
