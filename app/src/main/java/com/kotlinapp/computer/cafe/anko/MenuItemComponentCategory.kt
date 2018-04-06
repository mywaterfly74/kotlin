package com.kotlinapp.computer.cafe.anko

import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.kotlinapp.computer.cafe.MainActivity
import com.kotlinapp.computer.cafe.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint


class MenuItemComponentCategory : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        frameLayout {
            lparams(width = matchParent)
            cardView {
                id = R.id.cardViewCategory
                constraintLayout {
                    val name = textView {
                        id = R.id.textCategoryName
                        textSize = 24f
                    }.lparams(width = matchParent, height = wrapContent) {
                        margin = dip(16)
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent) {
                setMargins(dip(10), dip(10), dip(10), dip(0))
            }
        }
    }
}