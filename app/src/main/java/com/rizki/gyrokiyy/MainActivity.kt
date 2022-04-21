package com.rizki.gyrokiyy

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

//langkah 1 : Mengimplement sensor event listener
class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        square = findViewById(R.id.kotaksensor)

        //langkah 3 : membuat/menyiapkan sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        //langkah 4 : menentukan sensor accelerometer untuk digunakan pada aplikasi
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
    // langkah 2 : abstraksi method dari onSensorChanged
    override fun onSensorChanged(event: SensorEvent?) {
        //Langkah 5 : melakukan checking sensor yang digunakan
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //movement smartphone kiri kanan
            val xVal = event.values[0]
            //movement smartphone atas bawah
            val yVal = event.values[1]
            // memasukan data kedalam kotak sensor pada activity main
            square.apply {
                rotationX = xVal * 3f
                rotationY = yVal * 3f
                rotation = -yVal
                translationX = yVal * -10 // translete nilai y maximal -10
                translationY = xVal * 10 // translete nilai x maximal 10
            }
            //langkah 6 : mengubah warna, dari warna hijau jika posisi updown dan sides berada pada nilai 0
            //langkah 6 : jika nilai updown dan sides tidak 0 maka warna kotak akan berubah menjadi warna merah
            val color = if (xVal.toInt() == 0 && yVal.toInt() == 0) Color.GREEN else Color.RED
            square.setBackgroundColor(color)
            //langkah 7 : membuat text yang mengiukti didalam kotak yang dapat bergerak
            square.text =
                "posisi x : ${xVal.toInt()}\nposisi y : ${yVal.toInt()}"
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}