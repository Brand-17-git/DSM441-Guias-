package com.example.guia2 // Asegúrate de que coincida exactamente con tu paquete

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class Micalculadora : AppCompatActivity() {

    // 1. Declaramos los componentes visuales
    lateinit var etNumero: EditText
    lateinit var tvResultado: TextView
    lateinit var tvHistorial: TextView

    // Variables para controlar los estados de la operación
    var resultadoAcumulado: Double = 0.0
    var esPrimeraOperacion: Boolean = true

    // Lista dinámica para guardar las últimas 5 operaciones
    val listaHistorial = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_micalculadora) // Enlace al diseño corregido

        // 2. Enlazamos los componentes de la interfaz
        etNumero = findViewById(R.id.etNumero)
        tvResultado = findViewById(R.id.tvResultado)
        tvHistorial = findViewById(R.id.tvHistorial)

        val btnSuma: Button = findViewById(R.id.btnSuma)
        val btnResta: Button = findViewById(R.id.btnResta)
        val btnMultiplicacion: Button = findViewById(R.id.btnMultiplicacion)
        val btnDivision: Button = findViewById(R.id.btnDivision)
        val btnPorcentaje: Button = findViewById(R.id.btnPorcentaje)
        val btnCuadrado: Button = findViewById(R.id.btnCuadrado)
        val btnRaiz: Button = findViewById(R.id.btnRaiz)
        val btnLimpiarTodo: Button = findViewById(R.id.btnLimpiarTodo)

        // 3. LISTENERS PARA OPERACIONES BÁSICAS (Requieren 2 números)
        btnSuma.setOnClickListener {
            ejecutarOperacionBasica("+") { a, b -> a + b }
        }

        btnResta.setOnClickListener {
            ejecutarOperacionBasica("-") { a, b -> a - b }
        }

        btnMultiplicacion.setOnClickListener {
            ejecutarOperacionBasica("*") { a, b -> a * b }
        }

        btnDivision.setOnClickListener {
            val numEntrada = obtenerNumeroEntrada()
            if (numEntrada != null) {
                // Validación para evitar división por cero
                if (numEntrada == 0.0) {
                    mostrarToast(getString(R.string.error_division_cero))
                } else {
                    ejecutarOperacionBasica("/") { a, b -> a / b }
                }
            }
        }

        // 4. LISTENERS PARA OPERACIONES CIENTÍFICAS (Un solo número instantáneo)
        btnPorcentaje.setOnClickListener {
            ejecutarOperacionUnitaria("%") { a -> a / 100.0 }
        }

        btnCuadrado.setOnClickListener {
            ejecutarOperacionUnitaria("²") { a -> a * a }
        }

        btnRaiz.setOnClickListener {
            ejecutarOperacionUnitaria("√") { a ->
                if (a < 0) {
                    mostrarToast("Error: Raíz negativa")
                    0.0
                } else {
                    sqrt(a)
                }
            }
        }

        // 5. LÓGICA DEL BOTÓN LIMPIAR TODO
        btnLimpiarTodo.setOnClickListener {
            etNumero.text.clear()
            resultadoAcumulado = 0.0
            esPrimeraOperacion = true
            listaHistorial.clear()
            tvResultado.text = getString(R.string.txt_resultado_inicial)
            tvHistorial.text = getString(R.string.txt_historial_inicial)
            mostrarToast("Calculadora reiniciada")
        }
    }

    // Procesa las operaciones de dos operandos (El acumulado anterior y el nuevo ingresado)
    private fun ejecutarOperacionBasica(simbolo: String, operacion: (Double, Double) -> Double) {
        val numEntrada = obtenerNumeroEntrada() ?: return

        val antiguoAcumulado = resultadoAcumulado
        if (esPrimeraOperacion) {
            resultadoAcumulado = numEntrada
            esPrimeraOperacion = false
            agregarAlHistorial("Inició con: $resultadoAcumulado")
        } else {
            resultadoAcumulado = operacion(resultadoAcumulado, numEntrada)
            agregarAlHistorial("$antiguoAcumulado $simbolo $numEntrada = $resultadoAcumulado")
        }

        tvResultado.text = resultadoAcumulado.toString()
        etNumero.text.clear()
    }

    // Procesa operaciones sobre el número que está en el campo de texto inmediatamente
    private fun ejecutarOperacionUnitaria(simbolo: String, operacion: (Double) -> Double) {
        val numEntrada = obtenerNumeroEntrada() ?: return

        // Validación extra para raíz negativa
        if (simbolo == "√" && numEntrada < 0) {
            mostrarToast("Error: Raíz negativa")
            return
        }

        val resultadoNodo = operacion(numEntrada)
        agregarAlHistorial("$simbolo($numEntrada) = $resultadoNodo")
        resultadoAcumulado = resultadoNodo
        esPrimeraOperacion = false

        tvResultado.text = resultadoAcumulado.toString()
        etNumero.text.clear()
    }

    // Valida que el campo no esté vacío y extrae el valor Double
    private fun obtenerNumeroEntrada(): Double? {
        val texto = etNumero.text.toString().trim()
        return if (texto.isEmpty()) {
            mostrarToast(getString(R.string.error_num_invalido))
            null
        } else {
            texto.toDoubleOrNull()
        }
    }

    // Agrega registros al historial controlando que solo existan los últimos 5
    private fun agregarAlHistorial(registro: String) {
        if (listaHistorial.size >= 5) {
            listaHistorial.removeAt(0) // Remueve el registro más antiguo (el primero)
        }
        listaHistorial.add(registro)

        // Concatenamos y pintamos el historial en el TextView
        val sb = StringBuilder(getString(R.string.txt_historial_inicial)).append("\n")
        for (i in listaHistorial.indices) {
            sb.append("${i + 1}. ${listaHistorial[i]}\n")
        }
        tvHistorial.text = sb.toString()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}