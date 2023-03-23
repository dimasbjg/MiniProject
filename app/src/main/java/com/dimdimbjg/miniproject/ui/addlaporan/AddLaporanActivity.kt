package com.dimdimbjg.miniproject.ui.addlaporan

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.TypedValue
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.dimdimbjg.miniproject.R
import com.dimdimbjg.miniproject.data.Resource
import com.dimdimbjg.miniproject.databinding.ActivityAddLaporanBinding
import com.dimdimbjg.miniproject.domain.model.Vehicle
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class AddLaporanActivity : AppCompatActivity() {

    private val viewModel: AddLaporanViewModel by viewModel()
    private lateinit var binding: ActivityAddLaporanBinding
    private lateinit var jenisKendaraan: List<Vehicle>
    private lateinit var currentPhotoPath: String

    private lateinit var getAction: ActivityResultLauncher<Intent>
    private lateinit var file: File
    private var indexSelected by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Layouting icon on textfield
        val startIcon: CheckableImageButton =
            binding.tvLayoutCatatan.findViewById(com.google.android.material.R.id.text_input_start_icon)
        val sizeDp = 4
        val marginInDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, sizeDp.toFloat(), resources
                .displayMetrics
        ).toInt()

        val layoutParams = startIcon.layoutParams as LinearLayout.LayoutParams
        layoutParams.apply {
            gravity = Gravity.TOP or Gravity.LEFT
            topMargin = marginInDp
        }

        //Get Current Time
        val calendar = Calendar.getInstance()
        val date = calendar.time

        val dateFormat = SimpleDateFormat("EEEE, d MMM - HH:mm", Locale.forLanguageTag("id-ID"))

        binding.tvDate.text = dateFormat.format(date)

        //handling file
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(permissionListener).check()

        getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            file = File(currentPhotoPath)
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
            var cropbitmap: Bitmap? = null

            if (myBitmap != null) {
                if (myBitmap.width >= myBitmap.height) {
                    cropbitmap = Bitmap.createBitmap(
                        myBitmap,
                        myBitmap.width / 2 - myBitmap.height / 2,
                        0,
                        myBitmap.height,
                        myBitmap.height
                    )
                } else {
                    cropbitmap = Bitmap.createBitmap(
                        myBitmap,
                        0,
                        myBitmap.height / 2 - myBitmap.width / 2,
                        myBitmap.width,
                        myBitmap.width
                    )
                }

                val os = ByteArrayOutputStream()
                cropbitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                val bitmaparray = os.toByteArray()

                lifecycleScope.launch {
                    CoroutineScope(Dispatchers.IO).launch {
                        kotlin.runCatching {
                            val fos = FileOutputStream(file)
                            fos.write(bitmaparray)
                            fos.flush()
                            fos.close()

                            val checkBitmapCropped = BitmapFactory.decodeFile(file.absolutePath)

                            binding.ivPhoto.load(checkBitmapCropped) {
                                crossfade(true)
                            }
                        }
                    }
                }
            }
        }

        binding.cvImage.setOnClickListener {
            dispatchTakePictureIntent()
        }

        //handle upload

        binding.actvJenisKendaraan.setOnItemClickListener { _, _, i, _ ->
            indexSelected = i
            Toast.makeText(this, indexSelected.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.btAddLaporan.setOnClickListener {
            if (binding.actvJenisKendaraan.text.toString().isEmpty()) {
                val snackbar =
                    Snackbar.make(it, "Harap pilih jenis kendaraan", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    snackbar.dismiss()
                }
                snackbar.show()
                return@setOnClickListener
            }
            if (binding.etCatatanKeluhan.text.isEmpty()) {
                val snackbar = Snackbar.make(it, "Harap tuliskan keluhan", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    snackbar.dismiss()
                }
                snackbar.show()
                return@setOnClickListener
            }
            if (this::file.isInitialized) {
                if (file.exists()) {

                    val dataVehicle = viewModel.getAllVehicle()

                    dataVehicle.observe(this) {
                        when (it) {
                            is Resource.Success -> {
                                if (it.data != null) {

                                    val vehicleId = it.data[indexSelected].vehicleId
                                    val note = binding.etCatatanKeluhan.text.toString()
                                    val userId = "ILpBZSuKU8"

                                    val addLaporan = viewModel.addLaporan(file, vehicleId, userId, note)

                                    addLaporan.observe(this) {
                                        when (it) {
                                            is Resource.Success -> {
                                                Toast.makeText(this, "success insert", Toast.LENGTH_SHORT).show()
                                                finish()
                                            }
                                            is Resource.Loading -> {

                                            }
                                            is Resource.Error -> {
                                                Toast.makeText(this, "gagal insert", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }


                                }
                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Error -> {
                                Toast.makeText(this, "Koneksi terputus", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                } else {
                    val snackbar =
                        Snackbar.make(it, "Harap mengambil ulang gambar", Snackbar.LENGTH_LONG)
                    snackbar.setAction("OK") {
                        snackbar.dismiss()
                    }
                    snackbar.show()
                }
            } else {
                val snackbar = Snackbar.make(
                    it,
                    "Harap mengambil gambar terlebih dahulu",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("OK") {
                    snackbar.dismiss()
                }
                snackbar.show()
                return@setOnClickListener
            }

        }

    }

    override fun onResume() {
        super.onResume()
        val dataVehicle = viewModel.getAllVehicle()

        dataVehicle.observe(this) {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {

                        it.data.let {
                            this.jenisKendaraan = it
                        }

                        val namaKendaraan = mutableListOf<String>()

                        jenisKendaraan.map {
                            namaKendaraan.add(it.type)
                        }

                        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown, namaKendaraan)
                        binding.actvJenisKendaraan.setAdapter(arrayAdapter)
                        binding.actvJenisKendaraan.hint = "Jenis Kendaraan"
                    }
                }
                is Resource.Loading -> {
                    binding.actvJenisKendaraan.hint = "Loading"
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Koneksi terputus", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        ).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: Exception) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        Objects.requireNonNull(this),
                        "com.dimdimbjg.miniproject" + ".provider", it
                    );

                    intent.putExtra("aspectX", 1)
                    intent.putExtra("aspectY", 1)
                    intent.putExtra("scale", true)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    getAction.launch(intent)
                }
            }
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private object permissionListener : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
        }

        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?,
        ) {
        }
    }

}