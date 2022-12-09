package ru.shop.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import ru.shop.shoppinglist.data.ShopListRepositoryImpl
import ru.shop.shoppinglist.domain.DeleteShopItemUseCase
import ru.shop.shoppinglist.domain.EditShopItemUseCase
import ru.shop.shoppinglist.domain.GetShopListUseCase
import ru.shop.shoppinglist.domain.ShopItem


class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    // presentation слой знает о существовании data-слоя (ShopListRepositoryImpl),
    // чего быть не должно -> в дальнейшем исправлю, применяя DI

    // в дальнейшем реализую через инъекцию зависимостей в конструкторе
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList() // LiveData<List<ShopItem>>

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) { // изменение только статуса
        val newItem =  shopItem.copy(enabled = !shopItem.enabled)
        // в копию устанавливаем статус противоположный
        editShopItemUseCase.editShopItem(newItem)
    }
}