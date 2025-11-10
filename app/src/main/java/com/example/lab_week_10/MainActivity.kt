package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date

class MainActivity : AppCompatActivity() {

    // Database & ViewModel
    private val db by lazy { prepareDatabase() }
    private val viewModel by lazy { ViewModelProvider(this)[TotalViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeValueFromDatabase()
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text = getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) {
            updateText(it)
        }

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    // Build Room database
    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java,
            "total-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    // Ambil nilai awal dari database
    private fun initializeValueFromDatabase() {
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isEmpty()) {
            db.totalDao().insert(
                Total(
                    id = 1,
                    total = TotalObject(0, Date().toString())
                )
            )
        } else {
            viewModel.setTotal(totalList.first().total.value)
        }
    }

    // Simpan nilai terbaru ke database saat app di-pause
    override fun onPause() {
        super.onPause()
        val currentDate = Date().toString()
        db.totalDao().update(
            Total(
                ID,
                TotalObject(
                    viewModel.total.value ?: 0,
                    currentDate
                )
            )
        )
    }

    // Tampilkan Toast berisi tanggal terakhir update
    override fun onStart() {
        super.onStart()
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isNotEmpty()) {
            val lastDate = totalList.first().total.date
            Toast.makeText(this, "Last updated: $lastDate", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val ID: Long = 1
    }
}
