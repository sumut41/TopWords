package com.skyvo.mobile.core.database.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<BookEntity>)

    @Query("SELECT * FROM ${RoomDatabaseConstant.BOOK_TABLE} WHERE languageCode = :languageCode")
    suspend fun getBookAllList(languageCode: String): List<BookEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.BOOK_TABLE} WHERE categoryLevel = :level AND languageCode = :languageCode")
    suspend fun getBookLevelList(level: String?, languageCode: String): List<BookEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.BOOK_TABLE} WHERE id = :id")
    suspend fun getBookDetail(id: Long): BookEntity?

    @Query("SELECT * FROM ${RoomDatabaseConstant.BOOK_TABLE} WHERE title LIKE '%' || :searchText || '%' or categoryLevel LIKE '%' || :searchText || '%'")
    suspend fun searchBook(searchText: String?): List<BookEntity>?

    @Query("DELETE FROM ${RoomDatabaseConstant.BOOK_TABLE}")
    suspend fun clearAll()
}