package com.austindorsey.d20.model

interface CustomListCreator {
    fun buildDieList(): List<String>
}