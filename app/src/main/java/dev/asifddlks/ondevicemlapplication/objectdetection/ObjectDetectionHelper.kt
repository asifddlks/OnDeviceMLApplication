package dev.asifddlks.ondevicemlapplication.objectdetection

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

/**
 * Created by Asif Ahmed on 2/1/24.
 */

class ObjectDetectionHelper(val context: Context) {

    /**
     * ML Kit Object Detection function.
     */
    fun runObjectDetectionWithCustomModel(bitmap: Bitmap): MutableList<Detection>? {

        Log.d(
            "Check Execution Time",
            "ObjectDetectionHelper Start Time: ${System.currentTimeMillis()}"
        )
        Log.d(
            "Check Execution Time",
            "ObjectDetectionHelper End Time: ${System.currentTimeMillis()}"
        )
        // Step 1: create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.5f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            context, // the application context
            "final.tflite", // must be same as the filename in assets folder
            options
        )

        // Step 3: feed given image to the model and print the detection result
        val results = detector.detect(image)

        // Step 4: Parse the detection result and show it
        //debugPrintWithCustomModel(results)

        //displayResultsWithCustomModel(results, bitmap)

        return results
    }

    private fun debugPrintWithCustomModel(results: List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(MainActivity.TAG, "Detected object: ${i} ")
            Log.d(
                MainActivity.TAG,
                "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})"
            )

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(MainActivity.TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(MainActivity.TAG, "    Confidence: ${confidence}%")
            }
        }
    }

    fun isImageContainsHuman(results: List<Detection>): Boolean {
        return results.any { detection ->
            detection.categories.any { category ->
                category.label.equals("person", ignoreCase = true)
            }
        }
    }
}