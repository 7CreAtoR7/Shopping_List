package ru.shop.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.shop.shoppinglist.R
import ru.shop.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEWTYPE_ENABLED -> R.layout.item_shop_enabled
            VIEWTYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val element = getItem(position)
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

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEWTYPE_ENABLED
        } else {
            VIEWTYPE_DISABLED
        }
    }

    companion object {
        const val VIEWTYPE_ENABLED = 100
        const val VIEWTYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 15
    }
}