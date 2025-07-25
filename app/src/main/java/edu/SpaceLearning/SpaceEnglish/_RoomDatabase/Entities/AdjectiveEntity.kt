package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_adjectives")
data class AdjectiveEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_adj")
    override val id: Int,
    @ColumnInfo(name = "english_adj")
    override val eng: String,
    @ColumnInfo(name = "french_adj")
    override val fr: String,
    @ColumnInfo(name = "spanish_adj")
    override val sp: String,
    @ColumnInfo(name = "arabic_adj")
    override val ar: String,
    @ColumnInfo(name = "examples_adj")
    override val example: String?
    ) : CategoryItem