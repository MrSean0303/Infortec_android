package amsi.dei.estg.ipleiria.infortec_android;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.Adapters.ListProductAdapter;
import amsi.dei.estg.ipleiria.infortec_android.listeners.ApiCallBack;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ApiCallBack, ListProductAdapter.OnProdutoListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Produto> produtos;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SearchView searchView;


    public HomeFragment() {
        produtos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View viewRoot = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = viewRoot.findViewById(R.id.swipe);

        produtos = SingletonGestorTabelas.getInstance(getContext()).getProdutosBD();

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
                SingletonGestorTabelas.getInstance(getContext()).getAllProdutosAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /*MqttClient client = null;
        try {
            client = new MqttClient("tcp://188.81.5.22:1883", MqttClient.generateClientId());
            client.connect();
            MqttMessage message = new MqttMessage();
            message.setPayload("teste...".getBytes());
            client.publish("Teste", message);
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("--> erro mosquitto: " + e);
        }*/


        SingletonGestorTabelas.getInstance(getContext()).setListener(this);
        SingletonGestorTabelas.getInstance(getContext()).getAllProdutosAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()));
        System.out.println("--> epahyah: " + produtos);
        return viewRoot;
    }


    @Override
    public void onRefresh() {
        SingletonGestorTabelas.getInstance(getContext()).getAllProdutosAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshProdutos(ArrayList<Produto> produtos) {
        System.out.println("--> onRefreshListaLivros: " + produtos);

        mAdapter = (new ListProductAdapter(getContext(), produtos, this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onProdutoClick(int id) {
        Intent intent = new Intent(getContext(), ProdutoActivity.class);
        intent.putExtra("ID_PRODUTO", id);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa_produtos, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.prodPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Produto> listaProdutos = new ArrayList<>();

                for (Produto tempProduto : SingletonGestorTabelas.getInstance(getContext() ).getProdutosBD()){
                    if (tempProduto.getNome().toLowerCase().contains(s.toLowerCase())){
                        listaProdutos.add(tempProduto);
                    }
                }

                recyclerView.setAdapter(new ListProductAdapter(getContext(), listaProdutos, HomeFragment.this));

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
