package net.azarquiel.ahorcadogame.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.ahorcadogame.R
import net.azarquiel.ahorcadogame.model.Espanol
import net.azarquiel.ahorcadogame.util.Util
import net.azarquiel.ahorcadogame.viewmodel.EspanolViewModel

class Menu : AppCompatActivity() {
    private lateinit var btnoneplayer: Button
    private lateinit var btnmultiplayer: Button
    private lateinit var palabrasEspanol: List<Espanol>
    private lateinit var espanolViewModel: EspanolViewModel
    private var palabra = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        Util.inyecta(this, "palabras.sqlite")

        espanolViewModel = ViewModelProvider(this).get(EspanolViewModel::class.java)

        getData()



        btnoneplayer = findViewById<Button>(R.id.btnoneplayer)
        btnmultiplayer = findViewById<Button>(R.id.btnmultiplayer)



        btnoneplayer.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            intento.putExtra("palabra", "1234") //para enviar parámetros a otra activity
            startActivity(intento)
        }

        btnmultiplayer.setOnClickListener {
            val intento = Intent(this, Inicio::class.java)
            startActivity(intento)
        }

    }

    private fun getData() {

        GlobalScope.launch(){
            palabrasEspanol = espanolViewModel.getAllPalabras()
            launch(Dispatchers.Main){
                palabra = palabrasEspanol[(palabrasEspanol.indices).random()].palabra
            }
        }
    }
}