package Nodes

import java.math.BigDecimal

/**
 * Number node
 *
 * Node storing a number
 */

open class NumberNode(value: BigDecimal): Node {
    protected var value: BigDecimal = BigDecimal(0)
    init {
        this.value = value
    }

//    override fun equals(other: Any?): Boolean {
//        if (this == other) return true
//        if (other == null || this::class != other::class) return false
//        val nn: NumberNode = other as NumberNode
//        return value == nn.value
//    }

    override fun evaluate(): Node{
        return this
    }

    override fun toStr(): String {
        return value.toString()
    }
}