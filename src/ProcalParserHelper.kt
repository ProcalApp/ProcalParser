import nodes.Node
import org.bychan.core.dynamic.Language
import org.bychan.core.dynamic.LanguageBuilder

object ProcalParserHelper {

    private var l: Language<Node>? = null

    var b : LanguageBuilder<Node> = LanguageBuilder("Procal")

    fun createProcalLanguage(): Language<Node> {
        return b.build()
    }

    fun getProcalLanguage(): Language<Node>? {
        if (l == null)
            l = createProcalLanguage()
        return this.l
    }
}