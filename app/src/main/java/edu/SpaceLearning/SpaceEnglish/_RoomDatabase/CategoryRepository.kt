package edu.SpaceLearning.SpaceEnglish._RoomDatabase

import android.content.Context
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.VerbDao

class CategoryRepository( context : Context ) {
    private val db = MyDataBase.getDatabase(context)

    private val verbDao = db.verbDao()
    private val sentenceDao = db.sentenceDao()
    private val phrasalDao = db.phrasalDao()
    private val nounDao = db.nounDao()
    private val adjectiveDao = db.adjectiveDao()
    private val adverbDao = db.adverbDao()
    private val idiomDao = db.idiomDao()

    suspend fun getCategoriesByType(type : CategoryType) : List<CategoryItem>
    {
        return when(type) {
            CategoryType.Verb -> verbDao.getAll()
            CategoryType.Sentence -> sentenceDao.getAll()
            CategoryType.Phrasal -> phrasalDao.getAll()
            CategoryType.Noun -> nounDao.getAll()
            CategoryType.Adjective -> adjectiveDao.getAll()
            CategoryType.Adverb -> adverbDao.getAll()
            CategoryType.Idiom -> idiomDao.getAll()

        }
    }

}