package kz.example.verdict.data.repository;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kz.example.verdict.data.db.AppDatabase;
import kz.example.verdict.data.db.dao.SavedDao;
import kz.example.verdict.data.db.entities.SavedItem;

public class SavedRepository {

    private final SavedDao savedDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SavedRepository(Context context) {
        savedDao = AppDatabase.Companion.getDatabase(context).savedDao();
    }

    public interface Callback<T> { void onResult(T result); }

    public void save(SavedItem item) {
        executor.execute(() -> savedDao.save(item));
    }

    public void unsave(int itemId) {
        executor.execute(() -> savedDao.unsave(itemId));
    }

    public void getAll(Callback<List<SavedItem>> callback) {
        executor.execute(() -> callback.onResult(savedDao.getAll()));
    }

    public void isSaved(int itemId, Callback<Boolean> callback) {
        executor.execute(() -> callback.onResult(savedDao.isSaved(itemId) > 0));
    }
}
