package net.azarquiel.ahorcadogame.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.ahorcadogame.R
import net.azarquiel.ahorcadogame.model.Espanol
import net.azarquiel.ahorcadogame.viewmodel.EspanolViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvPalabra: TextView
    private lateinit var ivErrores: ImageView
    private lateinit var lv: LinearLayout
    private var errores: Int = 0
    private var aciertos: Int = 0
    private val letras = "abcdefghijklmnñopqrstuvwxyz".uppercase().toCharArray()
    private val letrasEspeciales = "áéíóú".uppercase().toCharArray()
    private var palabra = ""
    private var palabrasEspanol: List<Espanol> = emptyList()
    private var number: Int = 0
    private var oneplayer: Boolean = false
    private lateinit var letrasPalabra: MutableList<String>
    private lateinit var showPalabra: MutableList<String>
    private lateinit var espanolViewModel: EspanolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lv)
        ivErrores = findViewById(R.id.ivErrores)
        tvPalabra = findViewById(R.id.tvPalabra)


        espanolViewModel = ViewModelProvider(this).get(EspanolViewModel::class.java)
        GlobalScope.launch(){
            palabrasEspanol = espanolViewModel.getAllPalabras()
            launch(Dispatchers.Main){
                newGame()
            }
        }
    }

    private fun newGame() {
        errores = 0
        aciertos = 0
        val bundle = intent.extras
        palabra= bundle?.getString("palabra").toString().uppercase()
        if(palabra == "1234"){
            oneplayer = true
            palabra = palabrasEspanol[(palabrasEspanol.indices).random()].palabra.uppercase()

        } else{
            oneplayer = false
        }
        number = palabra.length
        letrasPalabra = MutableList(palabra.length) {i -> ""}
        showPalabra = MutableList(palabra.length) { i -> "__"}
        tvPalabra.text = showPalabra.toMutableList().joinToString(separator = " ")
        ivErrores.setBackgroundResource(R.color.white)
        procesarArrayPalabra()
        createTablero()
    }

    private fun procesarArrayPalabra() {
        for (i in 0 until palabra.length) {
            letrasPalabra[i] = palabra[i].toString()
        }

    }

    private fun createTablero() {
        ivErrores.setImageResource(android.R.color.transparent)

        for(i in 0 until lv.childCount) {
            val lh = lv.getChildAt(i) as LinearLayout
            for (j in 0 until lh.childCount) {
                val tecla = lh.getChildAt(j) as Button
                tecla.setOnClickListener(this)
                tecla.isEnabled = true
                tecla.setTextColor(Color.BLACK)
                tecla.text = letras[i*lh.childCount + j].toString()
                tecla.tag = tecla.text
            }
        }

    }

    override fun onClick(v: View?) {
        val btnPulsado = v as Button
        var letra = btnPulsado.tag.toString()

        var isTilde = false
        var letraTilde = 0

        for (i in letrasEspeciales.indices){
            if (letrasPalabra.contains(letrasEspeciales[i].toString())){
                isTilde = true
                letraTilde = i
            }
        }

        if ((letra == "A" ||letra == "E" ||letra == "I" ||letra == "O" ||letra == "U") && isTilde){
            when(letraTilde){
                0 -> if (letra == "A") letra = "Á"
                1 -> if (letra == "E") letra = "É"
                2 -> if (letra == "I") letra = "Í"
                3 -> if (letra == "O") letra = "Ó"
                4 -> if (letra == "U") letra = "Ú"
            }
        }

        if (letra in letrasPalabra) {
            btnPulsado.setTextColor(Color.GREEN)
            btnPulsado.isEnabled = false
            aciertos++
            actualizarTextBox(letra)
            calcularAciertosToWin(letra)
            checkGanado()

        } else {
            btnPulsado.setTextColor(Color.RED)
            btnPulsado.isEnabled = false
            errores++
            imagenesHoca(errores)
        }

    }

    private fun checkGanado() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)

        if(aciertos == number) {
            builder.setTitle("Has ganado!!")
            builder.setPositiveButton("Nueva jugada") { dialog, which ->
                if (oneplayer)
                    newGame()
                else
                    finish()
            }
            builder.setNegativeButton("Salir") { dialog, which ->
                val intento = Intent(this, Menu::class.java)
                startActivity(intento)
            }
            builder.show()
        }
    }

    private fun calcularAciertosToWin(letra: String) {
        val cont = letrasPalabra.count{it == letra}
        number -= (cont - 1)

    }

    private fun actualizarTextBox(letra: String) {
        for (i in 0 until letrasPalabra.size) {
            if (letrasPalabra[i] == letra) {
                showPalabra[i] = letra
            }
        }

        tvPalabra.text = showPalabra.toMutableList().joinToString(separator = " ")

    }

    private fun imagenesHoca(errores: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)

        val nombreImg = "fallo$errores"
        val id = ivErrores.resources.getIdentifier(nombreImg, "drawable", packageName)
        ivErrores.setBackgroundResource(id)

        if(errores == 9) {
            builder.setTitle("Has perdido. La palabra era ${palabra.lowercase()}")
            builder.setPositiveButton("Nueva jugada") { dialog, which ->
                if (oneplayer)
                    newGame()
                else
                    finish()
            }
            builder.setNegativeButton("Salir") { dialog, which ->
                val intento = Intent(this, Menu::class.java)
                startActivity(intento)
            }
            builder.show()
        }
    }


}

