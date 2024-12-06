package com.c14220188.cobaroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c14220188.cobaroom.database.daftarBelanja
import com.c14220188.cobaroom.database.daftarBelanjaDB
import com.c14220188.cobaroom.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftar : AppCompatActivity() {

    var DB = daftarBelanjaDB.getDatabase(this)

    var tanggal = getCurrentDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var _etItem = findViewById<EditText>(R.id.etItem)
        var _etJumlah = findViewById<EditText>(R.id.etJumlah)
        var _btnTambah = findViewById<Button>(R.id.btnTambah)
        var _btnUpdate = findViewById<Button>(R.id.btnUpdate)

        _btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString()
                    )
                )
            }
        }


    }
}