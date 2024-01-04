/*
 * Copyright (c) 2023 Robert Jaros
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

package dev.kilua.form.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.get
import dev.kilua.externals.obj
import dev.kilua.externals.toDate
import dev.kilua.externals.toLocalTime
import dev.kilua.form.TimeFormControl
import dev.kilua.i18n.DefaultLocale
import dev.kilua.i18n.Locale
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import kotlinx.datetime.LocalTime
import web.dom.events.Event

/**
 * Tempus Dominus rich date time component.
 */
public open class RichTime(
    value: LocalTime? = null,
    disabled: Boolean? = null,
    format: String = "HH:mm",
    inline: Boolean = false,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<LocalTime?> = WithStateFlowDelegateImpl()
) : AbstractRichDateTime(disabled, format, inline, locale, className, renderConfig), TimeFormControl,
    WithStateFlow<LocalTime?> by withStateFlowDelegate {

    public override var value: LocalTime? by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        if (tempusDominusInstance != null) {
            val currentValue = tempusDominusInstance!!.dates.lastPicked?.toLocalTime()
            if (it != currentValue) {
                if (it != null) {
                    tempusDominusInstance!!.dates.setValue(tempusDominusInstance!!.dates.parseInput(it.toDate()))
                } else {
                    tempusDominusInstance?.clear()
                }
            }
        }
    }

    init {
        @Suppress("LeakingThis")
        onEventDirect<Event>("change.td") {
            val date = it["detail"]?.get("date")?.cast<dev.kilua.externals.Date>()
            this.value = date?.toLocalTime()
            dispatchEvent("change", buildCustomEventInit(obj()))
        }
        @Suppress("LeakingThis")
        onEventDirect<Event>("error.td") {
            this.value = null
            dispatchEvent("change", buildCustomEventInit(obj()))
        }
    }

    override fun getValueAsString(): String? {
        return if (tempusDominusInstance != null && tempusDominusInstance!!.dates.lastPicked != null && value != null) {
            tempusDominusInstance!!.dates.formatInput(tempusDominusInstance!!.dates.lastPicked!!)
        } else value.toString()
    }

    override fun initializeTempusDominus() {
        if (renderConfig.isDom) {
            initializeTempusDominus(this.value?.toDate(), calendarView = false, clockView = true)
        }
    }

    public companion object {
        internal var idCounter: Int = 0
    }

}

@Composable
private fun ComponentBase.richTime(
    value: LocalTime? = null,
    disabled: Boolean? = null,
    format: String = "HH:mm",
    inline: Boolean = false,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    setup: @Composable RichTime.() -> Unit = {}
): RichTime {
    val component = remember { RichTime(value, disabled, format, inline, locale, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(RichTime::value, it) }
        set(disabled) { updateProperty(RichTime::disabled, it) }
        set(format) { updateProperty(RichTime::format, it) }
        set(inline) { updateProperty(RichTime::inline, it) }
        set(locale) { updateProperty(RichTime::locale, it) }
        set(className) { updateProperty(RichTime::className, it) }
    }, setup)
    return component
}


/**
 * Creates a [RichTime] component.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param id the ID of the generated HTML input element
 * @param locale the locale for i18n
 * @param className the CSS class name
 * @param setup a function for setting up the component
 * @return a [RichTime] component
 */
@Composable
public fun ComponentBase.richTime(
    value: LocalTime? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    id: String? = null,
    inline: Boolean = false,
    format: String = "HH:mm",
    locale: Locale = DefaultLocale(),
    className: String? = null,
    setup: @Composable RichTime.() -> Unit = {}
): RichTime {
    val bindId = remember { "kilua_tempus_dominus_rt_${RichTime.idCounter++}" }
    return richTime(value, disabled, format, inline, locale, className = className % "input-group") {
        commonRichDateTime(bindId, name, placeholder, disabled, required, id, inline, "fas fa-clock")
        setup()
    }
}
