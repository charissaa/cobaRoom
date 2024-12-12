package com.c14220188.cobaroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.c14220188.cobaroom.database.daftarBelanja
import com.c14220188.cobaroom.database.daftarBelanjaDB
import com.c14220188.cobaroom.database.historyBelanja
import com.c14220188.cobaroom.database.historyBelanjaDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //global variable
    private lateinit var DB : daftarBelanjaDB
    private lateinit var DBHistory : historyBelanjaDB
    private lateinit var adapterDaftar: adapterDaftar
    private var arDaftar: MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inisialisasi DB
        DB = daftarBelanjaDB.getDatabase(this)
        DBHistory = historyBelanjaDB.getDatabase(this)

        //inisialisasi view
        var _btnHistory = findViewById<Button>(R.id.btnHistory)
        _btnHistory.setOnClickListener {
            startActivity(Intent(this, History::class.java))
        }
        var _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }
        adapterDaftar = adapterDaftar(arDaftar)
        var _rvDaftar = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvNotes)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        // event click listener utk pindah page tambah daftar
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        //event listener
        adapterDaftar.setOnItemClickCallBack(
            object : adapterDaftar.OnItemClickCallBack {
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftarBelanja)
                        }
                    }
                }

                override fun statusData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DBHistory.funhistoryBelanjaDAO().insert(
                            historyBelanja(
                                tanggal = dtBelanja.tanggal,
                                item = dtBelanja.item,
                                jumlah = dtBelanja.jumlah
                            )
                        )
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftarBelanja)
                        }
                    }
                }
            }
        )

        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("data ROOM", daftarBelanja.toString())
        }
    }
}