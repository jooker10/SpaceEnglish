package edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryItem

@Entity(tableName = "table_sentences_phrases")
data class SentenceEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_sentence")
    override val id: Int,
    @ColumnInfo(name = "english_sentence")
    override val eng: String,
    @ColumnInfo(name = "french_sentence")
    override val fr: String,
    @ColumnInfo(name = "spanish_sentence")
    override val sp: String,
    @ColumnInfo(name = "arabic_sentence")
    override val ar: String,

    ) : CategoryItem {
    override val example: String? = null
    }