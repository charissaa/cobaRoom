package com.c14220188.cobaroom

import android.content.Intent
import android.telecom.Call
import android.telecom.CallEventCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.c14220188.cobaroom.database.daftarBelanja
import com.c14220188.cobaroom.database.daftarBelanjaDAO

class adapterDaftar (private val daftarBelanja: MutableList<daftarBelanja>) :
    RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView .findViewById<TextView>(R.id.tvTanggal)
        var _tvItemBarang = itemView .findViewById<TextView>(R.id.tvItemBarang)
        var _tvJumlahBarang = itemView .findViewById<TextView>(R.id.tvJumlahBarang)

        var _btnEdit = itemView .findViewById<TextView>(R.id.btnEdit)
        var _btnDelete = itemView .findViewById<TextView>(R.id.btnDelete)
        var _btnDone = itemView.findViewById<TextView>(R.id.btnDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list, parent,
            false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]

        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallBack.delData(daftar)
        }

        holder._btnDone.setOnClickListener {

        }
    }

    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }

}
