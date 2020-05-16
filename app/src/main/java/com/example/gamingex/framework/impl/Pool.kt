package com.example.gamingex.framework.impl


interface PoolFactory <T> {
    fun createObject(): T
}

class Pool <T>(val factory: PoolFactory<T>, val maxSize: Int) {
    val freeObjects = ArrayList<T>(maxSize)
    fun newObject(): T {
        return if (freeObjects.isEmpty()) factory.createObject()
        else freeObjects.removeAt(freeObjects.size-1)
    }

    fun free(obj: T) {
        if (freeObjects.size < maxSize) freeObjects.add(obj)
    }
}
