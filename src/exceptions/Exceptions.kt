package exceptions

/** @brief Invoked as division by zero operatio is attempted */
class DivideByZeroException(override var message: String = "Cannot divide by zero."): Exception()

/** @brief Invoked as certain int-only operations such as .pow() takes in non-int value */
class IntegerTooLargeException(override var message: String = "Certain operations do not support large values."): Exception()

/** @brief Invoked as zero powered by zero is attempted */
class ZeroPowerZeroException(override var message: String = "Cannot have zero powered by zero."): Exception()

/** @brief Should never be invoked, should only be used in cases where else clause is mandatory yet there is no chance reaching there */
class InvalidException(override var message: String = "Should not have reached this state. "): Exception()