import calc.BigCmplx
import org.bychan.core.dynamic.Language
import org.bychan.core.dynamic.LanguageBuilder
import org.bychan.core.dynamic.TokenDefinitionBuilder
import com.sun.org.apache.xerces.internal.dom.DOMNormalizer.abort
import nodes.*
import org.bychan.core.basic.EndToken
import org.bychan.core.basic.Parser
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


object ProcalParserHelper {

    private var l: Language<Node>? = null

    var b : LanguageBuilder<Node> = LanguageBuilder("Procal")

    object VariableMap {

        private val storage: MutableMap<String, BigCmplx> = HashMap()

        fun getValue(name: String): BigCmplx {
            if (storage.containsKey(name))
                return storage[name]!!
            return setValue(name, BigCmplx())
        }

        fun setValue(name: String, value: BigCmplx): BigCmplx {
            storage[name] = value
            return value
        }

    }

    object Tokens {
        val loopFor: TokenDefinitionBuilder<Node> = b.newToken()
                .named("for").matchesString("For")
                .nud{ left, parser, lexeme -> ForLoopNode(left, parser, lexeme)}

        val loopTo: TokenDefinitionBuilder<Node> = b.newToken()
                .named("to").matchesString("To")

        val loopStep: TokenDefinitionBuilder<Node> = b.newToken()
                .named("step").matchesString("Step")

        val loopNext: TokenDefinitionBuilder<Node> = b.newToken()
                .named("next").matchesString("Next")

        val colon: TokenDefinitionBuilder<Node> = b.newToken()
                .matchesString(":")
                .led{ left, parser, lexeme -> StatementNode(left, parser, lexeme)}

        val variable: TokenDefinitionBuilder<Node> = b.newToken()
                .named("variable").matchesPattern("\\$[A-Za-z_][A-Za-z_0-9]*")
                .nud{ left, parser, lexeme -> VariableNode(left, parser, lexeme)}

        val number: TokenDefinitionBuilder<Node> = b.newToken()
                .named("number").matchesPattern("\\d+\\.?\\d+|\\d+\\.|\\.\\d+|\\.|\\d+")
                .nud{ left, parser, lexeme -> NumberNode(left, parser, lexeme)}

        val set: TokenDefinitionBuilder<Node> = b.newToken()
                .named("set").matchesString("->")
                .led { left, parser, lexeme -> AssignmentNode(left, parser, lexeme)
                }

    }

    fun createProcalLanguage(): Language<Node> {
        return b.build()
    }

    fun getProcalLanguage(): Language<Node>? {
        if (l == null)
            l = createProcalLanguage()
        return this.l
    }

    fun nextIsStatementEnd(parser: Parser<Node>): Boolean {
        return parser.next().token.name.matches("set|next|whileEnd|ifEnd|else|colon|display".toRegex()) || parser.next().token == EndToken.get<Node>()
    }

    fun nextMustBeSeparator(parser: Parser<Node>, nodeName: String) {
        if (!nextIsStatementEnd(parser))
            parser.abort<Any>("You must end '" + nodeName + "' with 'colon' or a 'display' if the statement is not the end of a code block.");
    }

    fun indent(s: String): String {
        return s.replace("(?m)^".toRegex(), "  ")
    }
}