package br.com.pr.senai.restaurantecomabem.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;

@Database(entities = {Cardapio.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class CardapioDatabase extends RoomDatabase {
    private static CardapioDatabase database;

    public abstract CardapioDao cardapioDao();

    static CardapioDatabase getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), CardapioDatabase.class, "cardapiodb")
                    .fallbackToDestructiveMigration()
                    // Como inserir dados na Criação do Database
                    .addCallback(new Callback() {
                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                new Action<Cardapio>(obj -> database.cardapioDao().inserir(obj))
                                        .execute(Cardapio.builder()
                                                .id(0L)
                                                .produto("Strogonoff")
                                                .descricao("Arroz, Strogonoff e Batata Palha")
                                                .lancamento(LocalDate.of(2022, 11, 27))
                                                .cozinheiro(null)
                                                .del(false)
                                                .build());
                            }
                        }
                    })
                    // Como Migrar as versões do Database
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {

                        }
                    })
                    .build();
        }
        return database;
    }
}
