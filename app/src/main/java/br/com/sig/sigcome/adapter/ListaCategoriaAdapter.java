package br.com.sig.sigcome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.model.Categoria;

public class ListaCategoriaAdapter extends RecyclerView.Adapter<ListaCategoriaAdapter.ViewHolder> {

    private final Context context;
    private final List<Categoria> categorias = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public ListaCategoriaAdapter(Context context,
                                 OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater
                .from(context)
                .inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.vincula(categoria);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void atualiza(List<Categoria> categorias) {
        notifyItemRangeRemoved(0, this.categorias.size());
        this.categorias.clear();
        this.categorias.addAll(categorias);
        this.notifyItemRangeInserted(0, this.categorias.size());
    }

    public void adiciona(Categoria... categorias) {
        int tamanhoAtual = this.categorias.size();
        Collections.addAll(this.categorias, categorias);
        int tamanhoNovo = this.categorias.size();
        notifyItemRangeInserted(tamanhoAtual, tamanhoNovo);
    }

    public void edita(int posicao, Categoria categoria) {
        categorias.set(posicao, categoria);
        notifyItemChanged(posicao);
    }

    public void remove(int posicao) {
        categorias.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, Categoria categoria);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_descricao_categoria;
        private Categoria categoria;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_descricao_categoria = itemView.findViewById(R.id.tv_descricao_categoria);
            configuraItemClique(itemView);
        }

        void vincula(Categoria categoria) {
            this.categoria = categoria;
            tv_descricao_categoria.setText(categoria.getCh_descricao());
        }

        private void configuraItemClique(@NonNull View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener
                            .onItemClick(getAdapterPosition(), categoria);
                }
            });
        }

    }


}
