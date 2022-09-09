package com.example.coffeeroom.ui.coffeeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee

class CoffeeListAdapter : ListAdapter<Coffee, CoffeeListViewHolder>(
    DIFF_CALLBACK
) {

    // add clickListener
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(coffee: Coffee)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeListViewHolder =
        CoffeeListViewHolder(parent)

    override fun onBindViewHolder(holder: CoffeeListViewHolder, position: Int) {
        holder.textView.text = "${getItem(position).title}"
        holder.layout.setOnClickListener {
            listener.onClick(coffee = getItem(position))
        }
    }
}

class CoffeeListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_coffee, parent, false)
) {
    val textView: TextView = itemView.findViewById(R.id.textview_coffee_item)
    val layout: CardView = itemView.findViewById(R.id.cardview_coffee)
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Coffee>() {

    override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem == newItem
    }

}