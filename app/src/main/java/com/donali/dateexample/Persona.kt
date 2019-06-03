package com.donali.dateexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "persona")
data class Persona (
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name="fecha")
    val fecha:Date
){
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}