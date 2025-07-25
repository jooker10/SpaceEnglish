package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_verbs")
data class VerbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_verb")
    override val id: Int,
    @ColumnInfo(name = "english_verb")
    override val eng: String,
    @ColumnInfo(name = "french_verb")
    override val fr: String,
    @ColumnInfo(name = "spanish_verb")
    override val sp: String,
    @ColumnInfo(name = "arabic_verb")
    override val ar: String,
    @ColumnInfo(name = "examples_verb")
    override val example: String?
) : CategoryItem