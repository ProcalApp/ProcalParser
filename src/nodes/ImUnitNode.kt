package nodes

/**
 * Imaginary Unit (i) Node
 *
 * Node for storing pure i
 */

class ImUnitNode : Node {
    override fun evaluate(): Node {
        return this
    }

    override fun toString(): String {
        return "i"
    }
}