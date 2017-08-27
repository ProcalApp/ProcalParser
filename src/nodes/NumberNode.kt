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

    override fun evaluate(): BigCmplx{
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}