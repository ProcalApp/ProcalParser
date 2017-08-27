package nodes

import ProcalParserHelper.Tokens
import ProcalParserHelper.VariableMap
import ProcalParserHelper.nextMustBeSeparator
import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOM.indent



class ForLoopNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : Node {

    private val initNode: Node
    private val toNode: Node
    private val controlVariable: VariableNode
    private val doNode: Node
    private val stepNode: Node?

    override fun evaluate(): BigCmplx {
        val toResult: BigCmplx = toNode.evaluate()
        val controlVariableName: String = controlVariable.getName()
        VariableMap.setValue(controlVariableName, initNode.evaluate())
        var doResult:BigCmplx = BigCmplx()
        var controlVariableValue:BigCmplx

        TODO("not implemented")
    }

    override fun toString(): String {
        return "For " + initNode.toString() + " -> " + controlVariable.toString() +
                " To " + toNode.toString() +
                (if (stepNode == null) "" else " Step " + stepNode.toString()) + ":\n" +
                ProcalParserHelper.indent(doNode.toString()) +
                "\nNext"
    }

    init {
        //Expression before set
        initNode = parser.expr(left, 4)
        parser.swallow("set")
        controlVariable = parser.nud(parser.swallow(Tokens.variable.tokenName), left) as VariableNode
        parser.swallow("to")

        toNode = parser.expr(left, 3)

        stepNode = if (parser.next().token == Tokens.loopStep.token) {
            parser.swallow("step")
            parser.expr(left, 3)
        } else
            null

        parser.swallow("colon")

        doNode = parser.expr(left, lexeme.lbp())

        parser.swallow("next")
        nextMustBeSeparator(parser, "next")
    }

}