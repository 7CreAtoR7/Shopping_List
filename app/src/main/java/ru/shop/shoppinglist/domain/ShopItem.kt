package ru.shop.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID // астоящий id еще не установлен, указывается при добавлении объекта в коллекцию
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
