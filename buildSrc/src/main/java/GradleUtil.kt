object GradleUtil {
    private const val ENABLE_PROGUARD_KEY = "IS_ENABLE_PROGUARD"
    private const val DISABLE_PROGUARD = "0"
    private const val ENABLE_PROGUARD = "1"

    fun isEnableProguard(projectProperties: Map<String, String>): Boolean {
        val isEnableProguard = projectProperties.getOrDefault(ENABLE_PROGUARD_KEY, DISABLE_PROGUARD)
        println("Params IS_ENABLE_PROGUARD -> $isEnableProguard | Is Enable Proguard -> ${isEnableProguard == ENABLE_PROGUARD}")
        return isEnableProguard == ENABLE_PROGUARD
    }
}