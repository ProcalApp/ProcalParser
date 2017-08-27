package nodes

import ProcalParserHelper.VariableMap
import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class StatementNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {
    protected val left: Node
    protected val right: Node?
    protected val isLast: Boolean

    override fun evaluate(): BigCmplx {
        val leftResult: BigCmplx = left.evaluate()
        VariableMap.setValue("~Ans", leftResult)
        if (!isLast)
            return right!!.evaluate()
        else
            return leftResult
    }

    override fun toString(): String {
        if (!isLast)
            return left.toString() + ":\n" + right.toString()
        else
            return left.toString() + ":"
    }

    init {
        this.left = left!!
        isLast = ProcalParserHelper.nextIsStatementEnd(parser)
        if (!isLast)
            this.right = parser.expr(left, lexeme.lbp())
        else
            this.right = null
    }
}