package nodes

/**
 * Node class interface
 */

interface Node {
    fun evaluate(): Node
    fun toStr(): String
}
