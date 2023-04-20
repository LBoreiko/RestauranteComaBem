package br.com.pr.senai.restaurantecomabem.model;

import android.os.Build;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Cardapio {
    @PrimaryKey
    private Long id;
    private String produto;
    private String descricao;
    private LocalDate lancamento;
    private String cozinheiro;
    private boolean del;


    public String getDataDeLancamento() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return lancamento.format(DateTimeFormatter.ofPattern("dd 'de' MMM 'de' yyyy"));
        }
        return null;
    }


    public void setDataDeLancamento(String data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lancamento = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }
}
