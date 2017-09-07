package nodes

import calc.BigCmplx
import calc.BigCmplxExact
import calc.BigFrac
import calc.BigRealExact
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import java.math.BigDecimal

class ExponentialNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : Node {

    private val i: BigDecimal = BigDecimal(lexeme.text().substring(1))

    override fun evaluate(): BigCmplx {
        //return BigCmplx(BigCmplxExact(BigRealExact(BigFrac(BigDecimal(10)))))
        TODO("not implemented")
    }

    override fun toString(): String {
        return "E$i"
    }

}