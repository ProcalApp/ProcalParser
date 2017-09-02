import calc.BigCmplxFrac
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

        private val storage: MutableMap<String, BigCmplxFrac> = HashMap()

        fun getValue(name: String): BigCmplxFrac {
            if (storage.containsKey(name))
                return storage[name]!!
            return setValue(name, BigCmplxFrac())
        }

        fun setValue(name: String, value: BigCmplxFrac): BigCmplxFrac {
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

        //  If-Statement

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

        val negate = b.newToken().named("negate")
                .matchesString("(-)")
                .nud(::NegationNode)

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
                .matchesString("\\10^")
                .nud(::TenPowerNode)
                .led { left, parser, lexeme -> HiddenMultiplicationNode(left, TenPowerNode(left, parser, lexeme))}

        val permutation = b.newToken().named("permutation")
                .matchesString("P")
                .led(::PermutationNode)

        val combination = b.newToken().named("combination")
                .matchesString("C")
                .led(::CombinationNode)

        val percent = b.newToken().named("percent")
                .matchesString("%")
                .led(::PercentNode)


        //# Grouping

        val lparen = b.newToken().named("lparen")
                .matchesString("(")
                .nud(::ParenthesisNode)

        val rparen = b.newToken().named("rparen")
                .matchesString(")")



        //# Value Representations

        val answer = b.newToken().named("answer")
                .matchesString("Ans")
                .nud(::AnswerNode)

        val variable = b.newToken().named("variable")
                .matchesPattern("\\$[A-Za-z_][A-Za-z_0-9]*")
                .nud(::VariableNode)

        val constant = b.newToken().named("constant")
                .matchesPattern("&[A-Za-z_][A-Za-z_0-9]*")
                .nud(::ConstantNode)

        val number = b.newToken().named("number")
                .matchesPattern("(?:\\d+)?\\.(?:\\d+)?|\\d+")
                .nud(::NumberNode)

        val randomNumber = b.newToken().named("randomNumber")
                .matchesString("Ran#")
                .nud(::RandomNumberNode)

        val set = b.newToken().named("set")
                .matchesString("->")
                .led(::AssignmentNode)



        //# IO Operators



        //# Functions

        //  Prefix Functions

        //  Suffix Functions



        //# Comparison Operators



        //# Boolean Operators



        //# Special Tokens

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