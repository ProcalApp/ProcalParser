package nodes

import calc.BigCmplxFrac

/**
 * Node class interface
 */

interface Node {
    fun evaluate(): BigCmplxFrac
    override fun toString(): String
}
