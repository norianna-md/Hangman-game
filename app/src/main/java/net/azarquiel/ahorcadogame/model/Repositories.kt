package net.azarquiel.ahorcadogame.model

import android.app.Application
import androidx.lifecycle.LiveData

class EspanolRepository(application: Application) {

    val espanolDao = PalabrasDB.getDatabase(application)!!.espanolDao()
    // select
    fun getAllPalabras(): List<Espanol> {
        return espanolDao.getAllPalabras()
    }
    fun getPalabraById(paramid:Int): Espanol {
        return espanolDao.getPalabraById(paramid)
    }
    // insert
    fun insert(espanol: Espanol) {
        espanolDao.insert(espanol)
    }
    // delete
    fun delete(id:Int) {
        espanolDao.delete(id)
    }
    // update
    fun update(espanol: Espanol) {
        espanolDao.update(espanol)
    }
}
