package br.com.sig.sigcome.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.com.sig.sigcome.model.Categoria;

@Dao
public interface CategoriaDAO {

    @Insert
    void salva(Categoria categoria);

    @Update
    void edita(Categoria categoria);

    @Query("SELECT * FROM categoria")
    List<Categoria> todos();

    @Query("SELECT COUNT(*) FROM produto WHERE cd_categoria = :cd_categoria")
    Integer calcularTotalProdutosPorCategoria(Integer cd_categoria);
}
