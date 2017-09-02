package nodes

import ProcalParserHelper.VariableMap
import calc.BigCmplxExact
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class StatementNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {
    protected val left: Node = left!!
    protected val right: Node?
    protected val isLast: Boolean

    override fun evaluate(): BigCmplxExact {
        val leftResult: BigCmplxExact = left.evaluate()
        VariableMap.setValue("~Ans", leftResult)
        if (!isLast)
            return right!!.evaluate()
        else
            return leftResult
    }

    override fun toString(): String {
        if (!isLast)
            return "$left:\n$right"
        else
            return "$left:"
    }

    init {
        isLast = ProcalParserHelper.nextIsStatementEnd(parser)
        if (!isLast)
            this.right = parser.expr(left, lexeme.lbp())
        else
            this.right = null
    }
}