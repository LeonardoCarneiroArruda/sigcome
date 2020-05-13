package br.com.sig.sigcome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.model.Produto;

public class ListaItensAdapter extends RecyclerView.Adapter<ListaItensAdapter.ViewHolder>{

    private final Context context;
    private final List<Produto> produtos = new ArrayList<>();
    private final OnCheckedChangeListener onChangedChecked;

    public ListaItensAdapter(Context context,
                             OnCheckedChangeListener onChangedChecked) {
        this.context = context;
        this.onChangedChecked = onChangedChecked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater
                .from(context)
                .inflate(R.layout.item_inserir_item, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.vincula(produto);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void atualiza(List<Produto> categorias) {
        notifyItemRangeRemoved(0, this.produtos.size());
        this.produtos.clear();
        this.produtos.addAll(categorias);
        this.notifyItemRangeInserted(0, this.produtos.size());
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void adiciona(Produto... categorias) {
        int tamanhoAtual = this.produtos.size();
        Collections.addAll(this.produtos, categorias);
        int tamanhoNovo = this.produtos.size();
        notifyItemRangeInserted(tamanhoAtual, tamanhoNovo);
    }

    public void edita(int posicao, Produto categoria) {
        produtos.set(posicao, categoria);
        notifyItemChanged(posicao);
    }

    public void remove(int posicao) {
        produtos.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(Produto produto, boolean isChecked, View itemView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_inserir_item_descricao;
        private final CheckBox cb_inserir_item;
        private Produto produto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_inserir_item_descricao = itemView.findViewById(R.id.tv_inserir_item_descricao);
            cb_inserir_item = itemView.findViewById(R.id.cb_inserir_item);
            configuraChecked(itemView);
        }

        void configuraChecked(final View itemView) {
            cb_inserir_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    onChangedChecked.onCheckedChanged(produto, isChecked, itemView);
                }
            });
        }

        void vincula(Produto produto) {
            this.produto = produto;
            tv_inserir_item_descricao.setText(produto.getCh_descricao());
        }
    }
}
