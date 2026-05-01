package kz.example.verdict.data.repository;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kz.example.verdict.data.db.AppDatabase;
import kz.example.verdict.data.db.dao.VerdictDao;
import kz.example.verdict.data.db.entities.Verdict;

public class VerdictRepository {

    private final VerdictDao verdictDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public VerdictRepository(Context context) {
        verdictDao = AppDatabase.Companion.getDatabase(context).verdictDao();
    }

    public interface Callback<T> { void onResult(T result); }

    public void insert(Verdict verdict) {
        executor.execute(() -> verdictDao.insert(verdict));
    }

    public void getAll(Callback<List<Verdict>> callback) {
        executor.execute(() -> callback.onResult(verdictDao.getAll()));
    }

    public void getByItem(int itemId, Callback<List<Verdict>> callback) {
        executor.execute(() -> callback.onResult(verdictDao.getByItem(itemId)));
    }

    public void getAvgScore(int itemId, Callback<Float> callback) {
        executor.execute(() -> callback.onResult(verdictDao.getAvgScore(itemId)));
    }

    public void getCount(int itemId, Callback<Integer> callback) {
        executor.execute(() -> callback.onResult(verdictDao.getCount(itemId)));
    }

    public void deleteByItemId(int itemId, Callback<Boolean> callback) {
        executor.execute(() -> {
            verdictDao.deleteByItemId(itemId);
            if (callback != null) callback.onResult(true);
        });
    }
}
