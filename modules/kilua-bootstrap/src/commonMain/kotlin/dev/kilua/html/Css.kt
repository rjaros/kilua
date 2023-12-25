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

package dev.kilua.html

import dev.kilua.utils.toKebabCase

/**
 * Bootstrap border CSS classes.
 */
public enum class BsBorder {
    Border,
    BorderTop,
    BorderBottom,
    BorderEnd,
    BorderStart,
    Border0,
    BorderTop0,
    BorderBottom0,
    BorderEnd0,
    BorderStart0,
    BorderPrimary,
    BorderPrimarySubtle,
    BorderSecondary,
    BorderSecondarySubtle,
    BorderSuccess,
    BorderSuccessSubtle,
    BorderDanger,
    BorderDangerSubtle,
    BorderWarning,
    BorderWarningSubtle,
    BorderInfo,
    BorderInfoSubtle,
    BorderLight,
    BorderLightSubtle,
    BorderDark,
    BorderDarkSubtle,
    BorderWhite,
    BorderBlack;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Bootstrap rounded css classes.
 */
public enum class BsRounded {
    Rounded,
    RoundedTop,
    RoundedBottom,
    RoundedStart,
    RoundedEnd,
    RoundedCircle,
    RoundedPill,
    Rounded0,
    Rounded1,
    Rounded2,
    Rounded3,
    Rounded4,
    Rounded5;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Bootstrap color css classes.
 */
public enum class BsColor {
    TextPrimary,
    TextPrimaryEmphasis,
    TextSecondary,
    TextSecondaryEmphasis,
    TextSuccess,
    TextSuccessEmphasis,
    TextDanger,
    TextDangerEmphasis,
    TextWarning,
    TextWarningEmphasis,
    TextInfo,
    TextInfoEmphasis,
    TextLight,
    TextLightEmphasis,
    TextDark,
    TextDarkEmphasis,
    TextBlack,
    TextWhite,
    TextBody,
    TextBodySecondary,
    TextBodyTertiary,
    TextBodyEmphasis,
    TextBlack50,
    TextWhite50,
    TextBgPrimary,
    TextBgSecondary,
    TextBgSuccess,
    TextBgDanger,
    TextBgWarning,
    TextBgInfo,
    TextBgLight,
    TextBgDark;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Bootstrap background color css classes.
 */
public enum class BsBgColor {
    BgPrimary,
    BgPrimarySubtle,
    BgSecondary,
    BgSecondarySubtle,
    BgSuccess,
    BgSuccessSubtle,
    BgDanger,
    BgDangerSubtle,
    BgWarning,
    BgWarningSubtle,
    BgInfo,
    BgInfoSubtle,
    BgLight,
    BgLightSubtle,
    BgDark,
    BgDarkSubtle,
    BgBody,
    BgBodySecondary,
    BgBodyTertiary,
    BgBlack,
    BgWhite,
    BgTransparent;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}
