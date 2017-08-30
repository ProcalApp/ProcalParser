import calc.BigCmplx
import exceptions.MissingSeparatorException
import org.bychan.core.dynamic.Language
import org.bychan.core.dynamic.LanguageBuilder
import nodes.*
import org.bychan.core.basic.EndToken
import org.bychan.core.basic.Parser


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

    /**
     * Tokens defined in the Procal language
     */
    object Tokens {

        //# Recursions

        //  For Loop

        val loopFor = b.newToken().named("for")
                .matchesString("For")
                .nud{ left, parser, lexeme -> ForLoopNode(left, parser, lexeme)}

        val loopTo = b.newToken().named("to")
                .matchesString("To")

        val loopStep = b.newToken().named("step")
                .matchesString("Step")

        val loopNext = b.newToken().named("next")
                .matchesString("Next")


        //  While Loop

        val loopWhile = b.newToken().named("while")
                .matchesString("While")
                .nud(::WhileLoopNode)

        val loopWhileEnd = b.newToken().named("whileEnd")
                .matchesString("WhileEnd")


        //  Recursion Utilities

        val loopBreak = b.newToken().named("break")
                .matchesString("Break")
                .nud(::BreakNode)


        //  Memory

        val mPlus = b.newToken().named("M+")
                .matchesString("M+")
                .led(::MPlusNode)

        val mMinus = b.newToken().named("M-")
                .matchesString("M-")
                .led(::MMinusNode)

        val colon = b.newToken().named("colon")
                .matchesString(":")
                .led(::StatementNode)

        val answer = b.newToken().named("answer")
                .matchesString("Ans")
                .nud(::AnswerNode)

        val variable = b.newToken().named("variable")
                .matchesPattern("\\$[A-Za-z_][A-Za-z_0-9]*")
                .nud(::VariableNode)

        val number = b.newToken().named("number")
                .matchesPattern("\\d+\\.?\\d+|\\d+\\.|\\.\\d+|\\.|\\d+")
                .nud(::NumberNode)

        val set = b.newToken().named("set")
                .matchesString("->")
                .led(::AssignmentNode)

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
            throw MissingSeparatorException("You must end '$nodeName' with 'colon' or 'display' if statement is not at end of code block.");
    }

    fun indent(s: String): String {
        return s.replace("(?m)^".toRegex(), "  ")
    }
}