package Nodes

import java.math.BigDecimal

/**
 * Nodes.Node class interface
 */

interface Node {
    fun evaluate(): Node
    fun toStr(): String
}
