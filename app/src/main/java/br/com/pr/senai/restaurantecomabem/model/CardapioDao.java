package br.com.pr.senai.restaurantecomabem.model;

import androidx.paging.PagingSource;
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

    @Query("delete from cardapio where id = :id")
    void remover(long id);

    @Query("select * from cardapio order by produto")
    PagingSource<Integer, Cardapio> getCardapiosPorNome();
}