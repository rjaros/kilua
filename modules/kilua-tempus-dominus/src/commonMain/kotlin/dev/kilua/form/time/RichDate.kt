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
import dev.kilua.form.DateFormControl
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.buildCustomEventInit
import dev.kilua.utils.jsGet
import dev.kilua.utils.rem
import dev.kilua.utils.toDate
import dev.kilua.utils.toLocalDate
import dev.kilua.utils.unsafeCast
import kotlinx.datetime.LocalDate
import web.events.Event

/**
 * Tempus Dominus rich date component.
 */
public interface IRichDate : IAbstractRichDateTime, DateFormControl, WithStateFlow<LocalDate?>

/**
 * Tempus Dominus rich date component.
 */
public open class RichDate(
    value: LocalDate? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String = "yyyy-MM-dd",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<LocalDate?> = WithStateFlowDelegateImpl()
) : AbstractRichDateTime(disabled, required, format, inline, locale, className, id, renderConfig = renderConfig),
    DateFormControl,
    WithStateFlow<LocalDate?> by withStateFlowDelegate, IRichDate {

    private var sendChangeEvent: Boolean = true

    public override var value: LocalDate? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        if (tempusDominusInstance != null) {
            val currentValue = tempusDominusInstance!!.dates.lastPicked?.toLocalDate()
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
                this.value = date?.toLocalDate()
                dispatchEvent("change", buildCustomEventInit())
            }
        }
        @Suppress("LeakingThis")
        onEventDirect<Event>("error.td") {
            this.value = null
            if (tempusDominusInstance != null) {
                tempusDominusInstance?.clear()
            }
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
            initializeTempusDominus(this.value?.toDate(), calendarView = true, clockView = false)
        }
    }

    public companion object {
        internal var idCounter: Int = 0
    }

}

@Composable
private fun IComponent.richDateRef(
    value: LocalDate? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String = "yyyy-MM-dd",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDate.() -> Unit = {}
): RichDate {
    val component =
        remember {
            RichDate(
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
        set(value) { updateProperty(RichDate::value, it) }
        set(disabled) { updateProperty(RichDate::disabled, it) }
        set(required) { updateProperty(RichDate::required, it) }
        set(format) { updateProperty(RichDate::format, it) }
        set(inline) { updateProperty(RichDate::inline, it) }
        set(locale) { updateProperty(RichDate::locale, it) }
        set(className) { updateProperty(RichDate::className, it) }
        set(id) { updateProperty(RichDate::id, it) }
    }, setup)
    return component
}

@Composable
private fun IComponent.richDate(
    value: LocalDate? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String = "yyyy-MM-dd",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDate.() -> Unit = {}
) {
    val component =
        remember {
            RichDate(
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
        set(value) { updateProperty(RichDate::value, it) }
        set(disabled) { updateProperty(RichDate::disabled, it) }
        set(required) { updateProperty(RichDate::required, it) }
        set(format) { updateProperty(RichDate::format, it) }
        set(inline) { updateProperty(RichDate::inline, it) }
        set(locale) { updateProperty(RichDate::locale, it) }
        set(className) { updateProperty(RichDate::className, it) }
        set(id) { updateProperty(RichDate::id, it) }
    }, setup)
}

/**
 * Creates a [RichDate] component, returning a reference.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param calendarIcon the icon of the calendar button
 * @param inputClassName the CSS class name of the generated HTML input element
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [RichDate] component
 */
@Composable
public fun IComponent.richDateRef(
    value: LocalDate? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    inline: Boolean = false,
    format: String = "yyyy-MM-dd",
    locale: Locale = LocaleManager.currentLocale,
    calendarIcon: String = "fas fa-calendar-alt",
    inputClassName: String? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDate.() -> Unit = {}
): RichDate {
    val bindId = remember { "kilua_tempus_dominus_rd_${RichDate.idCounter++}" }
    return richDateRef(
        value,
        disabled,
        required,
        format,
        inline,
        locale,
        className = className % "input-group kilua-td",
        bindId
    ) {
        (this as AbstractRichDateTime).inputText = commonRichDateTime(
            bindId,
            name,
            placeholder,
            disabled,
            required,
            id,
            inline,
            calendarIcon,
            inputClassName
        ) {
            tempusDominusInstance?.hide()
        }
        setup()
    }
}

/**
 * Creates a [RichDate] component.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param calendarIcon the icon of the calendar button
 * @param inputClassName the CSS class name of the generated HTML input element
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [RichDate] component
 */
@Composable
public fun IComponent.richDate(
    value: LocalDate? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    inline: Boolean = false,
    format: String = "yyyy-MM-dd",
    locale: Locale = LocaleManager.currentLocale,
    calendarIcon: String = "fas fa-calendar-alt",
    inputClassName: String? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDate.() -> Unit = {}
) {
    val bindId = remember { "kilua_tempus_dominus_rd_${RichDate.idCounter++}" }
    richDate(
        value,
        disabled,
        required,
        format,
        inline,
        locale,
        className = className % "input-group kilua-td",
        bindId
    ) {
        (this as AbstractRichDateTime).inputText = commonRichDateTime(
            bindId,
            name,
            placeholder,
            disabled,
            required,
            id,
            inline,
            calendarIcon,
            inputClassName
        ) {
            tempusDominusInstance?.hide()
        }
        setup()
    }
}
