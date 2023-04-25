package br.com.pr.senai.restaurantecomabem.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

import br.com.pr.senai.restaurantecomabem.databinding.ViewCardapioItemBinding;
import br.com.pr.senai.restaurantecomabem.model.Cardapio;
import br.com.pr.senai.restaurantecomabem.view.listener.OnAdapterClickListener;

public class CardapioAdapter extends PagingDataAdapter<Cardapio, CardapioViewHolder> {
    private final FragmentActivity activity;
    private final OnAdapterClickListener listener;

    public CardapioAdapter(FragmentActivity activity, OnAdapterClickListener listener) {
        super(new DiffUtil.ItemCallback<Cardapio>() {
            @Override
            public boolean areItemsTheSame(@NonNull Cardapio oldCardapio, @NonNull Cardapio newCardapio) {
                return Objects.equals(oldCardapio.getId(), newCardapio.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Cardapio oldCardapio, @NonNull Cardapio newCardapio) {
                return oldCardapio.equals(newCardapio);
            }
        });
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardapioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        var binding = ViewCardapioItemBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
        return new CardapioViewHolder(binding, activity, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final CardapioViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }}
