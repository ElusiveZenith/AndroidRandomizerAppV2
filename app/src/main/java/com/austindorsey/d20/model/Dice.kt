package com.austindorsey.d20.model

data class Dice (val values: List<String>) {
    val sides: Int
        get() = values.size

    fun roll(): String {
        return values.random()
    }

    fun manyRolls(rollTimes: Int): List<String> {
        val rolls = mutableListOf<String>()
        for (i in 1..rollTimes) {
            rolls.add(roll())
        }
        return rolls
    }
}