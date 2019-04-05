package krypto

typealias Coords = Pair<Int, Int>

fun <K, V> HashMap<K, V>.putChecked(key: K, v: V): V? {
    return if (!this.containsValue(v))
        this.put(key, v)
    else
        null

}

fun <K, V> HashMap<K, V>.getKey(v: V): K? {
    if (this.containsValue(v)) {
        for ((key, find) in this) {
            if (v == find) {
                return key
            }
        }
    }
    return null
}

infix operator fun <T : Comparable<T>> ClosedRange<T>.contains(n: T): Boolean {
    return this.start <= n && this.endInclusive >= n
}

class SquareKt(keyWord: String) {
    private val key = HashMap<Char, Coords>()

    init {
        val temp = keyWord.toCharArray()
        var i = 0
        var j = 0
        for (c in temp) {
            if (!key.containsKey(c)) {
                key.putChecked(c, Coords(j, i))
                if (i != 5) i++
                else {
                    i = 0; j++
                }
            }
        }
        for (c: Char in 'a'..'z')
            if (!key.containsKey(c)) {
                key.putChecked(c, Coords(j, i))
                if (i != 5) i++
                else {
                    i = 0; j++
                }
            }
        for (c: Char in '0'..'9')
            if (!key.containsKey(c)) {
                key.putChecked(c, Coords(j, i))
                if (i != 5) i++
                else {
                    i = 0; j++
                }
            }
    }

    fun code(decoded: String): String {
        val temp = ArrayList<Char>()
        var coded = ""
        for (c in decoded.toCharArray()) {
            when {
                temp.size > 0 && c == temp.last() -> {
                    temp.add('x')
                    temp.add(c)
                }
                '0'..'9' contains c -> temp.add(c)
                'a'..'z' contains c -> temp.add(c)
            }
        }
        if (temp.size % 2 != 0) temp.add('x')
        run {
            for (i in temp.indices step 2) {
                val cl1 = key[temp[i]]
                val cl2 = key[temp[i + 1]]
                temp.removeAt(i)
                temp.removeAt(i)
                if (cl1 != null && cl2 != null)
                    when {
                        cl1.second == cl2.second -> {
                            temp.add(i, key.getKey(Coords((cl1.first + 1) % 6, cl1.second))!!)
                            temp.add(i + 1, key.getKey(Coords((cl2.first + 1) % 6, cl2.second))!!)
                        }
                        cl1.first == cl2.first -> {
                            temp.add(i, key.getKey(Coords(cl1.first, (cl1.second + 1) % 6))!!)
                            temp.add(i + 1, key.getKey(Coords(cl2.first, (cl2.second + 1) % 6))!!)
                        }
                        else -> {
                            temp.add(i, key.getKey(Coords(cl1.first, cl2.second))!!)
                            temp.add(i + 1, key.getKey(Coords(cl2.first, cl1.second))!!)
                        }
                    }
            }
        }
        for (c in temp) {
            coded += Character.toUpperCase(c)
        }
        return coded
    }

    fun decode(coded: String): String {
        val temp = ArrayList<Char>()
        var decoded = ""
        for (c in coded.toCharArray())
            temp.add(Character.toLowerCase(c))
        run {
            for (i in temp.indices step 2) {
                val cl1 = key[temp[i]]
                val cl2 = key[temp[i + 1]]
                temp.removeAt(i)
                temp.removeAt(i)
                if (cl1 != null && cl2 != null)
                    when {
                        cl1.second == cl2.second -> {
                            temp.add(i, key.getKey(Coords((cl1.first + 5) % 6, cl1.second))!!)
                            temp.add(i + 1, key.getKey(Coords((cl2.first + 5) % 6, cl2.second))!!)
                        }
                        cl1.first == cl2.first -> {
                            temp.add(i, key.getKey(Coords(cl1.first, (cl1.second + 5) % 6))!!)
                            temp.add(i + 1, key.getKey(Coords(cl2.first, (cl2.second + 5) % 6))!!)
                        }
                        else -> {
                            temp.add(i, key.getKey(Coords(cl1.first, cl2.second))!!)
                            temp.add(i + 1, key.getKey(Coords(cl2.first, cl1.second))!!)
                        }
                    }
            }
        }
        for (i in temp.indices) {
            if (temp[i] == 'x' && (i > 0 && i < temp.size - 1 && temp[i - 1] == temp[i + 1] || i == temp.size - 1))
            else
                decoded += temp[i]
        }
        return decoded
    }
}