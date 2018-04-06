package com.kotlinapp.computer.cafe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.kotlinapp.computer.cafe.R.attr.layoutManager
import com.kotlinapp.computer.cafe.adapters.MenuAdapter
import com.kotlinapp.computer.cafe.anko.MainActivityUI
import com.kotlinapp.computer.cafe.cafe.Category
import com.kotlinapp.computer.cafe.cafe.Dish
import com.kotlinapp.computer.cafe.cafe.ListItem
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.setContentView


class MainActivity : AppCompatActivity() {
    val downloader = DataBaseWorker(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        val activity = this
        super.onCreate(savedInstanceState)
        var menuAdapter = MenuAdapter(activity)
        val ui = MainActivityUI(menuAdapter)
        ui.setContentView(activity)
        launch(UI) {
            try {
                if (downloader.getMenuVersion() != getDBVersionFromServer().await()) {
                    downloader.updateMenu(getMenuFromServer().await())
                }
            }
            catch(exception : Exception) {
                Toast.makeText(activity, "Нет соединения с сервером", Toast.LENGTH_LONG).show()
            }
            menuAdapter = MenuAdapter(activity)
            ui.changeAdapter(menuAdapter)
            ui.setContentView(activity)
            ui.hideProgress()

        }
    }
}
