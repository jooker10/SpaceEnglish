package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_adverbs")
data class AdverbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_adv")
    override val id: Int,
    @ColumnInfo(name = "english_adv")
    override val eng: String,
    @ColumnInfo(name = "french_adv")
    override val fr: String,
    @ColumnInfo(name = "spanish_adv")
    override val sp: String,
    @ColumnInfo(name = "arabic_adv")
    override val ar: String,
    @ColumnInfo(name = "examples_adv")
    override val example: String?
    ) : CategoryItem