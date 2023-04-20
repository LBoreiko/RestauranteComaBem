package br.com.pr.senai.restaurantecomabem.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import br.com.pr.senai.restaurantecomabem.R;
import br.com.pr.senai.restaurantecomabem.databinding.FragmentSecondBinding;
import br.com.pr.senai.restaurantecomabem.model.Cardapio;
import br.com.pr.senai.restaurantecomabem.model.CardapioViewModel;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private CardapioViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(requireActivity()).get(CardapioViewModel.class);

        binding.btSalvar.setOnClickListener(__ -> {
            var cardapio = new Cardapio();
            cardapio.setProduto(binding.edProduto.getText().toString());
            cardapio.setDescricao(binding.edDescricao.getText().toString());
            cardapio.setDataDeLancamento(binding.edData.getText().toString());

            viewModel.inserir(cardapio);

            Log.d("Frame Secundário", cardapio.toString());

            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}