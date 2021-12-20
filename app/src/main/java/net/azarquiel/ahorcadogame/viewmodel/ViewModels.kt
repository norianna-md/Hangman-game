package net.azarquiel.ahorcadogame.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.ahorcadogame.model.Espanol
import net.azarquiel.ahorcadogame.model.EspanolRepository

class EspanolViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: EspanolRepository = EspanolRepository(application)

    fun getAllPalabras(): List<Espanol>{
        return repository.getAllPalabras()
    }

    fun getPalabraById(paramid:Int): Espanol {
        return repository.getPalabraById(paramid)
    }

    fun insert(espanol: Espanol) {
        GlobalScope.launch() {
            repository.insert(espanol)
            launch(Dispatchers.Main) {
            }
        }
    }

    fun delete(id: Int) {
        GlobalScope.launch() {
            repository.delete(id)
            launch(Dispatchers.Main) {
            }
        }
    }

    fun update(espanol: Espanol) {
        GlobalScope.launch() {
            repository.update(espanol)
            launch(Dispatchers.Main) {
            }
        }
    }
}
