package examples

import printLog

fun main() {
    val stack: Stack<Int> = UnsafeStack(100)
    stack.printLog()
    stack.isEmpty.printLog()
    stack.push(1)
    stack.push(2)
    stack.push(3)
    stack.printLog()
    stack.isEmpty.printLog()
    stack.pop().printLog()
    stack.pop().printLog()
    stack.pop().printLog()
    stack.printLog()
    stack.isEmpty.printLog()
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
    override var size: Int = 0
        private set

    override fun pop(): E? {
        if (size == 0) return null
        @Suppress("UNCHECKED_CAST")
        return storage[--size] as E
    }

    override fun top(): E? {
        if (size == 0) return null
        @Suppress("UNCHECKED_CAST")
        return storage[size - 1] as E
    }

    override fun push(element: E) {
        storage[size++] = element
    }

    override fun toString(): String {
        return storage.asSequence().take(size).joinToString(separator = ", ", prefix = "[${size}: ", postfix = "]")
    }
}