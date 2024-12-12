package com.c14220188.cobaroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.c14220188.cobaroom.database.daftarBelanja
import com.c14220188.cobaroom.database.historyBelanja


class adapterHistory(private val historyBelanja: MutableList<historyBelanja>) : RecyclerView.Adapter<adapterHistory.ListViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun delData(dtHistory: historyBelanja)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView .findViewById<TextView>(R.id.tvTanggal)
        var _tvItemBarang = itemView .findViewById<TextView>(R.id.tvItemBarang)
        var _tvJumlahBarang = itemView .findViewById<TextView>(R.id.tvJumlahBarang)
        var _btnDelete = itemView .findViewById<TextView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var item = historyBelanja[position]

        holder._tvTanggal.setText(item.tanggal)
        holder._tvItemBarang.setText(item.item)
        holder._tvJumlahBarang.setText(item.jumlah)

        holder._btnDelete.setOnClickListener {
            onItemClickCallBack.delData(item)
        }
    }

    fun isiData(daftar: List<historyBelanja>) {
        historyBelanja.clear()
        historyBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}
