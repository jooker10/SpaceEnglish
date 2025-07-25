package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryRepository
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryItem>> (emptyList())

    val categories : StateFlow<List<CategoryItem>> = _categories

    fun load(type: CategoryType) {
        viewModelScope.launch {
            _categories.value = repository.getCategoriesByType(type)
        }
    }
}