package nodes

import calc.type.BigCmplx
import calc.type.BigCmplxDecimal
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

open class ConstantNode(private val constantName: String) : Node {

    private val value: BigCmplx = BigCmplx(BigCmplxDecimal()) //TODO refer to init

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>):this(lexeme.text())

    fun getName(): String {
        return constantName
    }

    override fun evaluate(): BigCmplx {
        return ProcalParserHelper.VariableMap.getValue(constantName)
    }

    override fun toString(): String {
        return "&$constantName"
    }

    init {
        TODO("not implemented")
    }

}