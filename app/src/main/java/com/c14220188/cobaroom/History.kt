package com.c14220188.cobaroom

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.c14220188.cobaroom.database.historyBelanja
import com.c14220188.cobaroom.database.historyBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {
    //var global
    private lateinit var DB : historyBelanjaDB
    private lateinit var adapterHistory: adapterHistory
    private var arHistory: MutableList<historyBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inisialisasi DB
        DB = historyBelanjaDB.getDatabase(this)

        //inisialisasi view
        adapterHistory = adapterHistory(arHistory)
        var _rvHistory = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvHistory)
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory

        //event listener
        adapterHistory.setOnItemClickCallBack(
            object : adapterHistory.OnItemClickCallBack {
                override fun delData(dtHistory: historyBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funhistoryBelanjaDAO().delete(dtHistory)
                        val historyBelanja = DB.funhistoryBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterHistory.isiData(historyBelanja)
                        }
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBelanja = DB.funhistoryBelanjaDAO().selectAll()
            adapterHistory.isiData(historyBelanja)
            Log.d("data ROOM", historyBelanja.toString())
        }
    }
}