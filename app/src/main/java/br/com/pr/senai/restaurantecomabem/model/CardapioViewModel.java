package br.com.pr.senai.restaurantecomabem.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class CardapioViewModel extends ViewModel {
    private CardapioRepository repo = new CardapioRepository();

    public LiveData<PagedList<Cardapio>> getCardapios() {
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

    public void removerMarcados() {
        repo.removerMarcados();
    }

    public boolean existemCardapiosADeletar() throws DataBaseException {
        return repo.existeCardapiosADeletar() > 0;
    }

    public void limparMarcados() {
        repo.limparMarcados();
    }
}
