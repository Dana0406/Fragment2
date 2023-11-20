package com.example.fragments2.model

import com.github.javafaker.Faker
import java.util.*

typealias UsersListener = (users: List<User>) -> Unit

class UsersService {
    private var users: MutableList<User> = mutableListOf<User>()

    private val listeners:MutableList<UsersListener> = mutableListOf<UsersListener>()

    init{
        val locale = Locale("ru")
        val faker = Faker(locale)
        users = (1..100).map { User(
            id = it.toLong(),
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            phoneNumber = faker.phoneNumber().phoneNumber(),
            photo = faker.internet().avatar()
        ) }.toMutableList()
    }

    fun addListener(listener: UsersListener){
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(users)}
    }

}