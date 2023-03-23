package com.dimdimbjg.miniproject.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimdimbjg.miniproject.data.Resource
import com.dimdimbjg.miniproject.databinding.ActivityMainBinding
import com.dimdimbjg.miniproject.domain.model.Vehicle
import com.dimdimbjg.miniproject.ui.adapter.LaporanAdapter
import com.dimdimbjg.miniproject.ui.addlaporan.AddLaporanActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = LaporanAdapter()

        with(binding.rvLaporanKeluhan) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        val data = mainViewModel.getAllLaporan()


        data.observe(this) {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setAllLaporan(it.data)
                    }
                    hideErrorMessage()
                    showLoading(false)
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Error -> {
                    binding.tvErrorMessage.text = it.message
                    showLoading(false)
                }
            }
        }

        binding.btAddLaporan.setOnClickListener {
            val intent = Intent(this,AddLaporanActivity::class.java)
            startActivity(intent)
        }

    }

    private fun hideErrorMessage() {
        binding.tvErrorMessage.visibility = View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}