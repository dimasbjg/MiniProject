package com.dimdimbjg.miniproject.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimdimbjg.miniproject.R
import com.dimdimbjg.miniproject.data.Resource
import com.dimdimbjg.miniproject.databinding.ActivityMainBinding
import com.dimdimbjg.miniproject.ui.adapter.LaporanAdapter
import com.dimdimbjg.miniproject.ui.addlaporan.AddLaporanFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

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
                        adapter.notifyDataSetChanged()
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
            AddLaporanFragment().show(supportFragmentManager, "addLaporan")
        }

    }

    private fun hideErrorMessage() {
        binding.tvErrorMessage.visibility = View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tap 2 kali untuk kembali", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}