package dev.asifddlks.ondevicemlapplication.objectdetection

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions


/**
 * Created by Asif Ahmed on 2/1/24.
 */

class FaceDetectionHelper(val context: Context) {

    fun runFaceContourDetection(bitmap: Bitmap, callback: (Boolean) -> Unit) {
        Log.d(
            "Check Execution Time",
            "FaceDetectionHelper Start Time: ${System.currentTimeMillis()}"
        )

        val image = InputImage.fromBitmap(bitmap, 0)
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()

        val detector: FaceDetector = FaceDetection.getClient(options)
        detector.process(image)
            .addOnSuccessListener { faces ->
                hasFace(faces, callback)
                Log.d(
                    "Check Execution Time",
                    "FaceDetectionHelper End Time: ${System.currentTimeMillis()}"
                )
            }
            .addOnFailureListener { error ->
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
                Log.d(
                    "Check Execution Time",
                    "FaceDetectionHelper End Time: ${System.currentTimeMillis()}"
                )
            }
    }

    private fun hasFace(faces: List<Face>, callback: (Boolean) -> Unit) {
        // Task completed successfully
        if (faces.isEmpty()) {
            Toast.makeText(context, "No face found", Toast.LENGTH_SHORT).show()
            callback(false)
            return
        } else {
            callback(true)
        }

        /*for (i in faces.indices) {
            val face: Face = faces[i]
            Toast.makeText(context, "${faces.size} face found", Toast.LENGTH_SHORT).show()
        }*/
    }
}