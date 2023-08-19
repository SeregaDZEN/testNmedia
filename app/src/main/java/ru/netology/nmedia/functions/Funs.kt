package ru.netology.nmedia.functions


fun reduceNumber(count: Int): String {


    val oneThousands = count / 1000
    val hundredThousands = (count % 1_000_000) / 100_000
    val oneHundreds = (count % 1000) / 100
    val oneMillion = (count / 1_000_000)

    when {
        count < 1000 -> return "$count"
        count in 1000..9_999 -> {


            return if (count % 1000 in 1..99) {
                "${oneThousands}k"
            } else {
                return "${oneThousands}.${oneHundreds}k"
            }
        }

        count in 10_000..999_999 -> {
            return "${oneThousands}k"
        }

        else -> {
            return if (count % 1_000_000 in 0..99_999) {
                "${oneMillion}M"
            } else {
                "${oneMillion}.${hundredThousands}M"
            }
        }


    }

}
