package net.azarquiel.ahorcadogame.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update

@Dao
interface EspanolDao {
    @Query("SELECT * from espanol")
    fun getAllPalabras(): List<Espanol>

    @Query("SELECT * from espanol where id = :paramid")
    fun getPalabraById(paramid:Int): Espanol

    @Insert
    fun insert(espanol: Espanol)

    @Query("DELETE FROM espanol WHERE id=:id")
    fun delete(id:Int)

    @Update
    fun update(espanol: Espanol)
}
