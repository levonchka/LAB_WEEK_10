package com.example.lab_week_10.database

import androidx.room.*

@Dao
interface TotalDao {

    // Insert new row, replace if id already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)

    // Update existing row
    @Update
    fun update(total: Total)

    // Delete existing row
    @Delete
    fun delete(total: Total)

    // Get total value by ID
    @Query("SELECT * FROM total WHERE id = :id")
    fun getTotal(id: Long): List<Total>
}
