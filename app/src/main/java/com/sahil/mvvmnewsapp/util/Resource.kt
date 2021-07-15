package com.sahil.mvvmnewsapp.util

//sealed class - it is kind of abstract class ,but we can define that which classes are allowed to
// inherit from that resource class
sealed class Resource<T>(
    val data :T? = null, // body of response
    val message :String? = null //message of reponce
) {
    // only these 3 classes are allowed to inherit from that resource
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String,data: T? = null ):Resource<T>(data, message)
    class Loading<T>:Resource<T>()
}