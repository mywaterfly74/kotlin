package com.kotlinapp.computer.cafe

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


class DBHelper(context: Context) : ManagedSQLiteOpenHelper(context, "cafe_db") {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("category", true,
                "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "name" to TEXT + UNIQUE
                )
        db?.createTable("dish", true,
                "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "name" to TEXT + UNIQUE,
                "photo" to TEXT,
                "category_id" to INTEGER,
                "price" to REAL,
                "weight" to INTEGER,
                "count" to INTEGER,
                FOREIGN_KEY("category_id", "category", "_id")
                )
        db?.createTable("menu_version", true,
                "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "version" to INTEGER)
        db?.insert("menu_version", "version" to 0)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("dish")
        db?.dropTable("category")
        onCreate(db)
    }

}