package org.list.archives.dummy

import org.list.archives.Email
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Email> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Email> = HashMap()

    fun addItem(item: Email) {
        ITEMS.add(item)
        ITEM_MAP.put(item.message_id, item)
    }

    fun getSize(): Int {
        return ITEMS.size
    }

}

var content = DummyContent()