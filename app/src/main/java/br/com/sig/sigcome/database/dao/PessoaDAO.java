package br.com.sig.sigcome.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import br.com.sig.sigcome.model.Pessoa;

@Dao
public interface PessoaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salva(Pessoa pessoa);

    @Query("SELECT * FROM pessoa LIMIT 1")
    Pessoa retornaPessoaCadastrada();
}
