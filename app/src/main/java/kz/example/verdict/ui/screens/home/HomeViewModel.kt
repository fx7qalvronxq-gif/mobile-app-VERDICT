package kz.example.verdict.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.example.verdict.data.db.entities.Item
import kz.example.verdict.data.repository.ItemRepository

class HomeViewModel(context: Context) : ViewModel() {

    private val repository = ItemRepository(context)

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _trending = MutableStateFlow<List<Item>>(emptyList())
    val trending: StateFlow<List<Item>> = _trending

    private val _selectedCategory = MutableStateFlow("Всё")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init { load() }

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll { _items.value = it }
            repository.getTrending { _trending.value = it }
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isBlank()) {
                repository.getAll { _items.value = it }
            } else {
                repository.search(query) { _items.value = it }
            }
        }
    }

    fun filterBy(category: String) {
        _searchQuery.value = ""
        _selectedCategory.value = category
        viewModelScope.launch(Dispatchers.IO) {
            if (category == "Всё") {
                repository.getAll { _items.value = it }
            } else {
                repository.getByCategory(category) { _items.value = it }
            }
        }
    }
}
