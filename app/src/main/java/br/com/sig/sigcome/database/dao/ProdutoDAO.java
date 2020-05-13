package br.com.sig.sigcome.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import br.com.sig.sigcome.model.Produto;

@Dao
public interface ProdutoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salva(Produto produto);

    @Query("SELECT * FROM produto")
    List<Produto> todos();

    @Query("SELECT * FROM produto WHERE cd_produto = :cd_produto")
    Produto buscaProdutoPorId(int cd_produto);

    @Query("SELECT SUM(vr_quantidade) FROM movimentacao WHERE cd_produto = :cd_produto")
    Double calculaEstoqueProduto(Integer cd_produto);
}
