package com.example.permissiondemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SecondActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val LOCATION_PERMISSION_REQUEST_CODE = 101
    private val STORAGE_PERMISSION_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.btnCamera).setOnClickListener {
            requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE, "Camera")
        }

        findViewById<Button>(R.id.btnLocation).setOnClickListener {
            requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                LOCATION_PERMISSION_REQUEST_CODE,
                "Location"
            )
        }

        // Request Storage Permission
        findViewById<Button>(R.id.btnStorage).setOnClickListener {
            requestPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_REQUEST_CODE,
                "Storage"
            )
        }
    }

    private fun requestPermission(permission: String, requestCode: Int, permissionName: String) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            showToast("$permissionName Permission already granted")
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> handlePermissionResult(
                grantResults,
                "Camera"
            )
            LOCATION_PERMISSION_REQUEST_CODE -> handlePermissionResult(
                grantResults,
                "Location"
            )
            STORAGE_PERMISSION_REQUEST_CODE -> handlePermissionResult(
                grantResults,
                "Storage"
            )
        }
    }

    private fun handlePermissionResult(grantResults: IntArray, permissionName: String) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showToast("$permissionName Permission granted. You can now access $permissionName.")
        } else {
            showToast("$permissionName Permission denied or not requested")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
