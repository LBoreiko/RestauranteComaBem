package br.com.pr.senai.restaurantecomabem;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import br.com.pr.senai.restaurantecomabem.model.Cardapio;
import br.com.pr.senai.restaurantecomabem.model.CardapioViewModel;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

@RunWith(AndroidJUnit4.class)
public class CardapioViewModelTest extends TestCase {

    private CardapioViewModel viewModel;

    @Before
    public void setUp(){
        var context = ApplicationProvider.getApplicationContext();
        viewModel = new CardapioViewModel(context);

        viewModel.inserir(Cardapio.builder()
                .id(1L)
                .produto("Strogonoff")
                .descricao("Strogonoff de Mignon com Arroz branco e Batata Palha")
                .cozinheiro("Leonardo Boreiko")
                .lancamento(LocalDate.of(2022,10,1))
                .foto(null)
                .del(false)
                .build());
    }

    @After
    public void tearDown() throws Exception {
        var cardapio = viewModel.localizar(1L);
        if(cardapio != null) viewModel.remover(1L);

        cardapio = viewModel.localizar(2L);
        if(cardapio != null) viewModel.remover(2L);
    }

    @Test
    public void atualizar() throws Exception {
        var cardapio = viewModel.localizar(1L);
        assertNotNull("O Cardápio deve existir",cardapio);

        cardapio.setProduto("Ostra Gratinada");
        viewModel.atualizar(cardapio);

        var outro = viewModel.localizar(cardapio.getId());
        assertNotNull("O Cardápio deveria ser localizado", outro);
        assertEquals("O nome do Cardápio deveria estar alterado",
                "Ostra Gratinada" , outro.getProduto());
    }

    @Test
    public void inserir()throws Exception{
        var cardapio = Cardapio.builder()
                .id(2L)
                .produto("Stroganoff")
                .descricao("Strogonoff de Camarão com Arroz branco e Batata Frita")
                .cozinheiro("Gabriela Boreiko")
                .lancamento(LocalDate.of(2016,3,26))
                .foto(null)
                .del(false)
                .build();
        viewModel.inserir(cardapio);

        var outro = viewModel.localizar(2L);
        assertNotNull("O Cardápio deveria ser localizado", outro);
        assertEquals("O nome do Produto deveria ser Stroganoff",
                "Stroganoff" , outro.getProduto());
    }

    @Test
    public void localizar() throws Exception {
        var cardapio = viewModel.localizar(1L);
        assertNotNull("O Cardápio deve existir",cardapio);
    }

    @Test
    public void remover() throws Exception {
        var cardapio = viewModel.localizar(1L);
        assertNotNull("O Cardápio deve existir",cardapio);

        viewModel.remover(1L);
        cardapio = viewModel.localizar(1L);
        assertNull("O Cardápio NÃO deveria existir",cardapio);
    }
}
