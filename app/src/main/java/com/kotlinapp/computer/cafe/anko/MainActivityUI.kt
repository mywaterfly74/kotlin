package com.kotlinapp.computer.cafe.anko

import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.kotlinapp.computer.cafe.DataBaseWorker
import com.kotlinapp.computer.cafe.MainActivity
import com.kotlinapp.computer.cafe.R
import com.kotlinapp.computer.cafe.adapters.MenuAdapter
import com.mikepenz.materialdrawer.DrawerBuilder
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import com.mikepenz.materialdrawer.Drawer
import org.jetbrains.anko.appcompat.v7.actionBarContainer
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
//import org.jetbrains.anko.design.floatingActionButton
import com.github.clans.fab.FloatingActionButton
import org.jetbrains.anko.design.floatingActionButton
import java.lang.reflect.Array.set


/**
 * Created by Computer on 18.02.2018.
 */
class MainActivityUI(var menuAdapter : MenuAdapter) : AnkoComponent<MainActivity> {
    lateinit var mainActivityProgressBar : ProgressBar
    lateinit var whiteImage : ImageView
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        constraintLayout {
            lparams(width = matchParent, height = matchParent)
            toolbar {
                id = R.id.toolbar_main
                textView() {
                    setShadowLayer(7f, 7f, 7f, resources.getColor(R.color.colorGrey))
                    text = "Кафешка"
                    textColor = resources.getColor(R.color.colorWhite)
                    textSize = 25f
                }.lparams(){
                }
                backgroundColor = resources.getColor(R.color.colorRed)
                elevation = dip(4).toFloat()

            }.lparams(width = dip(0), height = wrapContent) {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            }

            recyclerView {
                id = R.id.menuView
                val orientation = LinearLayoutManager.VERTICAL
                layoutManager = LinearLayoutManager(context, orientation, false)
                overScrollMode = View.OVER_SCROLL_NEVER
                adapter = menuAdapter
            }.lparams(width = matchConstraint, height = matchParent) {
                //bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToBottom = R.id.toolbar_main
            }
            whiteImage = imageView {
                id = R.id.mainActivityWhite
                alpha = 0.8f
                scaleType = ImageView.ScaleType.CENTER_CROP
                imageResource = R.drawable.white
            }
            mainActivityProgressBar = progressBar {
            }.lparams(wrapContent, wrapContent) {
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }
    }
    fun hideProgress() {
        mainActivityProgressBar.visibility = View.INVISIBLE;
        whiteImage.visibility = View.INVISIBLE

    }
    fun changeAdapter(adapter: MenuAdapter) {
        menuAdapter = adapter
    }
}