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
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.get
import dev.kilua.externals.obj
import dev.kilua.externals.toDate
import dev.kilua.externals.toLocalDateTime
import dev.kilua.form.DateTimeFormControl
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.rem
import dev.kilua.utils.unsafeCast
import kotlinx.datetime.LocalDateTime
import web.dom.events.Event

/**
 * Tempus Dominus rich date time component.
 */
public interface IRichDateTime : IAbstractRichDateTime, DateTimeFormControl, WithStateFlow<LocalDateTime?>

/**
 * Tempus Dominus rich date time component.
 */
public open class RichDateTime(
    value: LocalDateTime? = null,
    disabled: Boolean? = null,
    format: String = "yyyy-MM-dd HH:mm",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<LocalDateTime?> = WithStateFlowDelegateImpl()
) : AbstractRichDateTime(disabled, format, inline, locale, className, id, renderConfig = renderConfig),
    DateTimeFormControl,
    WithStateFlow<LocalDateTime?> by withStateFlowDelegate, IRichDateTime {

    public override var value: LocalDateTime? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        if (tempusDominusInstance != null) {
            val currentValue = tempusDominusInstance!!.dates.lastPicked?.toLocalDateTime()
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
        withStateFlowDelegate.formControl(this)
        @Suppress("LeakingThis")
        onEventDirect<Event>("change.td") {
            val date = it["detail"]?.get("date")?.unsafeCast<dev.kilua.externals.Date>()
            this.value = date?.toLocalDateTime()
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
            val calendarView = (format.contains("y") || format.contains("M") || format.contains("d"))
            val clockView = (format.contains("h", ignoreCase = true) || format.contains("mm") || format.contains("ss"))
            initializeTempusDominus(this.value?.toDate(), calendarView, clockView)
        }
    }

    public companion object {
        internal var idCounter: Int = 0
    }

}

@Composable
private fun IComponent.richDateTimeRef(
    value: LocalDateTime? = null,
    disabled: Boolean? = null,
    format: String = "yyyy-MM-dd HH:mm",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDateTime.() -> Unit = {}
): RichDateTime {
    val component =
        remember { RichDateTime(value, disabled, format, inline, locale, className, id, renderConfig = renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(RichDateTime::value, it) }
        set(disabled) { updateProperty(RichDateTime::disabled, it) }
        set(format) { updateProperty(RichDateTime::format, it) }
        set(inline) { updateProperty(RichDateTime::inline, it) }
        set(locale) { updateProperty(RichDateTime::locale, it) }
        set(className) { updateProperty(RichDateTime::className, it) }
        set(id) { updateProperty(RichDateTime::id, it) }
    }, setup)
    return component
}

@Composable
private fun IComponent.richDateTime(
    value: LocalDateTime? = null,
    disabled: Boolean? = null,
    format: String = "yyyy-MM-dd HH:mm",
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDateTime.() -> Unit = {}
) {
    val component =
        remember { RichDateTime(value, disabled, format, inline, locale, className, id, renderConfig = renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(RichDateTime::value, it) }
        set(disabled) { updateProperty(RichDateTime::disabled, it) }
        set(format) { updateProperty(RichDateTime::format, it) }
        set(inline) { updateProperty(RichDateTime::inline, it) }
        set(locale) { updateProperty(RichDateTime::locale, it) }
        set(className) { updateProperty(RichDateTime::className, it) }
        set(id) { updateProperty(RichDateTime::id, it) }
    }, setup)
}

/**
 * Creates a [RichDateTime] component, returning a reference.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param inputClassName the CSS class name of the generated HTML input element
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [RichDateTime] component
 */
@Composable
public fun IComponent.richDateTimeRef(
    value: LocalDateTime? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    inline: Boolean = false,
    format: String = "yyyy-MM-dd HH:mm",
    locale: Locale = LocaleManager.currentLocale,
    inputClassName: String? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDateTime.() -> Unit = {}
): RichDateTime {
    val bindId = remember { "kilua_tempus_dominus_rdt_${RichDateTime.idCounter++}" }
    return richDateTimeRef(
        value,
        disabled,
        format,
        inline,
        locale,
        className = className % "input-group kilua-td",
        bindId
    ) {
        commonRichDateTime(
            bindId,
            name,
            placeholder,
            disabled,
            required,
            id,
            inline,
            "fas fa-calendar-alt",
            inputClassName
        )
        setup()
    }
}

/**
 * Creates a [RichDateTime] component.
 *
 * @param value the initial value
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param inputClassName the CSS class name of the generated HTML input element
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.richDateTime(
    value: LocalDateTime? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    inline: Boolean = false,
    format: String = "yyyy-MM-dd HH:mm",
    locale: Locale = LocaleManager.currentLocale,
    inputClassName: String? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichDateTime.() -> Unit = {}
) {
    val bindId = remember { "kilua_tempus_dominus_rdt_${RichDateTime.idCounter++}" }
    richDateTime(value, disabled, format, inline, locale, className = className % "input-group kilua-td", bindId) {
        commonRichDateTime(
            bindId,
            name,
            placeholder,
            disabled,
            required,
            id,
            inline,
            "fas fa-calendar-alt",
            inputClassName
        )
        setup()
    }
}
