package com.bharath.swipeandroidassignment.helpers

import androidx.annotation.Keep
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.data.entity.products.Products
import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem

/**
 * Extensive functions required to map the data
 * and reduce the amount of code.
 */
@Keep
fun Products.toEntity() = map {
    ProductEntity(
        image = it.image,
        price = it.price,
        productName = it.product_name,
        productType = it.product_type,
        tax = it.tax
    )
}

fun String.toIndianNumberSystem(): String {
    if (this.isEmpty()) return ""
    if (this.length <= 3) return this
    val source = this.replace(",", "")
    var decimal: Boolean
    val numberString = if (source.contains(".")) {
        decimal = true
        source.substringBefore(".")
    } else {
        decimal = false
        source
    }
    val stringBuilder = StringBuilder()

    var count = 0
    for (i in numberString.length - 1 downTo 0) {
        stringBuilder.append(numberString[i])
        count++
        if (count == 3 && i != 0) {
            stringBuilder.append(',')
            count = 1
        }
    }
    val ans: StringBuilder = stringBuilder.reverse()
    if (decimal) {
        ans.append(".")
            .append(source.substringAfter("."))
    }
    return ans.toString()
}

fun main() {
    val str = "9000000"
    println(str.toIndianNumberSystem())
}

fun List<ProductsItem>.toEntity() = map {
    ProductEntity(
        image = it.image,
        price = it.price, productName = it.product_name,
        productType = it.product_type,
        tax = it.tax
    )
}