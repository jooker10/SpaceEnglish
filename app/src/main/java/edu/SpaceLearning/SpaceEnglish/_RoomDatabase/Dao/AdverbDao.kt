package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Query
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.AdjectiveEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.AdverbEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.VerbEntity

@Dao
interface AdverbDao {
    @Query("SELECT * FROM table_adverbs")
    suspend fun getAll() : List<AdverbEntity>
}