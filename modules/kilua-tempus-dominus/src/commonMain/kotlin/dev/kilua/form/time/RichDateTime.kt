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
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.externals.TempusDominus
import dev.kilua.externals.TempusDominusOptions
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.get
import dev.kilua.externals.obj
import dev.kilua.externals.set
import dev.kilua.externals.tempusDominusLocales
import dev.kilua.externals.toDate
import dev.kilua.externals.toLocalDateTime
import dev.kilua.form.DateTimeFormControl
import dev.kilua.i18n.DefaultLocale
import dev.kilua.i18n.Locale
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import dev.kilua.utils.toJsArray
import kotlinx.datetime.LocalDateTime
import web.JsAny
import web.document
import web.dom.events.Event
import web.toJsNumber
import web.toJsString

/**
 * Tempus Dominus rich date time component.
 */
public open class RichDateTime(
    value: LocalDateTime? = null,
    disabled: Boolean? = null,
    format: String = "yyyy-MM-dd HH:mm",
    inline: Boolean = false,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<LocalDateTime?> = WithStateFlowDelegateImpl()
) : AbstractRichDateTime(disabled, format, inline, locale, className, renderConfig), DateTimeFormControl,
    WithStateFlow<LocalDateTime?> by withStateFlowDelegate {

    public override var value: LocalDateTime? by updatingProperty(
        value,
        skipUpdate,
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
        onEventDirect<Event>("change.td") {
            val date = it["detail"]?.get("date")?.cast<dev.kilua.externals.Date>()
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

    @NoLiveLiterals
    override fun initializeTempusDominus() {
        if (renderConfig.isDom) {
            val calendarView = (format.contains("y") || format.contains("M") || format.contains("d"))
            val clockView = (format.contains("h", ignoreCase = true) || format.contains("mm") || format.contains("ss"))
            val secondsView = format.contains("ss")
            val language = locale.language
            val locale = tempusDominusLocales[language]?.localization ?: obj {}
            locale["locale"] = language.toJsString()
            locale["format"] = inputFormat.toJsString()
            if (monthHeaderFormat != null || yearHeaderFormat != null) {
                locale["dayViewHeaderFormat"] = obj<JsAny> {}.apply {
                    this["month"] = (if (monthHeaderFormat != null) monthHeaderFormat!!.value else "long").toJsString()
                    this["year"] = (if (yearHeaderFormat != null) yearHeaderFormat!!.value else "2-digit").toJsString()
                }
            }
            val initialViewMode = viewMode ?: if (calendarView) ViewMode.Calendar else ViewMode.Clock
            val currentTheme = if (theme == null || theme == Theme.Auto) {
                document.documentElement?.getAttribute("data-bs-theme")?.let { theme ->
                    Theme.entries.find { theme == it.value }
                }
            } else {
                theme
            }
            val component = this
            val tempusDominusOptions = obj<TempusDominusOptions> {
                this.useCurrent = component.inline
                if (component.value != null) {
                    this.defaultDate = component.value!!.toDate()
                }
                this.stepping = component.stepping
                this.allowInputToggle = component.allowInputToggle
                if (component.viewDate != null) {
                    this.viewDate = component.viewDate!!.toDate()
                } else {
                    if (inline && component.value != null) this.viewDate = component.value!!.toDate()
                }
                this.promptTimeOnDateChange = component.promptTimeOnDateChange
                if (component.promptTimeOnDateChangeTransitionDelay != null)
                    this.promptTimeOnDateChangeTransitionDelay =
                        component.promptTimeOnDateChangeTransitionDelay!!.inWholeMilliseconds.toInt()
                this.restrictions = obj {
                    if (component.minDate != null) this.minDate = component.minDate!!.toDate()
                    if (component.maxDate != null) this.maxDate = component.maxDate!!.toDate()
                    if (!component.enabledDates.isNullOrEmpty()) this.enabledDates =
                        component.enabledDates!!.map { it.toDate() }.toJsArray()
                    if (!component.disabledDates.isNullOrEmpty()) this.disabledDates =
                        component.disabledDates!!.map { it.toDate() }.toJsArray()
                    if (!component.daysOfWeekDisabled.isNullOrEmpty()) this.daysOfWeekDisabled =
                        component.daysOfWeekDisabled!!.map { it.toJsNumber() }.toJsArray()
                }
                this.display = obj {
                    this.viewMode = initialViewMode.value
                    component.toolbarPlacement?.let { this.toolbarPlacement = it.value }
                    this.sideBySide = component.sideBySide
                    this.buttons = obj {
                        this.clear = component.showClear
                        this.close = component.showClose
                        this.today = component.showToday
                    }
                    this.inline = component.inline
                    this.keepOpen = component.keepOpen
                    currentTheme?.let { this.theme = it.value }
                    this.components = obj {
                        this.calendar = calendarView
                        this.clock = clockView
                        this.seconds = secondsView
                    }
                }
                this.localization = locale
            }
            tempusDominusInstance = TempusDominus(element, tempusDominusOptions)
            if (disabled == true) {
                tempusDominusInstance?.disable()
            }
        }
    }

    public companion object {
        internal var idCounter: Int = 0
    }

}

@Composable
private fun ComponentBase.richDateTime(
    value: LocalDateTime? = null,
    disabled: Boolean? = null,
    format: String = "yyyy-MM-dd HH:mm",
    inline: Boolean = false,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    setup: @Composable RichDateTime.() -> Unit = {}
): RichDateTime {
    val component = remember { RichDateTime(value, disabled, format, inline, locale, className, renderConfig) }
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
    }, setup)
    return component
}


/**
 * Creates a [RichDateTime] component.
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
 * @return a [RichDateTime] component
 */
@Composable
public fun ComponentBase.richDateTime(
    value: LocalDateTime? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    id: String? = null,
    inline: Boolean = false,
    format: String = "yyyy-MM-dd HH:mm",
    locale: Locale = DefaultLocale(),
    className: String? = null,
    setup: @Composable RichDateTime.() -> Unit = {}
): RichDateTime {
    val bindId = remember { "kilua_tempus_dominus_rdt_${RichDateTime.idCounter++}" }
    return richDateTime(value, disabled, format, inline, locale, className = className % "input-group") {
        commonRichDateTime(bindId, name, placeholder, disabled, required, id, inline, "fas fa-calendar-alt")
        setup()
    }
}
