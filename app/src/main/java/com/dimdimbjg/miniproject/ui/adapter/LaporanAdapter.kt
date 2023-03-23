package com.dimdimbjg.miniproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dimdimbjg.miniproject.databinding.ItemListLaporanKeluhanBinding
import com.dimdimbjg.miniproject.domain.model.Laporan
import java.text.SimpleDateFormat
import java.util.*

class LaporanAdapter() : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {

    private var allLaporan = listOf<Laporan>()

    fun setAllLaporan(allLaporan: List<Laporan>) {
        allLaporan.let {
            this.allLaporan = it
            notifyDataSetChanged()
        }
    }


    inner class LaporanViewHolder(private val view: ItemListLaporanKeluhanBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(laporan: Laporan) {
            with(view) {
                tvVehicleName.text = laporan.vehicleName
                tvReportBy.text = laporan.createdBy
                tvReportId.text = laporan.reportId
                tvStatus.text = laporan.reportStatus
                tvNoPol.text = laporan.vehicleLicenseNumber
                tvKeluhan.text = laporan.note
                ivPhoto.load(laporan.photo) {
                    crossfade(true)
                }
                val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("id-ID"))
                val date = inputFormat.parse(laporan.createdAt)
                val output = SimpleDateFormat("EEEE, d MMM - HH:mm", Locale.forLanguageTag("id-ID"))

                tvDate.text= output.format(date!!)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = ItemListLaporanKeluhanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        holder.bind(allLaporan[position])
    }

    override fun getItemCount(): Int = allLaporan.size
}