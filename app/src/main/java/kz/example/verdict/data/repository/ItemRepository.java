package kz.example.verdict.data.repository;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kz.example.verdict.data.db.AppDatabase;
import kz.example.verdict.data.db.dao.ItemDao;
import kz.example.verdict.data.db.entities.Item;

public class ItemRepository {

    private final ItemDao itemDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ItemRepository(Context context) {
        itemDao = AppDatabase.Companion.getDatabase(context).itemDao();
    }

    public interface Callback<T> { void onResult(T result); }

    public void insert(Item item, Callback<Long> callback) {
        executor.execute(() -> {
            long id = itemDao.insert(item);
            if (callback != null) callback.onResult(id);
        });
    }

    public void getAll(Callback<List<Item>> callback) {
        executor.execute(() -> callback.onResult(itemDao.getAll()));
    }

    public void getByCategory(String category, Callback<List<Item>> callback) {
        executor.execute(() -> callback.onResult(itemDao.getByCategory(category)));
    }

    public void search(String query, Callback<List<Item>> callback) {
        executor.execute(() -> callback.onResult(itemDao.search(query)));
    }

    public void getTrending(Callback<List<Item>> callback) {
        executor.execute(() -> callback.onResult(itemDao.getTrending()));
    }

    public void getById(int id, Callback<Item> callback) {
        executor.execute(() -> callback.onResult(itemDao.getById(id)));
    }

    public void update(Item item) {
        executor.execute(() -> itemDao.update(item));
    }

    public void delete(int id, Callback<Boolean> callback) {
        executor.execute(() -> {
            itemDao.deleteById(id);
            if (callback != null) callback.onResult(true);
        });
    }
}
