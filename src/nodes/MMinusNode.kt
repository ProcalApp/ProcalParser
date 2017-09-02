package nodes

import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class MMinusNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {
    private val left: Node = left!!

    override fun evaluate(): BigCmplxFrac {
        val m: BigCmplxFrac = ProcalParserHelper.VariableMap.getValue("M")
        val leftResult: BigCmplxFrac = left.evaluate()
        TODO("not implemented")
        // VariableMap.setValue("M", m.subtract(leftResult))
    }

    override fun toString(): String {
        return "($left) M-"
    }

    init {
        ProcalParserHelper.nextMustBeSeparator(parser, "M-")
    }
}