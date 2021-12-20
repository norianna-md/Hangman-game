package net.azarquiel.ahorcadogame.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import net.azarquiel.ahorcadogame.R

class Inicio : AppCompatActivity() {
    private var palabra: String = ""
    private lateinit var btnAceptar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val etPalabra = findViewById<EditText>(R.id.etPalabra)
        btnAceptar = findViewById<Button>(R.id.btnAceptar)

        btnAceptar.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            intento.putExtra("palabra", etPalabra.text.toString()) //para enviar parámetros a otra activity
            startActivity(intento)
            etPalabra.text.clear()
        }

    }
}