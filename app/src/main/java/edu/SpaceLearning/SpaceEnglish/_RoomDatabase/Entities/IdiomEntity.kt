package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_idioms")
data class IdiomEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_idiom")
    override val id: Int,
    @ColumnInfo(name = "english_idiom")
    override val eng: String,
    @ColumnInfo(name = "french_idiom")
    override val fr: String,
    @ColumnInfo(name = "spanish_idiom")
    override val sp: String,
    @ColumnInfo(name = "arabic_idiom")
    override val ar: String,
    ) : CategoryItem {
    override val example: String? = null
}