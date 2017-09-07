import java.util.*
import State.SettingValues.*

object State {

    val settings: MutableMap<Settings, SettingValues> = SettingsHashMap()

    enum class Settings(val acceptedValues: Array<SettingValues>) {
        MODE(arrayOf(COMP, CMPLX, SD, BASE))
    }

    enum class SettingValues {
        COMP,
        CMPLX,
        SD,
        BASE,
        TRUE,
        FALSE
    }

    private class SettingsHashMap : HashMap<Settings, SettingValues>() {
        override fun put(key: Settings, value: SettingValues): SettingValues? {
            if (key.acceptedValues.indexOf(value) == -1)
                throw IllegalArgumentException("Illegal setting value. $key only accepts ${key.acceptedValues}")
            return super.put(key, value)
        }
    }
}