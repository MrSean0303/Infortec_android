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

    public ListProductAdapter(Context context, ArrayList<Produto> produtos) {
        this.inflater = inflater.from(context);
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.valueOf(produto.getPreco()));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nome, preco;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNome);
            preco = itemView.findViewById(R.id.textPreco);
        }
    }
}
