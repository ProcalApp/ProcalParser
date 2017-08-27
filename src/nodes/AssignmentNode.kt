package nodes

import ProcalParserHelper.Tokens
import ProcalParserHelper.VariableMap
import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class AssignmentNode(private val left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : Node {
    private val right: Node
    private val variableName: String

    override fun evaluate(): BigCmplx {
        val leftResult: BigCmplx = left!!.evaluate()
        VariableMap.setValue(variableName, leftResult)
        return leftResult
    }

    override fun toString(): String {
        return left.toString() + "->$" + variableName
    }

    init {
        if (parser.peek().token != Tokens.variable.token)
            parser.abort<Any>("Invalid assignment RHS. Expected a variable name")
        this.right = parser.expr(left, lexeme.lbp()) as VariableNode
        this.variableName = right.getName()
    }
}