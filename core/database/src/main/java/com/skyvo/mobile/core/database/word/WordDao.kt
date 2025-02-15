package com.skyvo.mobile.core.database.word

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordEntity>)

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE level = :level AND languageCode = :languageCode")
    suspend fun getLevelWordList(level: String, languageCode: String): List<WordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE level = :level AND languageCode = :languageCode ORDER BY RANDOM() LIMIT 10")
    suspend fun getRandomLevelWordList(level: String, languageCode: String): List<WordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE word = :word AND languageCode = :languageCode")
    suspend fun getSearchWordTranslate(word: String, languageCode: String): List<WordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE isFavorite = 1 AND languageCode = :languageCode")
    suspend fun getFavoriteWordList(languageCode: String): List<WordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE isKnow = 1 AND languageCode = :languageCode")
    suspend fun getLearnedWordList(languageCode: String): List<WordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE isNotMuch = 1 AND languageCode = :languageCode")
    suspend fun getNotMuchWordList(languageCode: String): List<WordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE level = :level AND languageCode = :languageCode AND isKnow = 0")
    suspend fun getStudyWordList(level: String, languageCode: String): List<WordEntity>?

    @Query("UPDATE ${RoomDatabaseConstant.WORD_TABLE} SET isKnow = 1, isNotMuch = 0 WHERE id = :id AND languageCode = :languageCode")
    suspend fun markWordAsKnown(id: Long, languageCode: String?)

    @Query("UPDATE ${RoomDatabaseConstant.WORD_TABLE} SET isNotMuch = 1, isKnow = 0  WHERE id = :id AND languageCode = :languageCode")
    suspend fun markWordAsUnKnown(id: Long, languageCode: String?)

    @Query("UPDATE ${RoomDatabaseConstant.WORD_TABLE} SET isFavorite = :isFavorite  WHERE id = :id AND languageCode = :languageCode")
    suspend fun markWordAsFavorite(isFavorite: Boolean, id: Long, languageCode: String?)

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE id = :id")
    suspend fun getWord(id: Long): WordEntity?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE id = :id and isKnow = 0")
    suspend fun getStudyWord(id: Long): WordEntity?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE id = :id and isKnow = 1")
    suspend fun getQuizWord(id: Long): WordEntity?

    @Query("SELECT * FROM ${RoomDatabaseConstant.WORD_TABLE} WHERE word = :word")
    suspend fun getWordTranslate(word: String?): WordEntity?

    @Update
    suspend fun updateWord(word: WordEntity)

    @Query("DELETE FROM ${RoomDatabaseConstant.WORD_TABLE}")
    suspend fun clearAll()
}