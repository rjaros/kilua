/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package web

@Suppress("EXPECTED_EXTERNAL_DECLARATION")
public expect external class JsBoolean : JsAny

public expect fun JsBoolean.toBoolean(): Boolean

public expect fun Boolean.toJsBoolean(): JsBoolean
