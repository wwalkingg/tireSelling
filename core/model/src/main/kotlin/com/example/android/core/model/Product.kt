package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val isHot: Boolean
) {
    companion object {
        val fakeData = listOf(
            Product(1, "苹果", "https://source.unsplash.com/8WTR3uZMzGk", true),
            Product(2, "香蕉", "https://source.unsplash.com/ruSNkUs3vOU", false),
            Product(3, "橙子", "https://source.unsplash.com/ow1XZfd1Ic0", false),
            Product(4, "草莓", "https://source.unsplash.com/4zbwX9ZdGgQ", true),
            Product(5, "葡萄", "https://source.unsplash.com/iG2QWkRZcWk", false),
            Product(6, "樱桃", "https://source.unsplash.com/hO_4YdX9QAI", false),
            Product(7, "橙子", "https://source.unsplash.com/7PQo2PZo9Ng", true),
            Product(8, "蓝莓", "https://source.unsplash.com/TzrNSMoeEkY", false),
            Product(9, "哈密瓜", "https://source.unsplash.com/FzV7py-qCpo", false),
            Product(10, "柠檬", "https://source.unsplash.com/fEoGxL-1aOc", false),
            Product(11, "西瓜", "https://source.unsplash.com/-9t_2eOj-GM", true),
            Product(12, "菠萝", "https://source.unsplash.com/Fk3zL5Yg_KI", false),
            Product(13, "椰子", "https://source.unsplash.com/8UWrx9U6v-U", false),
            Product(14, "木瓜", "https://source.unsplash.com/7ohu8W0Py4M", false),
            Product(15, "荔枝", "https://source.unsplash.com/IpkrV1PUuAo", false),
            Product(16, "杏子", "https://source.unsplash.com/7UuPM_KXf2Q", false),
            Product(17, "桃子", "https://source.unsplash.com/y-kIvZM-N0k", true),
            Product(18, "梨子", "https://source.unsplash.com/5wOZ5HFnGgI", false),
            Product(19, "香椰", "https://source.unsplash.com/kL-JCrfiD50", false),
            Product(20, "樱桃", "https://source.unsplash.com/RG6fQV7E1Vo", false),
            Product(21, "红毛丹", "https://source.unsplash.com/L08C1bI0c3w", true),
            Product(22, "黄金瓜", "https://source.unsplash.com/gnBZRBvHkgE", false),
            Product(23, "甜瓜", "https://source.unsplash.com/95igzGtzZLw", false),
            Product(24, "柚子", "https://source.unsplash.com/sW8Sv5Pr5n0", false),
            Product(25, "莲雾", "https://source.unsplash.com/28-_HZZIKGA", false),
            Product(26, "樱桃", "https://source.unsplash.com/WcQ2CwVfHsA", false),
            Product(27, "葡萄柚", "https://source.unsplash.com/Yu46wJr-Z40", true),
            Product(28, "山竹", "https://source.unsplash.com/48qerZOQcJ8", false),
            Product(29, "龙眼", "https://source.unsplash.com/Z-J1EDJh1-Q", false),
            Product(30, "火龙果", "https://source.unsplash.com/BpjW9cLAcZ4", false)
        )
    }
}
