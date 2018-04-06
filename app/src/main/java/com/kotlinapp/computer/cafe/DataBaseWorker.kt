package com.kotlinapp.computer.cafe

import android.content.Context
import com.kotlinapp.computer.cafe.cafe.Category
import com.kotlinapp.computer.cafe.cafe.MenuVersion
import com.kotlinapp.computer.cafe.cafe.Dish
import com.kotlinapp.computer.cafe.cafe.Menu

import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.*
import org.jetbrains.anko.doAsync


class DataBaseWorker (context: Context) {
    val database = DBHelper(context)

    fun insertDish(id: Int, name: String, price: Double, weight: Int, categID: Int, photo: String) = database.doAsync {
        bg {
            database.writableDatabase.insert("dish",
                    "_id" to id,
                    "name" to name,
                    "price" to price,
                    "weight" to weight,
                    "category_id" to categID,
                    "photo" to photo,
                    "count" to 0
            )
        }
    }

    fun insertCategory(id: Int, name: String) = database.doAsync {
        bg {
            database.writableDatabase.insert("category",
                    "_id" to id,
                    "name" to name
            )
        }
    }

    fun getCategory(): List<Category> {
        val rowParser = classParser<Category>()
        val cat = database.writableDatabase.select("category").exec {
            parseList(rowParser)
        }
        return cat
    }

    fun getDish(categID: Int): List<Dish> {
        val rowParser = classParser<Dish>()
        val dish = database.writableDatabase.select("dish")
                .whereArgs("category_id = $categID")
                .exec {
                    parseList(rowParser)
                }
        return dish
    }

    fun plusDish(dishID: Int) {
        val rowParser = classParser<Dish>()
        val dish = database.readableDatabase.select("dish")
                .whereArgs("_id = $dishID")
                .exec {
                    parseSingle(rowParser)
                }
        database.writableDatabase.update("dish", "count" to "${(dish.count) + 1}")
                .whereArgs("_id = {dishId}", "dishId" to dishID).exec()
    }

    fun minusDish(dishID: Int) {
        val rowParser = classParser<Dish>()
        val dish = database.readableDatabase.select("dish")
                .whereArgs("_id = $dishID")
                .exec {
                    parseSingle(rowParser)
                }
        database.writableDatabase.update("dish", "count" to "${(dish.count) - 1}")
                .whereArgs("_id = {dishId}", "dishId" to dishID).exec()
    }

    fun getMenuVersion(): Int {
        val rowParser = classParser<MenuVersion>()
        val menuVersion = database.writableDatabase.select("menu_version")
                .whereArgs("_id = 1")
                .exec {
                    parseList(rowParser)
                }
        return menuVersion[0].version
    }

    fun updateMenu(menu : Menu) {
        database.writableDatabase.delete("dish", whereClause = "_id > -1")
        database.writableDatabase.delete("category", whereClause = "_id > -1")
        database.writableDatabase.delete("menu_version", whereClause = "_id > -1")
        for (dish in menu.dishs) {
            insertDish(dish.id, dish.name, dish.price, dish.weight, dish.categoryId, dish.photo)
        }
        for (category in menu.categories) {
            insertCategory(category.id, category.name)
        }
        database.writableDatabase.insert("menu_version",
                "version" to menu.menuVersion
        )
    }
}