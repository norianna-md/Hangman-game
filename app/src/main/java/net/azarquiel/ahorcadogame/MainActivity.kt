package net.azarquiel.ahorcadogame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvPalabra: TextView
    private lateinit var ivErrores: ImageView
    private lateinit var lv: LinearLayout
    private var errores: Int = 0
    private var aciertos: Int = 0
    private val letras = "abcdefghijklmnñopqrstuvwxyz".uppercase().toCharArray()
    private val listaPalabras = arrayListOf<String>("CARAMELO", "BALONCESTO", "VENTANA", "GLADIADOR", "PECULIARIDAD", "NOVEDAD", "ÑU", "JUEGO", "AHORACADO", "KILOGRAMO", "AZUL", "COLORES", "VIOLETA", "CAMALEON", "ARISTOCRACIA")
    private var palabra = ""
    private var number: Int = 0
    private lateinit var letrasPalabra: MutableList<String>
    private lateinit var showPalabra: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lv)
        ivErrores = findViewById(R.id.ivErrores)
        tvPalabra = findViewById(R.id.tvPalabra)
        newGame()

    }


    private fun newGame() {
        errores = 0
        aciertos = 0
        listaPalabras.shuffle()
        palabra = listaPalabras[0]
        number = palabra.length
        letrasPalabra = MutableList<String>(palabra.length) {i -> ""}
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

        val letra = btnPulsado.tag.toString()

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
                newGame()
            }
            builder.setNegativeButton("Salir") { dialog, which ->
                finish()
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
                newGame()
            }
            builder.setNegativeButton("Salir") { dialog, which ->
                finish()
            }
            builder.show()
        }
    }


}
