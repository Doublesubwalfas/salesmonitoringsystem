package com.example.salesmonitoringsystem.data


data class Store(
    val id: String = "",
    val name: String = "",
    val location: Location = Location(),
    val manager: Manager = Manager()
)

data class Location(
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val country: String = ""
)

data class Manager(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = ""
)