package br.com.pr.senai.restaurantecomabem.model;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CardapioDao {
    @Update
    void atualizar(Cardapio obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserir(Cardapio obj);

    @Query("select * from cardapio where id = :id")
    Cardapio localizar(long id);

    @Query("delete from cardapio where del = '1'")
    void removerMarcados();

    @Query("select count(*) from cardapio where del ='1'")
    int existeCardapiosADeletar();

    @Query("update cardapio set del ='0' where del = '1'")
    void limparMarcados();

    @Query("select * from cardapio order by produto")
    DataSource.Factory<Integer, Cardapio> getCardapiosPorNome();
}
