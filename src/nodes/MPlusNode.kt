package nodes

import ProcalParserHelper.VariableMap
import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class MPlusNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {
    private val left: Node = left!!

    override fun evaluate(): BigCmplxFrac {
        val m: BigCmplxFrac = VariableMap.getValue("M")
        val leftResult: BigCmplxFrac = left.evaluate()
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