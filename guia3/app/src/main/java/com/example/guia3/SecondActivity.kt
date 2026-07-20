package com.example.guia3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Referencia al archivo layout
        setContentView(R.layout.activity_second)

        // 2. Referencia al boton
        btnRegresar = findViewById(R.id.btnRegresar)

        // 3. Registro del observador pasándole el nombre de esta actividad
        lifecycle.addObserver(MyLifeCycleObserver("SecondActivity"))

        // 4. Listener del boton
        btnRegresar.setOnClickListener {
            // 5. Uso de un intent explicito para iniciar MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        mostrarToast("onCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarToast("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarToast("onResume")
    }

    override fun onPause() {
        super.onPause()
        mostrarToast("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarToast("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarToast("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarToast("onDestroy")
    }

    private fun mostrarToast(mensaje: String, duracion: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            this,
            "SecondActivity -> $mensaje",
            duracion
        ).show()
    }
}