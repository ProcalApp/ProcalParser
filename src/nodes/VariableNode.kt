package nodes

import ProcalParserHelper.VariableMap
import calc.BigCmplxExact
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

open class VariableNode(private val variableName: String) : Node {

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>):this(lexeme.text())

    fun getName(): String {
        return variableName
    }

    override fun evaluate(): BigCmplxExact {
        return VariableMap.getValue(variableName)
    }

    override fun toString(): String {
        return "$$variableName"
    }

}