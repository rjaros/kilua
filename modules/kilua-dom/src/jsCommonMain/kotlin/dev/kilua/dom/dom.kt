/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package dev.kilua.dom

import dev.kilua.dom.api.Document
import dev.kilua.dom.api.Storage
import dev.kilua.dom.api.Window

public external val window: Window

public external val document: Document

public external val localStorage: Storage

public external val sessionStorage: Storage
