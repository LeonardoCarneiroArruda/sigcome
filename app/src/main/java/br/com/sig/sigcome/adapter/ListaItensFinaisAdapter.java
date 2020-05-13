package br.com.sig.sigcome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.helper.Compra_ItemHelper;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Produto;

public class ListaItensFinaisAdapter extends RecyclerView.Adapter<ListaItensFinaisAdapter.ViewHolder> {

    private final Context context;
    private final List<Produto> produtos = new ArrayList<>();
    private final ClickListenersButtons OnClickListenersButtons;
    private final OnFocusChange OnFocusChangeListener;
    private List<Compra_Item> listaDeComprasVisualizacao = new ArrayList<>();
    private boolean ehVisualizacao = false;

    public ListaItensFinaisAdapter(Context context,
                                   ClickListenersButtons onClickListenersButtons,
                                   OnFocusChange onFocusChangeListener) {
        this.context = context;
        this.OnClickListenersButtons = onClickListenersButtons;
        OnFocusChangeListener = onFocusChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater
                .from(context)
                .inflate(R.layout.item_inserir_item_quantidade_compra, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.vincula(produto);

        if (ehVisualizacao) {
            Compra_Item item = Compra_ItemHelper
                    .retornaItemNaListaPorCodProduto(produto.getCd_produto(),
                            listaDeComprasVisualizacao);
            holder.vinculaQuantidadeEPreco(item);
        }

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

    public void setListaDeComprasVisualizacao(List<Compra_Item> lista) {
        this.listaDeComprasVisualizacao = lista;
        ehVisualizacao = true;
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

    public interface ClickListenersButtons {
        void ClickMenos(EditText et_quantidade_produto, int position);
        void ClickMais(EditText et_quantidade_produto, int position);
    }

    public interface OnFocusChange {
        void QuandoSairDoFocoCampoPreco(boolean hasFocus,
                                        EditText et_finalizando_compra_preco_produto,
                                        int position);

        void QuandoSairDoFocoCampoQuantidade(boolean hasFocus,
                                        EditText et_quantidade_produto,
                                        int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_inserir_item_quantidade_descricao_produto;
        private final TextView tv_inserir_item_quantidade_unidade_produto;
        private final EditText et_quantidade_produto;
        private final EditText et_finalizando_compra_preco_produto;
        private final ImageButton bt_quantidade_menos;
        private final ImageButton bt_quantidade_mais;
        private Produto produto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_inserir_item_quantidade_descricao_produto = itemView
                    .findViewById(R.id.tv_inserir_item_quantidade_descricao_produto);
            tv_inserir_item_quantidade_unidade_produto = itemView
                    .findViewById(R.id.tv_inserir_item_quantidade_unidade_produto);
            et_quantidade_produto = (itemView.findViewById(R.id.et_quantidade_produto));
            bt_quantidade_menos = itemView.findViewById(R.id.bt_quantidade_menos);
            bt_quantidade_mais = itemView.findViewById(R.id.bt_quantidade_mais);
            et_finalizando_compra_preco_produto = ((TextInputLayout)itemView
            .findViewById(R.id.et_finalizando_compra_preco_produto)).getEditText();

            et_quantidade_produto.setText("0");

            ConfiguraClickMenos();
            ConfiguraClickMais();
            ConfiguraFocusCampoPreco();
            ConfiguraFocusCampoQuantidade();
        }

        void ConfiguraFocusCampoQuantidade() {
            et_quantidade_produto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    OnFocusChangeListener.QuandoSairDoFocoCampoQuantidade(hasFocus,
                                            et_quantidade_produto,
                                            getAdapterPosition());
                }
            });
        }

        void ConfiguraFocusCampoPreco() {
            et_finalizando_compra_preco_produto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    OnFocusChangeListener.QuandoSairDoFocoCampoPreco(hasFocus,
                                            et_finalizando_compra_preco_produto,
                                            getAdapterPosition());
                }
            });
        }

        void vincula(Produto produto) {
            this.produto = produto;
            tv_inserir_item_quantidade_descricao_produto.setText(produto.getCh_descricao());
            tv_inserir_item_quantidade_unidade_produto.setText(produto.getCh_tipo_unidade());
        }

        void ConfiguraClickMenos() {
            bt_quantidade_menos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickListenersButtons.ClickMenos(et_quantidade_produto, getAdapterPosition());
                }
            });
        }

        void ConfiguraClickMais() {
            bt_quantidade_mais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickListenersButtons.ClickMais(et_quantidade_produto, getAdapterPosition());
                }
            });
        }


        void vinculaQuantidadeEPreco(Compra_Item item) {
            et_quantidade_produto.setText(item.getVr_quantidade() != null ?
                    item.getVr_quantidade().toString() : "0");
            et_finalizando_compra_preco_produto.setText( item.getVr_compra() != null ?
                    item.getVr_compra().toString() : "0");

            bt_quantidade_mais.setEnabled(false);
            bt_quantidade_menos.setEnabled(false);
            et_finalizando_compra_preco_produto.setEnabled(false);
            et_quantidade_produto.setEnabled(false);
        }
    }
}
