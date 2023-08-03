package com.example.userssp

// el data le da atributos especiales a la clase
data class User(val id: Long, var name: String, var lastName: String, var url: String) {

    fun getFullName(): String = "$lastName, $name"
}