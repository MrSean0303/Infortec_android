package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;

public class ProdutoActivity extends AppCompatActivity {
    private int id;
    private Produto produto;
    private SingletonGestorTabelas singletonGestorTabelas;

    public ProdutoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);



        //produtos = SingletonGestorTabelas.getInstance(getContext()).getProdutosBD();


        id = getIntent().getIntExtra("ID_PRODUTO", 0);
        produto = SingletonGestorTabelas.getInstance(getApplicationContext()).getProdutoById(id);


    }
}
