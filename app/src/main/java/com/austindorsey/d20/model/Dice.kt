package com.austindorsey.d20.model

data class Dice (val values: MutableList<String>) {
    val sides: Int
        get() = values.size

    fun roll(): String {
        return values.random()
    }

    fun manyRolls(rollTimes: Int): MutableList<String> {
        var rolls = mutableListOf<String>()
        for (i in 1..rollTimes) {
            rolls.add(roll())
        }
        return rolls
    }
}