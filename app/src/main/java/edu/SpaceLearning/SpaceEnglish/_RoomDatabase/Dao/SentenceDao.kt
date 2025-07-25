package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Query
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.SentenceEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.VerbEntity

@Dao
interface SentenceDao {
    @Query("SELECT * FROM table_sentences_phrases")
    suspend fun getAll() : List<SentenceEntity>
}