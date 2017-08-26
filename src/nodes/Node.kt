package nodes

/**
 * Node class interface
 */

interface Node {
    fun evaluate(): Node
    override fun toString(): String
}
