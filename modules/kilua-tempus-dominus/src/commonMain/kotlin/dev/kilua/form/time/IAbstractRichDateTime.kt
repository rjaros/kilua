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

package dev.kilua.form.time

import androidx.compose.runtime.Composable
import dev.kilua.externals.TempusDominus
import dev.kilua.form.Autocomplete
import dev.kilua.html.IDiv
import dev.kilua.i18n.Locale
import kotlinx.datetime.LocalDate
import js.core.JsAny
import kotlin.time.Duration

/**
 * Tempus Dominus base rich date time component interface.
 */
public interface IAbstractRichDateTime : IDiv {

    /**
     * The date/time format.
     */
    public val format: String

    /**
     * Set the date/time format.
     */
    @Composable
    public fun format(format: String)

    /**
     * The locale for i18n.
     */
    public val locale: Locale

    /**
     * Set the locale for i18n.
     */
    @Composable
    public fun locale(locale: Locale)

    /**
     * Determines if the component is disabled.
     */
    public val disabled: Boolean?

    /**
     * Set the component disabled state.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Determines if the component is required.
     */
    public val required: Boolean?

    /**
     * Set the component required state.
     */
    @Composable
    public fun required(required: Boolean?)

    /**
     * The component name.
     */
    public val name: String?

    /**
     * Set the component name.
     */
    @Composable
    public fun name(name: String?)

    /**
     * The autocomplete attribute of the generated HTML input element.
     */
    public val autocomplete: Autocomplete?

    /**
     * Set the autocomplete attribute of the generated HTML input element.
     */
    @Composable
    public fun autocomplete(autocomplete: Autocomplete?)

    /**
     * Days of the week that should be disabled.
     */
    public val daysOfWeekDisabled: List<Int>?

    /**
     * Set the days of the week that should be disabled.
     */
    @Composable
    public fun daysOfWeekDisabled(daysOfWeekDisabled: List<Int>?)

    /**
     * Determines if *Clear* button should be visible.
     */
    public val showClear: Boolean

    /**
     * Set if *Clear* button should be visible.
     */
    @Composable
    public fun showClear(showClear: Boolean)

    /**
     * Determines if *Close* button should be visible.
     */
    public val showClose: Boolean

    /**
     * Set if *Close* button should be visible.
     */
    @Composable
    public fun showClose(showClose: Boolean)

    /**
     * Determines if *Today* button should be visible.
     */
    public val showToday: Boolean

    /**
     * Set if *Today* button should be visible.
     */
    @Composable
    public fun showToday(showToday: Boolean)

    /**
     * The increment used to build the hour view.
     */
    public val stepping: Int

    /**
     * Set the increment used to build the hour view.
     */
    @Composable
    public fun stepping(stepping: Int)

    /**
     * Prevents date selection before this date.
     */
    public val minDate: LocalDate?

    /**
     * Set the minimum date.
     */
    @Composable
    public fun minDate(minDate: LocalDate?)

    /**
     * Prevents date selection after this date.
     */
    public val maxDate: LocalDate?

    /**
     * Set the maximum date.
     */
    @Composable
    public fun maxDate(maxDate: LocalDate?)

    /**
     * Shows date and time pickers side by side.
     */
    public val sideBySide: Boolean

    /**
     * Set if date and time pickers should be shown side by side.
     */
    @Composable
    public fun sideBySide(sideBySide: Boolean)

    /**
     * A list of enabled dates.
     */
    public val enabledDates: List<LocalDate>?

    /**
     * Set the list of enabled dates.
     */
    @Composable
    public fun enabledDates(enabledDates: List<LocalDate>?)

    /**
     * A list of disabled dates.
     */
    public val disabledDates: List<LocalDate>?

    /**
     * Set the list of disabled dates.
     */
    @Composable
    public fun disabledDates(disabledDates: List<LocalDate>?)

    /**
     * A list of enabled hours.
     */
    public val enabledHours: List<Int>?

    /**
     * Set the list of enabled hours.
     */
    @Composable
    public fun enabledHours(enabledHours: List<Int>?)

    /**
     * A list of disabled hours.
     */
    public val disabledHours: List<Int>?

    /**
     * Set the list of disabled hours.
     */
    @Composable
    public fun disabledHours(disabledHours: List<Int>?)

    /**
     * Keep the popup open after selecting a date.
     */
    public val keepOpen: Boolean

    /**
     * Set if the popup should be kept open after selecting a date.
     */
    @Composable
    public fun keepOpen(keepOpen: Boolean)

    /**
     * Date/time chooser color theme.
     */
    public val theme: Theme?

    /**
     * Set the date/time chooser color theme.
     */
    @Composable
    public fun theme(theme: Theme?)

    /**
     * Automatically open the chooser popup.
     */
    public val allowInputToggle: Boolean

    /**
     * Set if the chooser popup should be automatically opened.
     */
    @Composable
    public fun allowInputToggle(allowInputToggle: Boolean)

    /**
     * The view date of the date/time chooser.
     */
    public val viewDate: LocalDate?

    /**
     * Set the view date of the date/time chooser.
     */
    @Composable
    public fun viewDate(viewDate: LocalDate?)

    /**
     * Automatically open time component after date is selected.
     */
    public val promptTimeOnDateChange: Boolean

    /**
     * Set if time component should be automatically opened after date is selected.
     */
    @Composable
    public fun promptTimeOnDateChange(promptTimeOnDateChange: Boolean)

    /**
     * The delay for the time component opening after date is selected.
     */
    public val promptTimeOnDateChangeTransitionDelay: Duration?

    /**
     * Set the delay for the time component opening after date is selected.
     */
    @Composable
    public fun promptTimeOnDateChangeTransitionDelay(promptTimeOnDateChangeTransitionDelay: Duration?)

    /**
     * Default view mode of the date/time chooser.
     */
    public val viewMode: ViewMode?

    /**
     * Set the default view mode of the date/time chooser.
     */
    @Composable
    public fun viewMode(viewMode: ViewMode?)

    /**
     * Date/time chooser toolbar placement.
     */
    public val toolbarPlacement: ToolbarPlacement?

    /**
     * Set the date/time chooser toolbar placement.
     */
    @Composable
    public fun toolbarPlacement(toolbarPlacement: ToolbarPlacement?)

    /**
     * Date/time chooser month header format.
     */
    public val monthHeaderFormat: MonthHeaderFormat?

    /**
     * Set the date/time chooser month header format.
     */
    @Composable
    public fun monthHeaderFormat(monthHeaderFormat: MonthHeaderFormat?)

    /**
     * Date/time chooser year header format.
     */
    public val yearHeaderFormat: YearHeaderFormat?

    /**
     * Set the date/time chooser year header format.
     */
    @Composable
    public fun yearHeaderFormat(yearHeaderFormat: YearHeaderFormat?)

    /**
     * Date/time chooser custom icons.
     */
    public val customIcons: DateTimeIcons?

    /**
     * Set the date/time chooser custom icons.
     */
    @Composable
    public fun customIcons(customIcons: DateTimeIcons?)

    /**
     * Allows to select a date that is invalid according to the rules.
     */
    public val keepInvalid: Boolean

    /**
     * Set to allow selecting a date that is invalid according to the rules.
     */
    @Composable
    public fun keepInvalid(keepInvalid: Boolean)

    /**
     * The target container to use for the widget instead of body.
     */
    public val container: JsAny?

    /**
     * Set the target container to use for the widget instead of body.
     */
    @Composable
    public fun container(container: JsAny?)

    /**
     * The extra information about the date picker.
     */
    public val meta: JsAny?

    /**
     * Set the extra information about the date picker.
     */
    @Composable
    public fun meta(meta: JsAny?)

    /**
     * Displays an additional column with the calendar week.
     */
    public val calendarWeeks: Boolean

    /**
     * Set if an additional column with the calendar week should be displayed.
     */
    @Composable
    public fun calendarWeeks(calendarWeeks: Boolean)

    /**
     * The hour cycle. If not set it's determined by the locale.
     */
    public val hourCycle: HourCycle?

    /**
     * Set the hour cycle. If not set it's determined by the locale.
     */
    @Composable
    public fun hourCycle(hourCycle: HourCycle?)

    /**
     * Specifies whether the picker should allow keyboard navigation.
     */
    public val keyboardNavigation: Boolean

    /**
     * Specifies whether the picker should allow keyboard navigation.
     */
    @Composable
    public fun keyboardNavigation(keyboardNavigation: Boolean)

    /**
     * The Tempus Dominus instance.
     */
    public val tempusDominusInstance: TempusDominus?

}
