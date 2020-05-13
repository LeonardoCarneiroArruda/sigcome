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
import br.com.sig.sigcome.helper.MoedaHelper;
import br.com.sig.sigcome.model.Produto;
import br.com.sig.sigcome.model.modelsLista.ProdutoModelLista;

public class ListaProdutoAdapter extends RecyclerView.Adapter<ListaProdutoAdapter.ViewHolder>{

    private final Context context;
    private final List<ProdutoModelLista> produtos = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public ListaProdutoAdapter(Context context,
                                 OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater
                .from(context)
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdutoModelLista produto = produtos.get(position);
        holder.vincula(produto);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void atualiza(List<ProdutoModelLista> produtos) {
        notifyItemRangeRemoved(0, this.produtos.size());
        this.produtos.clear();
        this.produtos.addAll(produtos);
        this.notifyItemRangeInserted(0, this.produtos.size());
    }

    public void adiciona(ProdutoModelLista... categorias) {
        int tamanhoAtual = this.produtos.size();
        Collections.addAll(this.produtos, categorias);
        int tamanhoNovo = this.produtos.size();
        notifyItemRangeInserted(tamanhoAtual, tamanhoNovo);
    }

    public void edita(int posicao, ProdutoModelLista produto) {
        produtos.set(posicao, produto);
        notifyItemChanged(posicao);
    }

    public void remove(int posicao) {
        produtos.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void atualizaEstoqueNaLista(int position, Double estoque) {
        ProdutoModelLista prod = produtos.get(position);
        prod.setVr_quantidade_estoque(estoque);
        edita(position, prod);
    }

    public List<ProdutoModelLista> getListaProdutos() {
        return produtos;
    }


    public interface OnItemClickListener {
        void onItemClick(int posicao, ProdutoModelLista produto);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_descricao_produto;
        private final TextView tv_preco_produto;
        private final TextView tv_quantidade_produto;
        private ProdutoModelLista produto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_descricao_produto = itemView.findViewById(R.id.tv_descricao_produto);
            tv_preco_produto = itemView.findViewById(R.id.tv_preco_produto);
            tv_quantidade_produto = itemView.findViewById(R.id.et_quantidade_produto);
            configuraItemClique(itemView);
        }

        void vincula(ProdutoModelLista produto) {
            this.produto = produto;
            tv_descricao_produto.setText(produto.getProduto().getCh_descricao());
            tv_preco_produto.setText(MoedaHelper
                    .DoubleParaStringMoeda(produto.getProduto().getVr_ultima_compra()));
            tv_quantidade_produto.setText(produto.getVr_quantidade_estoque() != null ?
            produto.getVr_quantidade_estoque().toString() : "calculando");
        }

        private void configuraItemClique(@NonNull View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener
                            .onItemClick(getAdapterPosition(), produto);
                }
            });
        }

    }
}
