package nodes

import calc.BigCmplx
/**
 * Number node
 *
 * Node storing a number
 */

class NumberNode(value: BigCmplx): Node {
    private var value: BigCmplx = BigCmplx()
    init {
        this.value = value
    }

    override fun evaluate(): Node{
        return this
    }

    override fun toString(): String {
        return value.toString()
    }
}