package exceptions

/** Generic parsing exception thrown during Procal language parsing*/
open class ParsingException(override var message: String = "Parsing failed."): RuntimeException()

/** Missing statement conclusion at end of scope or statement*/
class MissingSeparatorException(override var message: String = "You must end statement with 'colon' or 'display' if statement is not at end of code block."): ParsingException()

/** Breaking from loop*/
class BreakException(override var message: String = "Breaking from loop."): RuntimeException()

/** Constant not found*/
class IllegalConstantException(override var message: String = "Constant not present"): IllegalArgumentException()

/** Invoked as division by zero operation is attempted */
class DivideByZeroException(override var message: String = "Cannot divide by zero."): ArithmeticException()

/** Invoked as certain int-only operations such as .pow() takes in non-int value */
class IntegerTooLargeException(override var message: String = "Certain operations do not support large values."): ArithmeticException()

/** Invoked as zero powered by zero is attempted */
class ZeroPowerZeroException(override var message: String = "Cannot have zero powered by zero."): ArithmeticException()

/** Should never be invoked, should only be used in cases where else clause is mandatory yet there is no chance reaching there */
class InvalidException(override var message: String = "Should not have reached this state. "): RuntimeException()

/** Invoked as null BigCmplx is called */
class NullException(override var message: String = "Attempting to resolve null value.") : RuntimeException()

/** Placed at places with unfinished implementation */
class UnfinishedException(override var message: String = "Unfinished.") : RuntimeException()

/** Invoked when complex numbers are generated in REAL mode */
class ComplexException(override var message: String = "Cannot have complex number.") : ArithmeticException()