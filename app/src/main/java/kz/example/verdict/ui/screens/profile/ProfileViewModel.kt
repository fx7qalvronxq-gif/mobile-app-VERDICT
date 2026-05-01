package kz.example.verdict.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.example.verdict.data.db.entities.Verdict
import kz.example.verdict.data.repository.VerdictRepository

class ProfileViewModel(context: Context) : ViewModel() {

    private val repo = VerdictRepository(context)

    private val _verdicts = MutableStateFlow<List<Verdict>>(emptyList())
    val verdicts: StateFlow<List<Verdict>> = _verdicts

    val avgScore: StateFlow<Float> get() = _avg
    private val _avg = MutableStateFlow(0f)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAll { list ->
                _verdicts.value = list
                _avg.value = if (list.isEmpty()) 0f
                else list.map { it.score }.average().toFloat()
            }
        }
    }
}
