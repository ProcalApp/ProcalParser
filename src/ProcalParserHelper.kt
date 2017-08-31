import calc.BigCmplx
import exceptions.MissingSeparatorException
import org.bychan.core.dynamic.Language
import org.bychan.core.dynamic.LanguageBuilder
import nodes.*
import org.bychan.core.basic.EndToken
import org.bychan.core.basic.Lexeme
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



        //# Memory

        val mPlus = b.newToken().named("M+")
                .matchesString("M+")
                .led(::MPlusNode)

        val mMinus = b.newToken().named("M-")
                .matchesString("M-")
                .led(::MMinusNode)



        //# Conditionals

        //  Shorthand If

        val shorthandIf = b.newToken().named("shorthandIf")
                .matchesString("=>")
                .led(::ShorthandIfNode)

        val colon = b.newToken().named("colon")
                .matchesString(":")
                .led { left, parser, lexeme -> StatementNode(left, parser, lexeme) }


        //# Arithmetic Operators

        val plus = b.newToken().named("plus")
                .matchesString("+")
                .nud{ left, parser, lexeme -> parser.expr(left, lexeme.lbp())} //Ignore positive sign
                .led(::AdditionNode)

        val minus = b.newToken().named("minus")
                .matchesString("-")
                .nud(::NegationNode)
                .led(::SubtractionNode)

        val exponential = b.newToken().named("exponential")
                .matchesPattern("E-*\\d+")
                .nud(::ExponentialNode)

        val multiply = b.newToken().named("multiply")
                .matchesString("*")
                .led(::MultiplicationNode)

        val hiddenMultiply = b.newToken().named("hiddenMultiply")
                .matchesString("`")
                .led(::HiddenMultiplicationNode)

        val divide = b.newToken().named("divide")
                .matchesString("/")
                .led(::DivisionNode)

        val power = b.newToken().named("power")
                .matchesString("^")
                .led(::PowerNode)

        val tenPower = b.newToken().named("10^")
                .matchesString("^")
                .nud(::TenPowerNode)
                .led { left, parser, lexeme -> HiddenMultiplicationNode(left, TenPowerNode(left, parser, lexeme))}



        //# Value Representations

        val answer = b.newToken().named("answer")
                .matchesString("Ans")
                .nud(::AnswerNode)

        val variable = b.newToken().named("variable")
                .matchesPattern("\\$[A-Za-z_][A-Za-z_0-9]*")
                .nud(::VariableNode)

        val number = b.newToken().named("number")
                .matchesPattern("(?:\\d+)?\\.(?:\\d+)?|\\d+")
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