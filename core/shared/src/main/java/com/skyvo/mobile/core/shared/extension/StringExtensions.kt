package com.skyvo.mobile.core.shared.extension

fun String?.getWeekDayAbbreviations(): List<String> {
    return when (this) {
        "tr" -> listOf("PTS", "SAL", "ÇAR", "PER", "CUM", "CTS", "PAZ")
        "es" -> listOf("LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB", "DOM")
        "fr" -> listOf("LUN", "MAR", "MER", "JEU", "VEN", "SAM", "DIM")
        "de" -> listOf("MON", "DIE", "MIT", "DON", "FRE", "SAM", "SON")
        "it" -> listOf("LUN", "MAR", "MER", "GIO", "VEN", "SAB", "DOM")
        "pt" -> listOf("SEG", "TER", "QUA", "QUI", "SEX", "SÁB", "DOM")
        "ru" -> listOf("ПОН", "ВТО", "СРЕ", "ЧЕТ", "ПЯТ", "СУБ", "ВОС")
        else -> listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
    }
}