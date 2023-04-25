package br.com.pr.senai.restaurantecomabem.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.Nullable;

import br.com.pr.senai.restaurantecomabem.R;
import br.com.pr.senai.restaurantecomabem.databinding.FragmentSecondBinding;
import br.com.pr.senai.restaurantecomabem.model.Cardapio;
import br.com.pr.senai.restaurantecomabem.model.CardapioViewModel;
import br.com.pr.senai.restaurantecomabem.model.DataBaseException;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private CardapioViewModel viewModel;

    private Long idCardapio;
    private Cardapio cardapio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            idCardapio = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), new CardapioViewModel.Factory(requireActivity()))
                .get(CardapioViewModel.class);

        if(idCardapio != null) {
            try {
                cardapio = viewModel.localizar(idCardapio);
                binding.edProduto.setText(cardapio.getProduto());
                binding.edDescricao.setText(cardapio.getDescricao());
                binding.edProfissional.setText(cardapio.getProduto());
                binding.edData.setText(cardapio.getDataDeLancamentoReduzido());
            } catch (DataBaseException ex) {
                Snackbar.make(view, ex.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Fechar", null).show();
            }
        }


        binding.btSalvar.setOnClickListener(__ -> {
            var editar = true;
            if(cardapio == null) {
                cardapio = new Cardapio();
                editar = false;
            }

            cardapio.setProduto(binding.edProduto.getText().toString());
            cardapio.setDescricao(binding.edDescricao.getText().toString());
            cardapio.setCozinheiro(binding.edProfissional.getText().toString());
            cardapio.setDataDeLancamento(binding.edData.getText().toString());

            if(editar) viewModel.atualizar(cardapio);
            else viewModel.inserir(cardapio);

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