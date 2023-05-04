package br.com.pr.senai.restaurantecomabem.model;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

interface DataBaseAction <Tipo> {
    void doAction(Tipo obj);
}

class Action<Tipo> {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private DataBaseAction<Tipo> action;

    public Action(DataBaseAction<Tipo> action) {
        this.action = action;
    }

    public void execute(Tipo obj) {
        try {
            executor.execute(() -> action.doAction(obj));
            executor.awaitTermination(2, TimeUnit.SECONDS);
        }catch (InterruptedException ex){
            // desprezar
        }
    }

    public void execute() {
        try {
        executor.execute(() -> action.doAction(null));
        executor.awaitTermination(2, TimeUnit.SECONDS);
        }catch (InterruptedException ex){
            // desprezar
        }
    }
}

interface DataBaseQuery<Tipo, Retorno> {
    Retorno doAction(Tipo obj);
}

class Query<Tipo, Retorno> {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private DataBaseQuery<Tipo, Retorno> query;

    public Query(DataBaseQuery<Tipo, Retorno> query) {
        this.query = query;
    }

    public Future<Retorno> execute(Tipo obj) { return executor.submit(() -> query.doAction(obj)); }

    public Future<Retorno> execute() { return executor.submit(() -> query.doAction(null)); }
}

class DateConverter {
    @TypeConverter
    public static LocalDate toDate(Long timestamp) {
        return timestamp == null ? null : Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    public static Long toLong(LocalDate date) {
        return date == null ? null : date
                .atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }
}