package com.example.appsmovie.Buffet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.R
import com.example.core.UseCase_Repository.Tickets.BuffetItem
import kotlin.io.path.name

    class BuffetAdapter(
    private val addItem: (BuffetItem) -> Unit,
    private val removeItem: (BuffetItem) -> Unit
) : RecyclerView.Adapter<BuffetAdapter.BuffetViewHolder>() {

    private var menuItems = listOf<BuffetItem>()

    fun setData(newItems: List<BuffetItem>) {
        this.menuItems = newItems
        notifyDataSetChanged()
    }

    inner class BuffetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val ivProductImage: ImageView = itemView.findViewById(R.id.iv_product_image)
        private val tvMenuName: TextView = itemView.findViewById(R.id.tv_menu_name)
        private val tvMenuPrice: TextView = itemView.findViewById(R.id.tv_menu_price)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tv_quantity)
        private val btnAdd: ImageButton = itemView.findViewById(R.id.btn_add)
        private val btnSubtract: ImageButton = itemView.findViewById(R.id.btn_subtract)

        fun bind(item: BuffetItem) {
            tvMenuName.text = item.name
            tvMenuPrice.text = "$ ${item.price}" // Sesuaikan format harga
            tvQuantity.text = item.quantity.toString()

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.item)
                .into(ivProductImage)


            btnAdd.setOnClickListener { addItem(item) }
            btnSubtract.setOnClickListener { removeItem(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuffetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itembuffet, parent, false)
        return BuffetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuffetViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size
}
