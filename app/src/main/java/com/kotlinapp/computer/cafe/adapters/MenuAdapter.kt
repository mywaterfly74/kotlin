package com.kotlinapp.computer.cafe.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kotlinapp.computer.cafe.DataBaseWorker
import com.kotlinapp.computer.cafe.MainActivity
import com.kotlinapp.computer.cafe.R
import com.kotlinapp.computer.cafe.anko.MenuItemComponentCategory
//import com.kotlinapp.computer.cafe.anko.MenuItemComponentCategory
import com.kotlinapp.computer.cafe.anko.MenuItemComponentDish
import com.kotlinapp.computer.cafe.cafe.Category
import com.kotlinapp.computer.cafe.cafe.Dish
import com.kotlinapp.computer.cafe.cafe.ListItem
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import java.lang.Thread.sleep

class MenuAdapter(val activity: MainActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var showList : MutableList<ListItem> = mutableListOf<ListItem>()
    val downloader = DataBaseWorker(activity)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            0 -> {
                return ViewHolderCategory(MenuItemComponentCategory().createView(AnkoContext.create(activity, activity, false)))
            }
            else -> {
                return ViewHolderDish(MenuItemComponentDish().createView(AnkoContext.create(activity, activity, false)))
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        notifyItemRangeChanged(0, showList.size)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        val categories = downloader.getCategory()
        showList.addAll(categories)
        super.registerAdapterDataObserver(observer)
    }

    override fun getItemCount(): Int = showList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(this.getItemViewType(position)) {
            0 -> {
                val catHolder = holder as ViewHolderCategory
                val category : Category = showList[position] as Category
                holder.textName.text = category.name
                holder.cardViewCategory.setOnClickListener(View.OnClickListener {
                    val dishList : MutableList<Dish> = downloader.getDish(showList[position].id) as MutableList<Dish>
                    if ((showList[position] == showList.last()) || (showList[position+1] is Category)) {
                        showList.addAll(position+1, dishList)
                        notifyItemRangeInserted(position+1, dishList.size)

                    }
                    else {
                        for (i in 1..dishList.size) {
                            showList.removeAt(position + 1)
                        }
                        notifyItemRangeRemoved(position+1, dishList.size)
                    }
                })
            }
            1 -> {
                val first = holder as ViewHolderDish
                val dish : Dish = showList[position] as Dish
                holder.textName.text = dish.name
                holder.textPrice.text = "${dish.price} р. / ${dish.weight} гр."
                holder.textCount.text = "${dish.count}"
                setButtonsVisibility(position, holder)
                holder.buttonMinus.setOnClickListener(){
                    downloader.minusDish(showList[position].id)
                    dish.count -= 1
                    holder.textCount.text = "${dish.count}"
                    setButtonsVisibility(position, holder)
                }
                holder.buttonPlus.setOnClickListener(){
                    downloader.plusDish(showList[position].id)
                    dish.count += 1
                    holder.textCount.text = "${dish.count}"
                    setButtonsVisibility(position, holder)
                }
                holder.buttonAddToBasket.setOnClickListener() {
                    downloader.plusDish(showList[position].id)
                    dish.count += 1
                    holder.textCount.text = "${dish.count}"
                    setButtonsVisibility(position, holder)
                }
                Picasso.get()
                        .load(dish.photo)
                        .placeholder(R.drawable.fish)
                        .resize(960, 600)
                        //.networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.photo);
            }
        }
    }

    fun setButtonsVisibility(position: Int, holder : ViewHolderDish) {
        if ((showList[position] as Dish).count == 0) {
            holder.textCount.visibility = View.INVISIBLE
            holder.buttonMinus.visibility = View.INVISIBLE
            holder.buttonPlus.visibility = View.INVISIBLE
            holder.buttonAddToBasket.visibility = View.VISIBLE
        }
        else {
            holder.textCount.visibility = View.VISIBLE
            holder.buttonMinus.visibility = View.VISIBLE
            holder.buttonPlus.visibility = View.VISIBLE
            holder.buttonAddToBasket.visibility = View.INVISIBLE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (showList.get(position)) {
            is Category -> 0
            is Dish -> 1
            else -> 999
        }
    }

    class ViewHolderCategory(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewCategory : CardView = itemView.find<CardView>(R.id.cardViewCategory)
        val textName : TextView = itemView.find<TextView>(R.id.textCategoryName)
    }

    class ViewHolderDish(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constraintLayout : ConstraintLayout = itemView.find<ConstraintLayout>(R.id.constraintLayoutDishItem)
        val textName : TextView = itemView.find<TextView>(R.id.textViewDishName)
        val textPrice : TextView = itemView.find<TextView>(R.id.textViewDishPrice)
        val textCount : TextView = itemView.find<TextView>(R.id.textViewDishCount)
        val buttonPlus : Button = itemView.find<Button>(R.id.buttonPlus)
        val buttonMinus : Button = itemView.find<Button>(R.id.buttonMinus)
        val buttonAddToBasket : ImageButton = itemView.find<ImageButton>(R.id.buttonAddToBasket)
        val photo : ImageView = itemView.find<ImageView>(R.id.imageViewDish)
    }
}