package com.jalaljankhan.azdigitizer.patterns.builder

import android.app.Activity
import com.jalaljankhan.azdigitizer.core.enums.PatternShape
import com.jalaljankhan.azdigitizer.patterns.core.FullScreenPattern
import com.jalaljankhan.azdigitizer.patterns.abstracts.Pattern
import com.jalaljankhan.azdigitizer.patterns.letters.CPattern
import com.jalaljankhan.azdigitizer.patterns.letters.CReversePattern
import com.jalaljankhan.azdigitizer.patterns.letters.LPattern
import com.jalaljankhan.azdigitizer.patterns.letters.NPattern
import com.jalaljankhan.azdigitizer.patterns.letters.OPattern

internal class PatternBuilder(private val context: Activity) {
    internal fun build(pattern: PatternShape): Pattern {
        return when (pattern) {
            PatternShape.C -> CPattern(context = context)
            PatternShape.L -> LPattern(context = context)
            PatternShape.N -> NPattern(context = context)
            PatternShape.O -> OPattern(context = context)
            PatternShape.REVERSE_C -> CReversePattern(context = context)
            else -> FullScreenPattern(context = context)
        }
    }
}