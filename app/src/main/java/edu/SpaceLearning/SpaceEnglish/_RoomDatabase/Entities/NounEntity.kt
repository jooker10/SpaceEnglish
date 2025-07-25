package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_nouns")
data class NounEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_noun")
    override val id: Int,
    @ColumnInfo(name = "english_noun")
    override val eng: String,
    @ColumnInfo(name = "french_noun")
    override val fr: String,
    @ColumnInfo(name = "spanish_noun")
    override val sp: String,
    @ColumnInfo(name = "arabic_noun")
    override val ar: String,
    @ColumnInfo(name = "examples_noun")
    override val example: String?
    ) : CategoryItem