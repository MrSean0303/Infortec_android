package amsi.dei.estg.ipleiria.infortec_android.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.models.Favorito;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;

public interface ApiCallBack {
    void onRefreshProdutos(ArrayList<Produto> produtos);
}
