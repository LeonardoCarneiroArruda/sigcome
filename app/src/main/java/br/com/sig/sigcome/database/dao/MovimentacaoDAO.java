package br.com.sig.sigcome.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import br.com.sig.sigcome.model.Movimentacao;

@Dao
public interface MovimentacaoDAO {

    @Insert
    void salva(Movimentacao movimentacao);

}
