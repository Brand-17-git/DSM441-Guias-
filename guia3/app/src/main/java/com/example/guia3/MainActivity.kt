package com.example.guia3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnSiguiente: Button
    lateinit var btnIncrementar: Button
    lateinit var tvContador: TextView
    var contador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSiguiente = findViewById(R.id.btnSiguiente)
        lifecycle.addObserver(MyLifeCycleObserver("MainActivity"))

        btnSiguiente.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        btnIncrementar = findViewById(R.id.btnIncrementar)
        tvContador = findViewById(R.id.tvContador)

        // --- 1. RECUPERAR EL VALOR GUARDADO ---
        // Si savedInstanceState no es nulo, significa que la pantalla se recreó (por ejemplo, al rotar)
        if (savedInstanceState != null) {
            contador = savedInstanceState.getInt("CONTADOR_KEY", 0)
            tvContador.text = "Valor contador: $contador"
        }

        btnIncrementar.setOnClickListener {
            tvContador.text = "Valor contador: ${++contador}"
        }

        mostrarToast("onCreate")
    }

    // --- 2. GUARDAR EL VALOR ANTES DE DESTRUIRSE ---
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardamos el valor actual de la variable con una etiqueta o llave
        outState.putInt("CONTADOR_KEY", contador)
        mostrarToast("onSaveInstanceState (Estado Guardado)")
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
            "MainActivity -> $mensaje",
            duracion
        ).show()
    }
}
