package com.bharath.swipeandroidassignment.data.common

/**
 * A sealed class representing the various states of a resource.
 * A resource can be in one of the following states: Success, Error, or Loading.
 *
 * @param T The type of data associated with the resource.
 * @property data The data associated with the resource. It can be null.
 * @property message A message describing the resource state. It can be null.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * Represents that user just had opened the app for first time / cleared the data.
     */


    class NotCached<T> : Resource<T>()


    /**
     * Represents a successful state of the resource.
     *
     * @param T The type of data associated with the resource.
     * @property data The data associated with the resource.
     */


    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents an error state of the resource.
     *
     * @param T The type of data associated with the resource.
     * @property message A message describing the error.
     * @property data The data associated with the resource, if any. It can be null.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state of the resource.
     *
     * @param T The type of data associated with the resource.
     * @property data The data associated with the resource, if any. It can be null.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
