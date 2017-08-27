package nodes

import calc.BigCmplx

/**
 * Node class interface
 */

interface Node {
    fun evaluate(): BigCmplx
    override fun toString(): String
}
