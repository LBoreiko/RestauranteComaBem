package br.com.pr.senai.restaurantecomabem.model;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.ExecutionException;


public class CardapioRepository {
    private CardapioDao dao;
    private LiveData<PagedList<Cardapio>> cardapios;

    public CardapioRepository() {
        CardapioDatabase db = CardapioDatabase.getInstance();
        dao = db.cardapioDao();
        cardapios = new LivePagedListBuilder<>(dao.getCardapiosPorNome(), 5).build();
    }

    public LiveData<PagedList<Cardapio>> getCardapios() {
        return cardapios;
    }

    public void atulizar(Cardapio cardapio) {
        new Action<>(dao::atualizar).execute(cardapio);
    }

    public void inserir(Cardapio cardapio) {
        new Action<>(dao::inserir).execute(cardapio);
    }

    public Cardapio localizar(long id) throws DataBaseException {
        try {
            return new Query<>(dao::localizar).execute(id).get();
        } catch (ExecutionException | InterruptedException ex) {
            throw new DataBaseException(("Falha na execução da consulta ao ID: " + id));
        }
    }

    public void removerMarcados() {
        new Action<Void>(__ -> dao.removerMarcados()).execute();
    }

    public int existeCardapiosADeletar() throws DataBaseException {
        try {
            return new Query<Void, Integer>(__ -> dao.existeCardapiosADeletar()).execute().get();
        } catch (ExecutionException | InterruptedException ex) {
            throw new DataBaseException(("Falha em determinar se existem Cardápios à remover"));
        }
    }

    public void limparMarcados() {
        new Action<Void>(__ -> dao.limparMarcados()).execute();
    }
}
