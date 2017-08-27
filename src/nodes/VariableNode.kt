package nodes

import ProcalParserHelper.VariableMap
import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class VariableNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : Node {

    private val variableName: String = lexeme.text()

    fun getName(): String {
        return variableName
    }

    override fun evaluate(): BigCmplx {
        return VariableMap.getValue(variableName)
    }

    override fun toString(): String {
        return "$" + variableName
    }

}