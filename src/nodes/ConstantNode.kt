package nodes

import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

open class ConstantNode(private val constantName: String) : Node {

    private val value: BigCmplxFrac = BigCmplxFrac()

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>):this(lexeme.text())

    fun getName(): String {
        return constantName
    }

    override fun evaluate(): BigCmplxFrac {
        return ProcalParserHelper.VariableMap.getValue(constantName)
    }

    override fun toString(): String {
        return "&$constantName"
    }

    init {
        TODO("not implemented")
    }

}