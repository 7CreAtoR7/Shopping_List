package ru.shop.shoppinglist.data

import ru.shop.shoppinglist.domain.ShopItem
import ru.shop.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    // синглтон - где бы мы не обратились к объекту, это будет один и тот же экземпляр во
    // избежании ситуации, когда на первом экране один репозиторий, а на втором - другой репозиторий

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        // на вход изменённый объект, id которого совпадает со старым
        shopList.remove(getShopItem(shopItem.id))
        shopList.add(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        // возвращается копия, чтобы при редактировании коллекции исходная не изменялась
        return shopList.toList()
    }
}