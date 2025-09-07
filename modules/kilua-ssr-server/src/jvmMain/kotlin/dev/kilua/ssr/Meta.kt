/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.ssr

import kotlinx.serialization.Serializable

/**
 * Represents sitemap metadata for a web page.
 */
@Serializable
public data class Sitemap(
    public var loc: String,
    public var lastmod: String? = null,
    public var changefreq: String? = null,
    public var priority: Double? = null
)

/**
 * Represents an Open Graph image with optional width, height, and alt text.
 */
@Serializable
public data class OpenGraphImage(
    public val url: String,
    public val width: Int? = null,
    public val height: Int? = null,
    public val alt: String? = null
)

/**
 * Converts an OpenGraphImage instance to an HTML string representation.
 */
public fun OpenGraphImage.toHtml(): String {
    return buildString {
        append("    <meta property=\"og:image\" content=\"$url\">\n")
        if (width != null) {
            append("    <meta property=\"og:image:width\" content=\"$width\">\n")
        }
        if (height != null) {
            append("    <meta property=\"og:image:height\" content=\"$height\">\n")
        }
        if (alt != null) {
            append("    <meta property=\"og:image:alt\" content=\"$alt\">\n")
        }
    }
}

/**
 * Represents Open Graph metadata for a web page.
 */
@Serializable
public data class OpenGraph(
    public val title: String? = null,
    public val description: String? = null,
    public val images: List<OpenGraphImage>? = null,
    public val type: String? = null,
    public val url: String? = null,
    public val locale: String? = null,
    public val siteName: String? = null
)

/**
 * Converts an OpenGraph instance to an HTML string representation.
 */
public fun OpenGraph.toHtml(): String {
    return buildString {
        if (title != null) {
            append("    <meta property=\"og:title\" content=\"$title\">\n")
        }
        if (description != null) {
            append("    <meta property=\"og:description\" content=\"$description\">\n")
        }
        if (images != null && images.isNotEmpty()) {
            images.forEach { append(it.toHtml()) }
        }
        if (type != null) {
            append("    <meta property=\"og:type\" content=\"$type\">\n")
        }
        if (url != null) {
            append("    <meta property=\"og:url\" content=\"$url\">\n")
        }
        if (locale != null) {
            append("    <meta property=\"og:locale\" content=\"$locale\">\n")
        }
        if (siteName != null) {
            append("    <meta property=\"og:site_name\" content=\"$siteName\">\n")
        }
    }
}

/**
 * Represents robots meta tags for controlling indexing and crawling behavior.
 */
@Serializable
public data class Robots(
    public var noindex: Boolean = false,
    public var nofollow: Boolean = false,
    public var noarchive: Boolean = false,
    public var nosnippet: Boolean = false,
    public var noimageindex: Boolean = false
)

/**
 * Converts a Robots instance to an HTML string representation.
 */
public fun Robots.toHtml(): String {
    val flags = buildList {
        if (noindex) add("noindex")
        if (nofollow) add("nofollow")
        if (noarchive) add("noarchive")
        if (nosnippet) add("nosnippet")
        if (noimageindex) add("noimageindex")
    }.joinToString(", ")
    return "    <meta name=\"robots\" content=\"$flags\">\n"
}

/**
 * Represents metadata for a web page, including title, description, author, and more.
 */
@Serializable
public data class Meta(
    val title: String? = null,
    val titleDefault: String? = null,
    val titleTemplate: String? = null,
    val titleAbsolute: String? = null,
    val description: String? = null,
    val generator: String? = null,
    val author: String? = null,
    val creator: String? = null,
    val publisher: String? = null,
    val applicationName: String? = null,
    val referrer: String? = null,
    val keywords: List<String>? = null,
    val themeColor: String? = null,
    val colorScheme: String? = null,
    val formatDetectionEmail: Boolean? = null,
    val formatDetectionTelephone: Boolean? = null,
    val formatDetectionAddress: Boolean? = null,
    val icon: String? = null,
    val shortcutIcon: String? = null,
    val openGraph: OpenGraph? = null,
    val robots: Robots? = null,
    val customMeta: Map<String, String>? = null,
)

/**
 * Converts a Meta instance to an HTML string representation.
 */
public fun Meta.toHtml(): String {
    return buildString {
        if (titleAbsolute != null) {
            append("    <title>$titleAbsolute</title>\n")
        } else if (titleTemplate != null && titleDefault != null) {
            append("    <title>${titleTemplate.replace("%s", titleDefault)}</title>\n")
        } else if (titleTemplate != null && title != null) {
            append("    <title>${titleTemplate.replace("%s", title)}</title>\n")
        } else if (titleDefault != null) {
            append("    <title>$titleDefault</title>\n")
        } else if (title != null) {
            append("    <title>$title</title>\n")
        }
        if (description != null) {
            append("    <meta name=\"description\" content=\"$description\">\n")
        }
        if (generator != null) {
            append("    <meta name=\"generator\" content=\"$generator\">\n")
        }
        if (author != null) {
            append("    <meta name=\"author\" content=\"$author\">\n")
        }
        if (creator != null) {
            append("    <meta name=\"creator\" content=\"$creator\">\n")
        }
        if (publisher != null) {
            append("    <meta name=\"publisher\" content=\"$publisher\">\n")
        }
        if (applicationName != null) {
            append("    <meta name=\"application-name\" content=\"$applicationName\">\n")
        }
        if (referrer != null) {
            append("    <meta name=\"referrer\" content=\"$referrer\">\n")
        }
        if (keywords != null && keywords.isNotEmpty()) {
            append("    <meta name=\"keywords\" content=\"${keywords.joinToString(", ")}\">\n")
        }
        if (themeColor != null) {
            append("    <meta name=\"theme-color\" content=\"$themeColor\">\n")
        }
        if (colorScheme != null) {
            append("    <meta name=\"color-scheme\" content=\"$colorScheme\">\n")
        }
        if (formatDetectionEmail != null || formatDetectionTelephone != null || formatDetectionAddress != null) {
            val content = buildList {
                formatDetectionEmail?.let { add("email=${if (it) "yes" else "no"}") }
                formatDetectionTelephone?.let { add("telephone=${if (it) "yes" else "no"}") }
                formatDetectionAddress?.let { add("address=${if (it) "yes" else "no"}") }
            }.joinToString(", ")
            append("    <meta name=\"format-detection\" content=\"$content\">\n")
        }
        if (icon != null) {
            append("    <link rel=\"icon\" href=\"$icon\">\n")
        }
        if (shortcutIcon != null) {
            append("    <link rel=\"shortcut icon\" href=\"$shortcutIcon\">\n")
        }
        if (openGraph != null) {
            append(openGraph.toHtml())
        }
        if (robots != null) {
            append(robots.toHtml())
        }
        if (customMeta != null && customMeta.isNotEmpty()) {
            customMeta.forEach { (name, value) ->
                append("    <meta name=\"$name\" content=\"$value\">\n")
            }
        }
    }
}
