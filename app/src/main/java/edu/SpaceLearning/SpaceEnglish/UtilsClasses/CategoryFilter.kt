package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.widget.Filter
import java.util.*

/**
 * Custom Filter class to filter a list of Category objects based on native or English text.
 */
class CategoryFilter(
    private val originalList: List<Category>,
    private val onFilterResult: (List<Category>) -> Unit
) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList = if (constraint.isNullOrEmpty()) {
            originalList
        } else {
            val query = constraint.toString().lowercase(Locale.getDefault()).trim()
            originalList.filter {
                it.englishName.lowercase(Locale.getDefault()).contains(query)
                        || getNativeText(it).lowercase(Locale.getDefault()).contains(query)
            }
        }

        return FilterResults().apply { values = filteredList }
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        val resultList = results?.values as? List<Category> ?: emptyList()
        onFilterResult(resultList)
    }

    private fun getNativeText(category: Category): String {
        return when (QuizUtils.translationLanguage) {
            Constants.LANGUAGE_NATIVE_ARABIC -> category.arabicName
            Constants.LANGUAGE_NATIVE_SPANISH -> category.spanishName
            else -> category.frenchName
        }
    }
}