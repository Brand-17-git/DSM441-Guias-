package com.example.guia2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // 1. Variables globales de la interfaz
    lateinit var btnSaludar: Button
    lateinit var btnLimpiar: Button
    lateinit var etNombre: EditText
    lateinit var etApellido: EditText
    lateinit var tvSaludo: TextView
    lateinit var tvMensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Enlace de componentes con el XML
        btnSaludar = findViewById(R.id.btnSaludar)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        tvSaludo = findViewById(R.id.tvSaludo)
        tvMensaje = findViewById(R.id.tvMensaje)

        // Limpiamos los textos al iniciar
        tvSaludo.text = ""
        tvMensaje.text = ""

        // 3. Lógica del botón Saludar con las 3 franjas horarias
        btnSaludar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()

            // Validación de campos vacíos
            if (nombre.isEmpty() || apellido.isEmpty()) {
                mostrarToast("Error, los campos de texto están vacíos.")
            } else {
                // Obtenemos la hora en formato de 24 horas (0 a 23)
                val calendar = Calendar.getInstance()
                val hora = calendar.get(Calendar.HOUR_OF_DAY)

                // Evaluamos la hora con un 'when' estructurado
                val prefijoSaludo = when (hora) {
                    in 6..11  -> "Hola buenos días"     // De 6:00 AM a 11:59 AM
                    in 12..18 -> "Hola buenas tardes"   // De 12:00 PM a 6:59 PM
                    else      -> "Hola buenas noches"   // De 7:00 PM a 5:59 AM
                }

                // Concatenamos el saludo dinámico, el nombre y el apellido
                val saludoCompleto = "$prefijoSaludo, $nombre $apellido"
                val mensajeCuerpo = "Bienvenido al laboratorio 2 de DSM441."

                // Asignamos los valores a las etiquetas de la pantalla
                tvSaludo.text = saludoCompleto
                tvMensaje.text = mensajeCuerpo
                mostrarToast("Saludo generado exitosamente")
            }
        }

        // 4. Lógica del botón Limpiar
        btnLimpiar.setOnClickListener {
            etNombre.text.clear()
            etApellido.text.clear()
            tvSaludo.text = ""
            tvMensaje.text = ""
            etNombre.requestFocus()
            mostrarToast("Pantalla reiniciada, feliz dia")
        }
    }

    private fun mostrarToast(mensaje: String, duracion: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, mensaje, duracion).show()
    }
}