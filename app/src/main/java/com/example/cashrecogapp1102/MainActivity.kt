package com.example.cashrecogapp1102

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.cashrecogapp1102.util.checkSelfPermissionCompat
import com.example.cashrecogapp1102.util.requestPermissionsCompat
import com.example.cashrecogapp1102.util.shouldShowRequestPermissionRationaleCompat
import com.example.cashrecogapp1102.util.showSnackbar
import com.google.android.material.snackbar.Snackbar
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.CameraActivity

const val PERMISSION_REQUEST_CAMERA = 0

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonCamera.setOnClickListener { showCameraPreview() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                mainLayout.showSnackbar("Camera permission granted", Snackbar.LENGTH_SHORT)
                startCamera()
            } else {
                // Permission request was denied.
                mainLayout.showSnackbar("Camera permission denied", Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun showCameraPreview() {
        // Check if the Camera permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            mainLayout.showSnackbar("Camera permission available", Snackbar.LENGTH_SHORT)
            startCamera()
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission()
        }
    }

    /**
     * Requests the [android.Manifest.permission.CAMERA] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private fun requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            mainLayout.showSnackbar("Camera accesss required",
                Snackbar.LENGTH_INDEFINITE, "Ok") {
                requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA)
            }

        } else {
            mainLayout.showSnackbar("Camera permission not available", Snackbar.LENGTH_SHORT)

            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}