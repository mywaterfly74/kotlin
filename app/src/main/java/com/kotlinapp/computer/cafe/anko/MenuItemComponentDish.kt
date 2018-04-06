package com.kotlinapp.computer.cafe.anko

import android.support.constraint.ConstraintLayout
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.kotlinapp.computer.cafe.MainActivity
import com.kotlinapp.computer.cafe.R
import com.kotlinapp.computer.cafe.cafe.Category
import com.kotlinapp.computer.cafe.cafe.Dish
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout


class MenuItemComponentDish : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        linearLayout {
            cardView {
                constraintLayout {
                    id = R.id.constraintLayoutDishItem
                    padding = dip(16)

                    val dishPhoto = imageView {
                        id = R.id.imageViewDish
                        setImageResource(R.drawable.fish)
                        adjustViewBounds = true
                    }.lparams(width = wrapContent, height = wrapContent) {
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    }

                    val dishName = textView {
                        id = R.id.textViewDishName
                        textColor = resources.getColor(R.color.colorRed)
                        textSize = 22f //sp
                    }.lparams {
                        topMargin = dip(8)
                        startToStart = R.id.imageViewDish
                        topToBottom = R.id.imageViewDish
                    }

                    val dishPriceAndWeight = textView {
                        id = R.id.textViewDishPrice
                    }.lparams {
                        topMargin = dip(8)
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        startToStart = R.id.textViewDishName
                        topToBottom = R.id.textViewDishName
                    }

                    val buttonPlus = button {
                        id = R.id.buttonPlus
                        gravity = Gravity.CENTER
                        backgroundColor = resources.getColor(R.color.colorRed)
                        text = "+"
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textColor = resources.getColor(R.color.colorWhite)
                        textSize = 28f //sp
                        padding = 0
                    }.lparams(width = dip(46), height = dip(46)) {
                        topMargin = dip(16)
                        endToEnd = R.id.imageViewDish
                        topToBottom = R.id.imageViewDish
                    }

                    val buttonMinus = button {
                        id = R.id.buttonMinus
                        backgroundColor = resources.getColor(R.color.colorRed)
                        text = "–"
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textColor = resources.getColor(R.color.colorWhite)
                        textSize = 28f //sp
                        padding = 0
                    }.lparams(width = dip(46), height = dip(46)) {
                        marginEnd = dip(16)
                        topMargin = dip(16)
                        endToStart = R.id.textViewDishCount
                        topToBottom = R.id.imageViewDish
                    }

                    val buttonCart = imageButton {
                        id = R.id.buttonAddToBasket
                        imageResource = R.drawable.ic_shopping_cart_24dp
                        backgroundColor = resources.getColor(R.color.colorRed)
                    }.lparams(width = dip(46), height = dip(46)) {
                        topMargin = dip(16)
                        endToEnd = R.id.imageViewDish
                        topToBottom = R.id.imageViewDish
                    }

                    val dishCount = textView {
                        id = R.id.textViewDishCount
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textSize = 28f //sp
                    }.lparams(width = dip(32), height = dip(46)) {
                        marginEnd = dip(16)
                        topMargin = dip(16)
                        endToStart = R.id.buttonPlus
                        topToBottom = R.id.imageViewDish

                    }
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent){
                setMargins(dip(10), dip(10), dip(10), dip(0))
            }
        }
    }
}