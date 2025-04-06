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
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.utils.buildCustomEventInit
import dev.kilua.utils.jsGet
import dev.kilua.externals.toDate
import dev.kilua.externals.toLocalTime
import dev.kilua.form.TimeFormControl
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.rem
import dev.kilua.utils.unsafeCast
import kotlinx.datetime.LocalTime
import web.events.Event

/**
 * Tempus Dominus rich time component.
 */
public interface IRichTime : IAbstractRichDateTime, TimeFormControl, WithStateFlow<LocalTime?>

/**
 * Tempus Dominus rich time component.
 */
public open class RichTime(
    value: LocalTime? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String = "HH:mm",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<LocalTime?> = WithStateFlowDelegateImpl()
) : AbstractRichDateTime(disabled, required, format, inline, locale, className, id, renderConfig = renderConfig),
    TimeFormControl,
    WithStateFlow<LocalTime?> by withStateFlowDelegate, IRichTime {

    private var sendChangeEvent: Boolean = true

    public override var value: LocalTime? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        if (tempusDominusInstance != null) {
            val currentValue = tempusDominusInstance!!.dates.lastPicked?.toLocalTime()
            if (it != currentValue) {
                sendChangeEvent = false
                if (it != null) {
                    tempusDominusInstance!!.dates.setValue(tempusDominusInstance!!.dates.parseInput(it.toDate()))
                } else {
                    tempusDominusInstance?.clear()
                }
                sendChangeEvent = true
            }
        }
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        @Suppress("LeakingThis")
        onEventDirect<Event>("change.td") {
            if (sendChangeEvent) {
                val date = it.jsGet("detail")?.jsGet("date")?.unsafeCast<js.date.Date>()
                this.value = date?.toLocalTime()
                dispatchEvent("change", buildCustomEventInit())
            }
        }
        @Suppress("LeakingThis")
        onEventDirect<Event>("error.td") {
            this.value = null
            dispatchEvent("change", buildCustomEventInit())
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
private fun IComponent.richTimeRef(
    value: LocalTime? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String = "HH:mm",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichTime.() -> Unit = {}
): RichTime {
    val component =
        remember {
            RichTime(
                value,
                disabled,
                required,
                format,
                inline,
                locale,
                className,
                id,
                renderConfig = renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(RichTime::value, it) }
        set(disabled) { updateProperty(RichTime::disabled, it) }
        set(required) { updateProperty(RichTime::required, it) }
        set(format) { updateProperty(RichTime::format, it) }
        set(inline) { updateProperty(RichTime::inline, it) }
        set(locale) { updateProperty(RichTime::locale, it) }
        set(className) { updateProperty(RichTime::className, it) }
    }, setup)
    return component
}

@Composable
private fun IComponent.richTime(
    value: LocalTime? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String = "HH:mm",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichTime.() -> Unit = {}
) {
    val component =
        remember {
            RichTime(
                value,
                disabled,
                required,
                format,
                inline,
                locale,
                className,
                id,
                renderConfig = renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(RichTime::value, it) }
        set(disabled) { updateProperty(RichTime::disabled, it) }
        set(required) { updateProperty(RichTime::required, it) }
        set(format) { updateProperty(RichTime::format, it) }
        set(inline) { updateProperty(RichTime::inline, it) }
        set(locale) { updateProperty(RichTime::locale, it) }
        set(className) { updateProperty(RichTime::className, it) }
    }, setup)
}


/**
 * Creates a [RichTime] component, returning a reference.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param clockIcon the icon of the clock button
 * @param inputClassName the CSS class name of the generated HTML input element
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [RichTime] component
 */
@Composable
public fun IComponent.richTimeRef(
    value: LocalTime? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    inline: Boolean = false,
    format: String = "HH:mm",
    locale: Locale = LocaleManager.currentLocale,
    clockIcon: String = "fas fa-clock",
    inputClassName: String? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichTime.() -> Unit = {}
): RichTime {
    val bindId = remember { "kilua_tempus_dominus_rt_${RichTime.idCounter++}" }
    return richTimeRef(
        value,
        disabled,
        required,
        format,
        inline,
        locale,
        className = className % "input-group kilua-td",
        bindId
    ) {
        commonRichDateTime(bindId, name, placeholder, disabled, required, id, inline, clockIcon, inputClassName) {
            tempusDominusInstance?.hide()
        }
        setup()
    }
}

/**
 * Creates a [RichTime] component.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param clockIcon the icon of the clock button
 * @param inputClassName the CSS class name of the generated HTML input element
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.richTime(
    value: LocalTime? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    inline: Boolean = false,
    format: String = "HH:mm",
    locale: Locale = LocaleManager.currentLocale,
    clockIcon: String = "fas fa-clock",
    inputClassName: String? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichTime.() -> Unit = {}
) {
    val bindId = remember { "kilua_tempus_dominus_rt_${RichTime.idCounter++}" }
    richTime(
        value,
        disabled,
        required,
        format,
        inline,
        locale,
        className = className % "input-group kilua-td",
        bindId
    ) {
        commonRichDateTime(bindId, name, placeholder, disabled, required, id, inline, clockIcon, inputClassName) {
            tempusDominusInstance?.hide()
        }
        setup()
    }
}
