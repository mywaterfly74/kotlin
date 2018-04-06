package com.kotlinapp.computer.cafe

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import com.kotlinapp.computer.cafe.cafe.Category
import com.kotlinapp.computer.cafe.cafe.Dish
import com.kotlinapp.computer.cafe.cafe.Menu
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext


fun getDBVersionFromServer(
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Int> = async(coroutineContext) {
    val httpClient = OkHttpClient()
    val body = FormBody.Builder()
            .add("query", "GetDBVersion")
            .build()
    val request = Request.Builder()
            .url("$protocol://$ip_address:$port$path")
            .post(body)
            .build()
    httpClient.newCall(request).execute().use {
        Gson().fromJson(it.body()!!.string(), Int::class.java)
    }
}

fun getCategoriesFromServer(
        coroutineContext: CoroutineContext = CommonPool
): Deferred<List<Category>> = async(coroutineContext) {
    val httpClient = OkHttpClient()
    val body = FormBody.Builder()
            .add("query", "GetCategories")
            .build()
    val request = Request.Builder()
            .url("$protocol://$ip_address:$port$path")
            .post(body)
            .build()
    httpClient.newCall(request).execute().use {
        Gson().fromJson(it.body()!!.string(), listOf<Category>()::class.java)
    }
}

fun getDishsFromServer(
        coroutineContext: CoroutineContext = CommonPool
): Deferred<List<Dish>> = async(coroutineContext) {
    val httpClient = OkHttpClient()
    val body = FormBody.Builder()
            .add("query", "GetDishs")
            .build()
    val request = Request.Builder()
            .url("$protocol://$ip_address:$port$path")
            .post(body)
            .build()
    httpClient.newCall(request).execute().use {
        Gson().fromJson(it.body()!!.string(), listOf<Dish>()::class.java)
    }
}

fun getMenuFromServer(
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Menu> = async(coroutineContext) {
    val httpClient = OkHttpClient()
    val body = FormBody.Builder()
            .add("query", "GetMenu")
            .build()
    val request = Request.Builder()
            .url("$protocol://$ip_address:$port$path")
            .post(body)
            .build()
    httpClient.newCall(request).execute().use {
        val resp = it.body()!!.string()
        Log.i(ContentValues.TAG, resp)
        Gson().fromJson(resp, Menu::class.java)
    }
}