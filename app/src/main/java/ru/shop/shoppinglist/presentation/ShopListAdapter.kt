package ru.shop.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.shop.shoppinglist.R
import ru.shop.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var count = 0

    var shopList = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(shopList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        //Log.d("TestOnCreate", "onCreateViewHolder count: ${++count}")
        val layout = when (viewType) {
            VIEWTYPE_ENABLED -> R.layout.item_shop_enabled
            VIEWTYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder count: ${++count}")
        val element = shopList[position]
        holder.tvName.text = element.name
        holder.tvCount.text = element.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(element)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(element)
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enabled) {
            VIEWTYPE_ENABLED
        } else {
            VIEWTYPE_DISABLED
        }
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    companion object {
        const val VIEWTYPE_ENABLED = 100
        const val VIEWTYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 15
    }
}