package br.com.pr.senai.restaurantecomabem.model;

import android.content.Context;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Flowable;


public class CardapioRepository {
    private final CardapioDao dao;
    private Flowable<PagingData<Cardapio>> cardapios;

    public CardapioRepository(Context context) {
        CardapioDatabase db = CardapioDatabase.getInstance(context);
        dao = db.cardapioDao();
        cardapios = getCardapios();
    }

    public Flowable<PagingData<Cardapio>> getCardapios() {
        if(cardapios == null) {
            cardapios = PagingRx.getFlowable(
                    new Pager<>(
                            new PagingConfig(
                                    20,
                                    5,
                                    false,
                                    40,
                                    100),
                            dao::getCardapiosPorNome));
        }
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

    public void remover(long idCardapio) {
        new Action<>(dao::remover).execute(idCardapio);
    }
}
