package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_phrasals")
data class PhrasalEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_phrasal")
    override val id: Int,
    @ColumnInfo(name = "english_phrasal")
    override val eng: String,
    @ColumnInfo(name = "french_phrasal")
    override val fr: String,
    @ColumnInfo(name = "spanish_phrasal")
    override val sp: String,
    @ColumnInfo(name = "arabic_phrasal")
    override val ar: String,
    @ColumnInfo(name = "examples_phrasal")
    override val example: String?
    ) : CategoryItem