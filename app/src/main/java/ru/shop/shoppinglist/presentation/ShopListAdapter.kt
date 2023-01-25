package ru.shop.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.shop.shoppinglist.R
import ru.shop.shoppinglist.databinding.ItemShopDisabledBinding
import ru.shop.shoppinglist.databinding.ItemShopEnabledBinding
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
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding) // ViewHolder принимает binding в конструкторе

    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val element = getItem(position)
        val binding = holder.binding

        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(element)
            true
        }

        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(element)
        }
        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.tvName.text = element.name
                binding.tvCount.text = element.count.toString()
            }
            is ItemShopEnabledBinding -> {
                binding.tvName.text = element.name
                binding.tvCount.text = element.count.toString()
            }
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