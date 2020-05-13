package br.com.sig.sigcome.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import br.com.sig.sigcome.model.Compra_Item;

@Dao
public interface Compra_ItemDAO {

    @Insert
    void salva(List<Compra_Item> itens);

    @Query("SELECT * FROM Compra_Item WHERE cd_compra = :cd_compra")
    List<Compra_Item> retornaItensPorCompra(Integer cd_compra);
}
