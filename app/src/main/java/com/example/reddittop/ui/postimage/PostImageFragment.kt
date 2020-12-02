package com.example.reddittop.ui.postimage

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.reddittop.R
import com.example.reddittop.databinding.FragmentPostImageBinding
import java.io.File

class PostImageFragment : Fragment() {

    private lateinit var binding: FragmentPostImageBinding
    private var imageUrl: String? = null
    var msg: String? = ""
    var lastMsg = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_post_image, container, false
        )

        imageUrl = arguments?.getString("imageUrl")

        loadImage(imageUrl)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun loadImage(imageUrl: String?) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_broken)
            .into(binding.postBigImageView)
    }

    private fun saveImage(imageUrl: String?) {
        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager =
            activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(imageUrl)
        val imageName = imageUrl?.substring(imageUrl.lastIndexOf("/") + 1)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(imageName)
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    imageName
                )
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                msg = imageUrl?.let { statusMessage(it, directory, status) }
                if (msg != lastMsg) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                    lastMsg = msg ?: ""
                }
                cursor.close()
            }
        }).start()
    }

    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> getString(R.string.failed_download)
            DownloadManager.STATUS_RUNNING -> getString(R.string.downloading)
            DownloadManager.STATUS_SUCCESSFUL -> getString(R.string.image_downloaded) + "$directory${File.separator}" +
                    url.substring(url.lastIndexOf("/") + 1
            )
            else -> getString(R.string.nothing_to_download)
        }
        return msg
    }


    private fun askPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.permission_required))
                .setMessage(getString(R.string.permission_explaining))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        111
                    )
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, i ->
                    dialogInterface.cancel()
                }
                .show()
        } else {
            saveImage(imageUrl)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            111 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    saveImage(imageUrl)
                }
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_image_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveImage -> saveImageWithPermission()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveImageWithPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
        ) {
            askPermissions()
        } else {
            saveImage(imageUrl)
        }
    }


}