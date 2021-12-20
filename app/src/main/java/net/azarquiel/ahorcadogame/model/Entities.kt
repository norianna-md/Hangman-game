package net.azarquiel.ahorcadogame.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "espanol")
data class Espanol(@PrimaryKey(autoGenerate = true)
                    var id: Int,
                    var palabra:String,
                    var categoria:String)
