package br.com.pr.senai.restaurantecomabem.model;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Flowable;

public class CardapioViewModel extends ViewModel {
    private final CardapioRepository repo;
    private final Flowable<PagingData<Cardapio>> cardapios;

    public static class Factory implements ViewModelProvider.Factory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            return (T) new CardapioViewModel(context);
        }
    }

    public CardapioViewModel(Context context) {
        repo = new CardapioRepository(context);
        cardapios = repo.getCardapios();
        var coroutine = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(cardapios, coroutine);
    }

    public Flowable<PagingData<Cardapio>> getCardapios() {
        return repo.getCardapios();
    }

    public void inserir(Cardapio cardapio) {
        repo.inserir(cardapio);
    }

    public void atualizar(Cardapio cardapio) {
        repo.atulizar(cardapio);
    }

    public Cardapio localizar(long id) throws DataBaseException {
        return repo.localizar(id);
    }

    public void remover(long id) {
        repo.remover(id);
    }
}
