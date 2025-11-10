package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {

    // Ambil instance ViewModel
    private val viewModel by lazy { ViewModelProvider(this)[TotalViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareViewModel()
    }

    // Update teks di TextView utama (jika masih ada)
    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total)?.text = getString(R.string.text_total, total)
    }

    // Hubungkan ViewModel ke UI
    private fun prepareViewModel() {
        // Observe LiveData: ketika data berubah, update UI
        viewModel.total.observe(this, {
            updateText(it)
        })

        // Tambahkan event tombol
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }
}
