package nodes

import ProcalParserHelper.VariableMap
import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class MPlusNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {
    private val left: Node = left!!

    override fun evaluate(): BigCmplx {
        val m: BigCmplx = VariableMap.getValue("M")
        val leftResult: BigCmplx = left.evaluate()
        TODO("not implemented")
        // VariableMap.setValue("M", m.add(leftResult))
    }

    override fun toString(): String {
        return "($left) M+"
    }

    init {
        ProcalParserHelper.nextMustBeSeparator(parser, "M+")
    }
}