package net.azarquiel.ahorcadogame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu : AppCompatActivity() {
    private lateinit var btnoneplayer: Button
    private lateinit var btnmultiplayer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnoneplayer = findViewById<Button>(R.id.btnoneplayer)
        btnmultiplayer = findViewById<Button>(R.id.btnmultiplayer)

        btnoneplayer.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            intento.putExtra("palabra", "one-player-mode") //para enviar parámetros a otra activity
            startActivity(intento)
        }

        btnmultiplayer.setOnClickListener {
            val intento = Intent(this, Inicio::class.java)
            startActivity(intento)
        }

    }
}