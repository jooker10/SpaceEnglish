package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Query
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.VerbEntity

@Dao
interface VerbDao {
    @Query("SELECT * FROM table_verbs")
    suspend fun getAll() : List<VerbEntity>
}