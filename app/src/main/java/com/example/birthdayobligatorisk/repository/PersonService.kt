package com.example.birthdayobligatorisk.repository

import com.example.birthdayobligatorisk.models.Person
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface PersonService {

    @GET("Persons")
    fun getAllPersons(): Call<List<Person>>

    @GET("Persons/{id}")
    fun getPersonById(@Path("id") id: Int): Call<Person>

    @POST("Persons")
    fun addPerson(@Body person: Person): Call<Person>

    @DELETE("Persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>

    @PUT("Person/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>

}
