package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Query
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.IdiomEntity
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.VerbEntity

@Dao
interface IdiomDao {
    @Query("SELECT * FROM table_idioms")
    suspend fun getAll() : List<IdiomEntity>
}