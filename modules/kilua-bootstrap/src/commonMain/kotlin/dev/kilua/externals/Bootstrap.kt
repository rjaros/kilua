@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

package dev.kilua.externals

import dev.kilua.JsModule
import web.JsAny
import web.dom.HTMLElement

/**
 * External Bootstrap object.
 */
@JsModule("bootstrap")
public external object Bootstrap : JsAny {
    /**
     * External Bootstrap Modal class.
     */
    public class Modal(element: HTMLElement) : JsAny {
        /**
         * Shows the modal.
         */
        public fun show()

        /**
         * Hides the modal.
         */
        public fun hide()

        /**
         * Toggles the visibility.
         */
        public fun toggle()

        /**
         * Disposes the modal.
         */
        public fun dispose()
    }

    /**
     * External Bootstrap Carousel class.
     */
    public class Carousel(element: HTMLElement) : JsAny {
        /**
         * Show next item.
         */
        public fun next()

        /**
         * Show previous item.
         */
        public fun prev()

        /**
         * Show an item identified by its index.
         */
        public fun to(index: Int)

        /**
         * Cycles through the carousel items from left to right.
         */
        public fun cycle()

        /**
         * Stops the carousel from cycling through items.
         */
        public fun pause()

        /**
         * Disposes the carousel.
         */
        public fun dispose()
    }

    /**
     * External Bootstrap Offcanvas class.
     */
    public class Offcanvas(element: HTMLElement) : JsAny {
        /**
         * Shows the offcanvas.
         */
        public fun show()

        /**
         * Hides the offcanvas.
         */
        public fun hide()

        /**
         * Toggles the visibility.
         */
        public fun toggle()

        /**
         * Disposes the offcanvas.
         */
        public fun dispose()
    }

    /**
     * External Bootstrap Tooltip delay configuration.
     */
    public class TooltipDelay : JsAny {
        public var show: Int?
        public var hide: Int?
    }

    /**
     * External Bootstrap Tooltip configuration.
     */
    public class TooltipConfig : JsAny {
        public var title: String?
        public var animation: Boolean?
        public var delay: TooltipDelay?
        public var placement: String?
        public var trigger: String?
        public var html: Boolean?
        public var sanitize: Boolean?
    }

    /**
     * External Bootstrap Tooltip class.
     */
    public class Tooltip(element: HTMLElement, config: TooltipConfig) : JsAny {
        /**
         * Shows the tooltip.
         */
        public fun show()

        /**
         * Hides the tooltip.
         */
        public fun hide()

        /**
         * Toggles the visibility.
         */
        public fun toggle()

        /**
         * Disposes the tooltip.
         */
        public fun dispose()

        /**
         * Enables the tooltip.
         */
        public fun enable()

        /**
         * Disables the tooltip.
         */
        public fun disable()

        public companion object {
            /**
             * Returns the tooltip instance associated with the given element or `null` if there is none.
             */
            public fun getInstance(element: HTMLElement): Tooltip?
        }
    }


    /**
     * External Bootstrap Popover delay configuration.
     */
    public class PopoverDelay : JsAny {
        public var show: Int?
        public var hide: Int?
    }

    /**
     * External Bootstrap Popover configuration.
     */
    public class PopoverConfig : JsAny {
        public var content: String?
        public var title: String?
        public var animation: Boolean?
        public var delay: PopoverDelay?
        public var placement: String?
        public var trigger: String?
        public var html: Boolean?
        public var sanitize: Boolean?
    }

    /**
     * External Bootstrap Popover class.
     */
    public class Popover(element: HTMLElement, config: PopoverConfig) : JsAny {
        /**
         * Shows the popover.
         */
        public fun show()

        /**
         * Hides the popover.
         */
        public fun hide()

        /**
         * Toggles the visibility.
         */
        public fun toggle()

        /**
         * Disposes the popover.
         */
        public fun dispose()

        /**
         * Enables the popover.
         */
        public fun enable()

        /**
         * Disables the popover.
         */
        public fun disable()

        public companion object {
            /**
             * Returns the popover instance associated with the given element or `null` if there is none.
             */
            public fun getInstance(element: HTMLElement): Popover?
        }
    }

    /**
     * External Bootstrap Toast class.
     */
    public class Toast(element: HTMLElement) : JsAny {
        /**
         * Shows the toast.
         */
        public fun show()

        /**
         * Hides the toast.
         */
        public fun hide()

        /**
         * Disposes the toast.
         */
        public fun dispose()

        public companion object {
            /**
             * Returns the toast instance associated with the given element or `null` if there is none.
             */
            public fun getInstance(element: HTMLElement): Toast?
        }
    }
}
