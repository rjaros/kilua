package dev.kilua.dom.core/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

public expect class JsNumber: JsAny

public expect fun JsNumber.toDouble(): Double

public expect fun Double.toJsNumber(): JsNumber

public expect fun JsNumber.toInt(): Int

public expect fun Int.toJsNumber(): JsNumber
