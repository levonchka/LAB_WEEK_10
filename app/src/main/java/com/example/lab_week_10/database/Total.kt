package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity utama untuk tabel "total"
@Entity(tableName = "total")
data class Total(
    // ID unik untuk setiap record
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    // TotalObject berisi nilai dan tanggal terakhir update
    @Embedded
    val total: TotalObject
)

// Class embedded untuk menyimpan nilai dan tanggal
data class TotalObject(
    @ColumnInfo(name = "value")
    val value: Int,

    @ColumnInfo(name = "date")
    val date: String
)
