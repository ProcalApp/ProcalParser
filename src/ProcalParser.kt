import nodes.Node
import org.bychan.core.basic.ParseResult
import org.bychan.core.dynamic.Language

class ProcalParser() {

    private var l: Language<Node>? = ProcalParserHelper.getProcalLanguage()

    fun parse(input:String) {
        var line = input.replace("\\s+".toRegex(), " ").trim()
        try {
            var parseResult: ParseResult<Node>? = l!!.newLexParser().tryParse(input, {p -> p.expr(null, 0)})
        } catch (e:Exception) {

        }
    }
}