package br.com.pr.senai.restaurantecomabem.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.Nullable;

import br.com.pr.senai.restaurantecomabem.R;
import br.com.pr.senai.restaurantecomabem.databinding.FragmentSecondBinding;
import br.com.pr.senai.restaurantecomabem.model.Cardapio;
import br.com.pr.senai.restaurantecomabem.model.CardapioViewModel;
import br.com.pr.senai.restaurantecomabem.model.DataBaseException;
import kotlin.Unit;


public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    private CardapioViewModel viewModel;
    private Long idCardapio;
    private Cardapio cardapio;
    private Uri pratoUri;
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

        binding.ivPratos.setOnClickListener(__ -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), binding.ivPratos);
            popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.ac_tirar_foto) {
                    ImagePicker.with(this)
                            .crop()
                            .cameraOnly()
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .createIntent(intent -> {
                                launcher.launch(intent);
                                return Unit.INSTANCE;
                            });
                } else if(item.getItemId() == R.id.ac_galeria_foto) {
                    ImagePicker.with(this)
                            .crop()
                            .galleryOnly()
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .createIntent(intent -> {
                                launcher.launch(intent);
                                return Unit.INSTANCE;
                            });
                } else {
                    binding.ivPratos.setImageResource(R.drawable.pratos);
                    pratoUri = null;
                }
                return true;
            });
            popupMenu.show();
        });


        if(idCardapio != null) {
            try {
                cardapio = viewModel.localizar(idCardapio);
                binding.edProduto.setText(cardapio.getProduto());
                binding.edDescricao.setText(cardapio.getDescricao());
                binding.edProfissional.setText(cardapio.getCozinheiro());
                binding.edData.setText(cardapio.getDataDeLancamentoReduzido());
                String foto = cardapio.getFoto();
                if(foto != null) {
                    pratoUri = Uri.parse(foto);
                    Glide.with(this)
                            .load(pratoUri)
                            .fitCenter()
                            .into(binding.ivPratos);
                }
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
            cardapio.setFoto(pratoUri != null ? pratoUri.toString() : null);

            if(editar) viewModel.atualizar(cardapio);
            else viewModel.inserir(cardapio);

            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                int resultCode = result.getResultCode();
                Intent oIntent = result.getData();

                if(resultCode == Activity.RESULT_OK) {
                    pratoUri = oIntent.getData();
                    Glide.with(this)
                            .load(pratoUri)
                            .fitCenter()
                            .into(binding.ivPratos);
                } else if(resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(requireActivity(),
                            ImagePicker.getError(oIntent), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(requireActivity(), "Cancelado", Toast.LENGTH_LONG).show();
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}