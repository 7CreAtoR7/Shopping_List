package ru.shop.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.shop.shoppinglist.domain.ShopItem
import ru.shop.shoppinglist.domain.ShopListRepository
import java.util.TreeSet
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    // синглтон - где бы мы не обратились к объекту, это будет один и тот же экземпляр во
    // избежании ситуации, когда на первом экране один репозиторий, а на втором - другой репозиторий

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>(Comparator { o1, o2 -> o1.id.compareTo(o2.id)})

    private var autoIncrementId = 0

    init {
        for (i in 0 until 1000) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList() // обновляем LD после добавления элемента
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        // на вход изменённый объект, id которого совпадает со старым
        shopList.remove(getShopItem(shopItem.id))
        addShopItem(shopItem) // в методе add уже обновляется LD
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }


    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateList() {
        // обновляем LD (вставляем копию списка)
        shopListLiveData.value = shopList.toList()
    }
}