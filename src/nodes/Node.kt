package nodes

import calc.BigCmplx

/**
 * Node class interface
 */

public interface Node {
    fun evaluate(): BigCmplx
    override fun toString(): String
}
