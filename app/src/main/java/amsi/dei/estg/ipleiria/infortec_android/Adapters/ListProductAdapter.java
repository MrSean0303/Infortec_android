package amsi.dei.estg.ipleiria.infortec_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.R;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Produto> produtos;
    private OnProdutoListener mOnProdutoListener;

    public ListProductAdapter(Context context, ArrayList<Produto> produtos, OnProdutoListener onProdutoListener) {
        this.inflater = inflater.from(context);
        this.produtos = produtos;
        this.mOnProdutoListener = onProdutoListener;
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
        holder.preco.setText(String.valueOf(produto.getPreco()));
        holder.id = produto.getId();
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nome, preco;
        private ImageView img;
        private int id;
        OnProdutoListener onProdutoListener;

        public ViewHolder(@NonNull View itemView, OnProdutoListener onProdutoListener) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNome);
            preco = itemView.findViewById(R.id.textPreco);
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
