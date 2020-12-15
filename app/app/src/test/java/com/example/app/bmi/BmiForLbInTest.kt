package com.example.app.bmi

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class BmiForLbInTest : StringSpec({
    val accuracy  = 0.01

    "count" {
        BmiForLbIn(187.3, 74.3).count() shouldBe (23.85 plusOrMinus accuracy)
        BmiForLbIn(0.00, 74.3).count() shouldBe (0.00 plusOrMinus accuracy)
        BmiForLbIn(187.3, 0.00).count() shouldBe (0.00 plusOrMinus accuracy)
        BmiForLbIn(0.00, 0.00).count() shouldBe (0.00 plusOrMinus accuracy)
        BmiForLbIn(0.00, -142.3).count() shouldBe (-1.00 plusOrMinus accuracy)
        BmiForLbIn(-12.3, 0.00).count() shouldBe (-1.00 plusOrMinus accuracy)
        BmiForLbIn(-123.4, -68.2).count() shouldBe (-1.00 plusOrMinus accuracy)
    }
})
