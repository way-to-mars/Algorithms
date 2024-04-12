package examples

import printLog

fun main() {
    val stack: Stack<Int> = UnsafeStack(100)
    (1..30).forEach { stack.push(it * it) }
    stack.printLog()
}

interface Stack<E> {
    val size: Int
    val isEmpty: Boolean
        get() = size == 0

    fun push(element: E)
    fun pop(): E?
    fun top(): E?
}

/**
 *  Реализация небезопасного стека.
 *  Имеет фиксированный размер хранилища и не проверяет его переполнение
 */
class UnsafeStack<E>(initialSize: Int) : Stack<E> {
    private val storage = Array<Any?>(initialSize) { null }
    private var _size = 0
    override val size
        get() = _size

    override fun pop(): E? {
        if (_size == 0) return null
        @Suppress("UNCHECKED_CAST")
        return storage[--_size] as E
    }

    override fun top(): E? {
        if (_size == 0) return null
        @Suppress("UNCHECKED_CAST")
        return storage[_size - 1] as E
    }

    override fun push(element: E) {
        storage[_size++] = element
    }

    override fun toString(): String {
        if (size == 0) return "0: [empty stack]"
        if (size < 20) return storage.asSequence().take(size)
            .joinToString(separator = ", ", prefix = "${size}: [", postfix = "]")
        return storage.take(9).joinToString(separator = ", ", prefix = "${size}: [", postfix = ", ... ") +
                storage.slice(size - 9..<size).joinToString(separator = ", ", postfix = "]")
    }
}