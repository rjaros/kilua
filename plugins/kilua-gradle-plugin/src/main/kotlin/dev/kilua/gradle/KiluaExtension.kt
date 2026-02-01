/*
 * Copyright (c) 2024 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kilua.gradle

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject

/**
 * Configuration for the Kilua plugin.
 *
 * The default values are set in [KiluaPlugin.createKiluaExtension].
 */
public abstract class KiluaExtension @Inject constructor(
    private val objects: ObjectFactory,
    private val providers: ProviderFactory,
) {

    public val enableGradleTasks: Property<Boolean> = kiluaGradleProperty("enableGradleTasks")

    public val enableResolutions: Property<Boolean> = kiluaGradleProperty("enableResolutions")

    public val disableSkiko: Property<Boolean> = kiluaGradleProperty("disableSkiko")

    public val kiluaYml: Property<String> = kiluaGradleProperty("kiluaYml", "kilua.yml")

    private fun kiluaGradleProperty(
        property: String,
        default: Boolean = true,
    ): Property<Boolean> {
        val convention = providers.gradleProperty("dev.kilua.plugin.$property")
            .map { it.toBoolean() }
            .orElse(default)
        return objects.property(Boolean::class.java).convention(convention)
    }

    private fun kiluaGradleProperty(
        property: String,
        default: String,
    ): Property<String> {
        val convention = providers.gradleProperty("dev.kilua.plugin.$property")
            .orElse(default)
        return objects.property(String::class.java).convention(convention)
    }
}
