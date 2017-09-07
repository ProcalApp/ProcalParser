package nodes

import ProcalParserHelper.VariableMap
import calc.type.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class StatementNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {
    protected val left: Node = left!!
    protected val right: Node?
    protected val isLast: Boolean

    override fun evaluate(): BigCmplx {
        val leftResult: BigCmplx = left.evaluate()
        VariableMap.setValue("~Ans", leftResult)
        return if (!isLast)
            right!!.evaluate()
        else
            leftResult
    }

    override fun toString(): String {
        return if (!isLast)
            "$left:\n$right"
        else
            "$left:"
    }

    init {
        isLast = ProcalParserHelper.nextIsStatementEnd(parser)
        if (!isLast)
            this.right = parser.expr(left, lexeme.lbp())
        else
            this.right = null
    }
}