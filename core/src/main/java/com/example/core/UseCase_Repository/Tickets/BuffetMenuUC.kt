package com.example.core.UseCase_Repository.Tickets

import javax.inject.Inject

class BuffetMenuUC @Inject constructor() {

    operator fun invoke(): List<BuffetItem> {
        return listOf(
            BuffetItem(
                1,
                "Large Menu",
                "Large Popcorn\nLarge Coco Cola (400 ml.)",
                30.0,
                "https://media.istockphoto.com/id/681903568/photo/popcorn-in-box-with-cola.jpg?s=612x612&w=0&k=20&c=0rXGh6COImJ8iYpv99Yt2dQOyMneVw_rJw6QwsZPrh4="
            ),
            BuffetItem(
                2,
                "Medium Menu",
                "Medium Popcorn\nMedium Coco Cola (330 ml.)",
                20.0,
                "https://media.istockphoto.com/id/681903568/photo/popcorn-in-box-with-cola.jpg?s=612x612&w=0&k=20&c=0rXGh6COImJ8iYpv99Yt2dQOyMneVw_rJw6QwsZPrh4="
            ),
            BuffetItem(
                3,
                "Small Menu",
                "Small Popcorn\nSmall Coco Cola (250 ml.)",
                15.0,
                "https://media.istockphoto.com/id/681903568/photo/popcorn-in-box-with-cola.jpg?s=612x612&w=0&k=20&c=0rXGh6COImJ8iYpv99Yt2dQOyMneVw_rJw6QwsZPrh4="
            )
        )
    }
}