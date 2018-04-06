package com.kotlinapp.computer.cafe.cafe


/**
 * Created by Computer on 18.02.2018.
 */
class Dish (
        override val id : Int,
        val name: String,
        val photo: String,
        val categoryId : Int,
        val price : Double,
        val weight : Int,
        var count : Int) : ListItem