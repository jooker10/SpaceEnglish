package edu.SpaceLearning.SpaceEnglish._RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.AdjectiveDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.AdverbDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.IdiomDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.NounDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.PhrasalDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.SentenceDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao.VerbDao
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.AdjectiveEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.AdverbEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.IdiomEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.NounEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.PhrasalEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.SentenceEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.VerbEntity

@Database(entities = (arrayOf(VerbEntity::class,
    SentenceEntity::class,
    PhrasalEntity::class, NounEntity::class, AdjectiveEntity::class, AdverbEntity::class,
    IdiomEntity::class)), version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {

    abstract fun verbDao(): VerbDao
    abstract fun sentenceDao(): SentenceDao
    abstract fun phrasalDao(): PhrasalDao
    abstract fun nounDao(): NounDao
    abstract fun adjectiveDao(): AdjectiveDao
    abstract fun adverbDao(): AdverbDao
    abstract fun idiomDao(): IdiomDao

    companion object {
        @Volatile private var INSTANCE: MyDataBase? = null

        fun getDatabase(context : Context) : MyDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    "english_learning.db"
                ).createFromAsset("english_learning.db").build()
                INSTANCE = instance
                instance
            }
        }
    }


}