package br.com.pr.senai.restaurantecomabem.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.pr.senai.restaurantecomabem.R;
import br.com.pr.senai.restaurantecomabem.databinding.FragmentFirstBinding;
import br.com.pr.senai.restaurantecomabem.model.CardapioViewModel;
import br.com.pr.senai.restaurantecomabem.view.adapter.CardapioAdapter;
import br.com.pr.senai.restaurantecomabem.view.adapter.CardapioViewHolder;
import br.com.pr.senai.restaurantecomabem.view.callback.ItemTouchCallback;
import io.reactivex.rxjava3.disposables.Disposable;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    private Disposable subscribe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(view1 ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment));

        var viewModel = new ViewModelProvider(this, new CardapioViewModel.Factory(requireContext()))
                .get(CardapioViewModel.class);

        var adapter = new CardapioAdapter(requireActivity(), cardapio -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", cardapio.getId());

            var navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        });

        var recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        var itemTouchHelper = new ItemTouchHelper(new ItemTouchCallback(requireContext()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    var cardapioViewHolder = (CardapioViewHolder) viewHolder;
                    var cardapio = cardapioViewHolder.getCardapio();
                    var position = cardapioViewHolder.getAbsoluteAdapterPosition();

                    new AlertDialog.Builder(requireActivity())
                            .setMessage("Confirma a exclusão deste Prato?")
                            .setPositiveButton("Sim", (d, b) ->
                                    cardapio.ifPresent(value -> viewModel.remover(value.getId())))
                            .setNegativeButton("Não", (d, b) ->
                                    recyclerView.getAdapter().notifyItemChanged(position))
                            .create()
                            .show();
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        subscribe = viewModel.getCardapios().subscribe(cardapioPagingData ->
                adapter.submitData(getLifecycle(), cardapioPagingData));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscribe != null) {
            subscribe.dispose();
        }
        binding = null;
    }
}