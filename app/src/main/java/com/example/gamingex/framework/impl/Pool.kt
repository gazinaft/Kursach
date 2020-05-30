package com.example.gamingex.framework.impl


interface PoolFactory <T> {
    fun createObject(): T
}

//this is a pool class
//made to store and reuse instances
//mainly used in touch and keyboard handling
//because of the amount of events to optimize it for the GC
class Pool <T>(val factory: PoolFactory<T>, val maxSize: Int) {
    val freeObjects = ArrayList<T>(maxSize)
    fun newObject(): T {
        return if (freeObjects.isEmpty()) factory.createObject()
        else freeObjects.removeAt(freeObjects.size-1)
    }

    //returns an object to the pool
    fun free(obj: T) {
        if (freeObjects.size < maxSize) freeObjects.add(obj)
    }
}
