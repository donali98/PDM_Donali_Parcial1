package com.donali.dateexample

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface PersonaDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(persona: Persona):Long

    @Query("select * from persona where id = :id")
    fun getOne(id:Long): LiveData<List<Persona>>

    @Query("select * from persona")
    fun getAll():LiveData<List<Persona>>


    @Query("delete from persona")
    suspend fun deleteAll()
}