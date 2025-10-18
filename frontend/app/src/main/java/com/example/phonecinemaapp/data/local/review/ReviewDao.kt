package com.example.phonecinemaapp.data.local.review

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: ReviewEntity)

    @Query("SELECT * FROM reviews WHERE movieId = :movieId ORDER BY timestamp DESC")
    suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity>
}
