package com.example.app.bmi

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class BmiForCmKgTest : StringSpec({
    val accuracy  = 0.01

    "count" {
        BmiForCmKg(85.0, 190.0).count() shouldBe (23.54 plusOrMinus accuracy)
        BmiForCmKg(85.4, 0.00).count() shouldBe (0.00 plusOrMinus accuracy)
        BmiForCmKg(0.00, 190.3).count() shouldBe (0.00 plusOrMinus accuracy)
        BmiForCmKg(0.00, 0.00).count() shouldBe (0.00 plusOrMinus accuracy)
        BmiForCmKg(0.00, -184.2).count() shouldBe (-1.00 plusOrMinus accuracy)
        BmiForCmKg(-78.2, 0.00).count() shouldBe (-1.00 plusOrMinus accuracy)
        BmiForCmKg(-99.3, -185.6).count() shouldBe (-1.00 plusOrMinus accuracy)
    }
})
