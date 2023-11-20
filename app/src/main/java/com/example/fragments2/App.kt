package com.example.fragments2

import android.app.Application
import com.example.fragments2.model.UsersService

class App: Application() {
    val usersService = UsersService()
}