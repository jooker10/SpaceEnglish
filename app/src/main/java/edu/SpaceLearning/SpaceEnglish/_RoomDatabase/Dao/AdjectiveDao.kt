package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Query
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.AdjectiveEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.VerbEntity

@Dao
interface AdjectiveDao {
    @Query("SELECT * FROM table_adjectives")
    suspend fun getAll() : List<AdjectiveEntity>
}