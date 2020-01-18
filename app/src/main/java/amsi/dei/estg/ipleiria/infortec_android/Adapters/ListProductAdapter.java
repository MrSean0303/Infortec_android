package amsi.dei.estg.ipleiria.infortec_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.R;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Produto> produtos;
    private OnProdutoListener mOnProdutoListener;
    private Context context;

    private String urlImg = "http://188.81.8.49/Infortec/infortec_site/frontend/web/imagens/";

    public ListProductAdapter(Context context, ArrayList<Produto> produtos, OnProdutoListener onProdutoListener) {
        this.inflater = inflater.from(context);
        this.produtos = produtos;
        this.mOnProdutoListener = onProdutoListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view, mOnProdutoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        String precoFinal = String.format("%.2f", produto.getPreco()-produto.getValorDesconto());
        holder.preco.setText(precoFinal + "€");
        if(produto.getValorDesconto() != 0)
            holder.valorDesconto.setText("- "+produto.getValorDesconto()+"€");
        else
            holder.valorDesconto.setText("");
        holder.id = produto.getId();
        Glide.with(context)
                .load(urlImg + produto.getFotoProduto())
                .placeholder(R.drawable.imginfortec)
                .thumbnail(0f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgProd);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nome, preco, valorDesconto;
        private ImageView imgProd;
        private int id;
        OnProdutoListener onProdutoListener;

        private ViewHolder(@NonNull View itemView, OnProdutoListener onProdutoListener) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNome);
            preco = itemView.findViewById(R.id.textPreco);
            imgProd = itemView.findViewById(R.id.fotoProduto);
            valorDesconto = itemView.findViewById(R.id.valorDesconto);
            this.onProdutoListener = onProdutoListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProdutoListener.onProdutoClick(id);
        }
    }

    public interface OnProdutoListener{
        void onProdutoClick(int id);
    }
}
