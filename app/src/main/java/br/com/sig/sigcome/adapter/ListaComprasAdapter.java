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
import br.com.sig.sigcome.helper.CalendarHelper;
import br.com.sig.sigcome.model.Compra;

public class ListaComprasAdapter extends RecyclerView.Adapter<ListaComprasAdapter.ViewHolder>{
    private final Context context;
    private final List<Compra> compras = new ArrayList<>();
    private final OnItemClickListener OnItemClickListener;

    public ListaComprasAdapter(Context context,
                               ListaComprasAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        OnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater
                .from(context)
                .inflate(R.layout.item_compra, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compra compra = compras.get(position);
        holder.vincula(compra);
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    public void atualiza(List<Compra> compras) {
        notifyItemRangeRemoved(0, this.compras.size());
        this.compras.clear();
        this.compras.addAll(compras);
        this.notifyItemRangeInserted(0, this.compras.size());
    }

    public void adiciona(Compra... compras) {
        int tamanhoAtual = this.compras.size();
        Collections.addAll(this.compras, compras);
        int tamanhoNovo = this.compras.size();
        notifyItemRangeInserted(tamanhoAtual, tamanhoNovo);
    }

    public void edita(int posicao, Compra compra) {
        compras.set(posicao, compra);
        notifyItemChanged(posicao);
    }

    public void remove(int posicao) {
        compras.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, Compra compra);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_descricao_compra;
        private final TextView tv_quantidade_produtos_na_compra;
        private Compra compra;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_descricao_compra = itemView.findViewById(R.id.tv_descricao_compra);
            tv_quantidade_produtos_na_compra =
                    itemView.findViewById(R.id.tv_quantidade_produtos_na_compra);
            configuraClick(itemView);
        }

        void configuraClick(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnItemClickListener.onItemClick(getAdapterPosition(), compra);
                }
            });
        }

        void vincula(Compra compra) {
            this.compra = compra;
            tv_descricao_compra.setText("Compra do dia "
                    + CalendarHelper.FormataData(compra.getDt_compra()));
            tv_quantidade_produtos_na_compra.setText("em desenvolvimento");
        }


    }



}
