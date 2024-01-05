package com.reyaly.reyalyhealthtracker.storage

import kotlin.reflect.KClass

fun <T : Any> mapToObject(map: MutableMap<String, Any>, cl: KClass<T>) : T {
    //Get default constructor
    val constructor = cl.constructors.first()

    //Map constructor parameters to map values
    val args = constructor
        .parameters
        .map { it to map.get(it.name) }
        .toMap()

    //return object from constructor call
    return constructor.callBy(args)
}