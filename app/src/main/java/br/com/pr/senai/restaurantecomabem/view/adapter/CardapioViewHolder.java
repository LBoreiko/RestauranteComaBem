package br.com.pr.senai.restaurantecomabem.view.adapter;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Optional;

import br.com.pr.senai.restaurantecomabem.databinding.ViewCardapioItemBinding;
import br.com.pr.senai.restaurantecomabem.model.Cardapio;
import br.com.pr.senai.restaurantecomabem.view.listener.OnAdapterClickListener;

public class CardapioViewHolder extends RecyclerView.ViewHolder {
    private final OnAdapterClickListener listener;
    private final ViewCardapioItemBinding binding;
    private final FragmentActivity activity;
    private Optional<Cardapio> cardapio;

    public CardapioViewHolder(ViewCardapioItemBinding binding, FragmentActivity activity, OnAdapterClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.activity = activity;
        this.listener = listener;
    }

    public void bindTo(Cardapio cardapio) {
        this.cardapio = Optional.of(cardapio);

        if (this.cardapio.isPresent()) {
            binding.tvPrato.setText(cardapio.getProduto());
            binding.tvDescricao.setText(cardapio.getDescricao());
            binding.tvChefe.setText(cardapio.getCozinheiro());
            binding.tvData.setText(cardapio.getDataDeLancamento());
            //carregaFoto(binding.ivPratos, cardapio.getProduto());

            binding.getRoot().setOnClickListener(__ -> listener.onClick(cardapio));
        }
    }

    public Optional<Cardapio> getCardapio() {
        return cardapio;
    }
}
