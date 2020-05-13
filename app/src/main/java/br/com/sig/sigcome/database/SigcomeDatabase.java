package br.com.sig.sigcome.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.com.sig.sigcome.database.converter.ConversorCalendar;
import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.database.dao.CompraDAO;
import br.com.sig.sigcome.database.dao.Compra_ItemDAO;
import br.com.sig.sigcome.database.dao.MovimentacaoDAO;
import br.com.sig.sigcome.database.dao.PessoaDAO;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Categoria;
import br.com.sig.sigcome.model.Compra;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Movimentacao;
import br.com.sig.sigcome.model.Pessoa;
import br.com.sig.sigcome.model.Produto;

@Database(entities = {Pessoa.class, Categoria.class, Compra.class, Compra_Item.class,
        Movimentacao.class, Produto.class}, version = 2, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class SigcomeDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "sigcome.db";

    public abstract PessoaDAO getRoomPessoaDAO();
    public abstract CategoriaDAO getRoomCategoriaDAO();
    public abstract CompraDAO getRoomCompraDAO();
    public abstract Compra_ItemDAO getRoomCompraItemDAO();
    public abstract MovimentacaoDAO getRoomMovimentacaoDAO();
    public abstract ProdutoDAO getRoomProdutoDAO();

    public static SigcomeDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, SigcomeDatabase.class, NOME_BANCO_DE_DADOS)
                .build();
    }
}
