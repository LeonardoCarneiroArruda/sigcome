package br.com.sig.sigcome.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import br.com.sig.sigcome.model.Compra;

@Dao
public interface CompraDAO {

    @Insert
    Long salva(Compra compra);

    @Query("SELECT * FROM Compra ORDER BY dt_compra DESC")
    List<Compra> todos();
}
