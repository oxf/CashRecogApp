package com.example.cashrecogapp1102.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import com.example.cashrecogapp1102.R
import kotlinx.android.synthetic.main.activity_camera.*
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat

class CameraActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        cameraView.setCameraPermissionGranted()
        cameraView.visibility = SurfaceView.VISIBLE
        cameraView.setCvCameraViewListener(this)
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        //TODO("Not yet implemented")
    }

    override fun onCameraViewStopped() {
        //TODO("Not yet implemented")
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        return inputFrame!!.rgba()
    }
}