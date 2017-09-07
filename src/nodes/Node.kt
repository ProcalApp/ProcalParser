package nodes

import calc.type.BigCmplx

/**
 * Node class interface
 */

interface Node {
    fun evaluate(): BigCmplx
    override fun toString(): String
}
