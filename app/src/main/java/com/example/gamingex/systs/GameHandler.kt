package com.example.gamingex.systs

abstract class GameHandler <T> (initializer: Collection<T>)  {
    val listeners: ArrayList<T> = ArrayList<T>()

    init {
        listeners.addAll(initializer)
    }

    fun addListener(value: T) {
        listeners.add(value)
    }

    fun addListeners(list: Collection<T>) {
        listeners.addAll(list)
    }

    abstract fun handle()

    fun remove(value: T) {
        listeners.remove(value)
    }


}