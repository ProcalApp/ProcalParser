package nodes

import calc.BigCmplxExact

/**
 * Node class interface
 */

interface Node {
    fun evaluate(): BigCmplxExact
    override fun toString(): String
}
