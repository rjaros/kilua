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

package dev.kilua.routing

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
public enum class Changefreq {
    Always,
    Hourly,
    Daily,
    Weekly,
    Monthly,
    Yearly,
    Never;
}

/**
 * Represents sitemap metadata for a web page.
 */
@Serializable
public data class Sitemap(
    public var loc: String? = null,
    public var lastmod: LocalDate? = null,
    public var changefreq: Changefreq? = null,
    public var priority: Double? = null
)

/**
 * Represents an Open Graph image with optional width, height, and alt text.
 */
@Serializable
public data class OpenGraphImage(
    public var url: String,
    public var width: Int? = null,
    public var height: Int? = null,
    public var alt: String? = null
)

/**
 * Represents Open Graph metadata for a web page.
 */
@Serializable
public data class OpenGraph(
    public var title: String? = null,
    public var description: String? = null,
    public var images: List<OpenGraphImage>? = null,
    public var type: String? = null,
    public var url: String? = null,
    public var locale: String? = null,
    public var siteName: String? = null
)

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
 * Represents metadata for a web page, including title, description, author, and more.
 */
public interface Meta {
    public var title: String?
    public var titleDefault: String?
    public var titleTemplate: String?
    public var titleAbsolute: String?
    public var description: String?
    public var generator: String?
    public var author: String?
    public var creator: String?
    public var publisher: String?
    public var applicationName: String?
    public var referrer: String?
    public var keywords: List<String>?
    public var themeColor: String?
    public var colorScheme: String?
    public var formatDetectionEmail: Boolean?
    public var formatDetectionTelephone: Boolean?
    public var formatDetectionAddress: Boolean?
    public var icon: String?
    public var sitemap: Sitemap?
    public var shortcutIcon: String?
    public var openGraph: OpenGraph?
    public var robots: Robots?
    public val customMeta: Map<String, String>

    /**
     * Adds a custom meta tag with the specified name and value.
     *
     * @param name the name of the custom meta tag
     * @param value the value of the custom meta tag
     */
    public fun custom(name: String, value: String)

    /**
     * A composable function to add page metadata.
     */
    public fun view(view: @Composable @RoutingDsl Meta.() -> Unit)

    public companion object {
        /**
         * The page metadata for the current route.
         */
        public var current: Meta? = null
            internal set
    }
}

/**
 * Default implementation of the [Meta] interface.
 */
@Serializable
public class MetaImpl : Meta {
    override var title: String? = null
    override var titleDefault: String? = null
    override var titleTemplate: String? = null
    override var titleAbsolute: String? = null
    override var description: String? = null
    override var generator: String? = null
    override var author: String? = null
    override var creator: String? = null
    override var publisher: String? = null
    override var applicationName: String? = null
    override var referrer: String? = null
    override var keywords: List<String>? = null
    override var themeColor: String? = null
    override var colorScheme: String? = null
    override var formatDetectionEmail: Boolean? = null
    override var formatDetectionTelephone: Boolean? = null
    override var formatDetectionAddress: Boolean? = null
    override var icon: String? = null
    override var shortcutIcon: String? = null
    override var sitemap: Sitemap? = null
    override var openGraph: OpenGraph? = null
    override var robots: Robots? = null
    override val customMeta: MutableMap<String, String> = mutableMapOf()

    public var view: (@Composable @RoutingDsl Meta.() -> Unit)? = null

    override fun custom(name: String, value: String) {
        customMeta[name] = value
    }

    override fun view(view: @Composable @RoutingDsl Meta.() -> Unit) {
        this.view = view
    }

    override fun toString(): String {
        return buildString {
            append("Meta(")
            if (title != null) append("title=$title, ")
            if (titleDefault != null) append("titleDefault=$titleDefault, ")
            if (titleTemplate != null) append("titleTemplate=$titleTemplate, ")
            if (titleAbsolute != null) append("titleAbsolute=$titleAbsolute, ")
            if (description != null) append("description=$description, ")
            if (generator != null) append("generator=$generator, ")
            if (author != null) append("author=$author, ")
            if (creator != null) append("creator=$creator, ")
            if (publisher != null) append("publisher=$publisher, ")
            if (applicationName != null) append("applicationName=$applicationName, ")
            if (referrer != null) append("referrer=$referrer, ")
            if (keywords != null) append("keywords=${keywords?.joinToString(", ")}, ")
            if (themeColor != null) append("themeColor=$themeColor, ")
            if (colorScheme != null) append("colorScheme=$colorScheme, ")
            if (formatDetectionEmail != null) append("formatDetectionEmail=$formatDetectionEmail, ")
            if (formatDetectionTelephone != null) append("formatDetectionTelephone=$formatDetectionTelephone, ")
            if (formatDetectionAddress != null) append("formatDetectionAddress=$formatDetectionAddress, ")
            if (icon != null) append("icon=$icon, ")
            if (shortcutIcon != null) append("shortcutIcon=$shortcutIcon, ")
            if (sitemap != null) append("sitemap=$sitemap, ")
            if (openGraph != null) append("openGraph=$openGraph, ")
            if (robots != null) append("robots=$robots, ")
            if (customMeta.isNotEmpty()) {
                append("customMeta={")
                customMeta.entries.joinTo(this, ", ") { "${it.key}=${it.value}" }
                append("}, ")
            }
            append(")")
        }
    }

    /**
     * Converts the metadata to a JSON string.
     */
    public fun toJson(): String {
        return Json.encodeToString(this)
    }
}

/**
 * Merges the given metadata with the parent metadata and the default metadata.
 */
internal fun mergeMetadataWithDefault(meta: Meta?, parentMeta: Meta?, defaultMeta: Meta?): Meta? {
    return if (meta == null && parentMeta == null && defaultMeta == null) {
        null
    } else {
        MetaImpl().apply {
            title = meta?.title ?: defaultMeta?.title
            titleDefault = meta?.titleDefault ?: parentMeta?.titleDefault ?: defaultMeta?.titleDefault
            titleTemplate = meta?.titleTemplate ?: parentMeta?.titleTemplate ?: defaultMeta?.titleTemplate
            titleAbsolute = meta?.titleAbsolute
            description = meta?.description ?: defaultMeta?.description
            generator = meta?.generator ?: defaultMeta?.generator
            author = meta?.author ?: defaultMeta?.author
            creator = meta?.creator ?: defaultMeta?.creator
            publisher = meta?.publisher ?: defaultMeta?.publisher
            applicationName = meta?.applicationName ?: defaultMeta?.applicationName
            referrer = meta?.referrer ?: defaultMeta?.referrer
            keywords = meta?.keywords ?: defaultMeta?.keywords
            themeColor = meta?.themeColor ?: defaultMeta?.themeColor
            colorScheme = meta?.colorScheme ?: defaultMeta?.colorScheme
            formatDetectionEmail = meta?.formatDetectionEmail ?: defaultMeta?.formatDetectionEmail
            formatDetectionTelephone = meta?.formatDetectionTelephone ?: defaultMeta?.formatDetectionTelephone
            formatDetectionAddress = meta?.formatDetectionAddress ?: defaultMeta?.formatDetectionAddress
            icon = meta?.icon ?: defaultMeta?.icon
            shortcutIcon = meta?.shortcutIcon ?: defaultMeta?.shortcutIcon
            sitemap = meta?.sitemap ?: defaultMeta?.sitemap
            openGraph = meta?.openGraph ?: defaultMeta?.openGraph
            robots = meta?.robots ?: defaultMeta?.robots
            if (defaultMeta != null) {
                customMeta.putAll(defaultMeta.customMeta)
            }
            if (meta != null) {
                customMeta.putAll(meta.customMeta)
            }
        }
    }
}

/**
 * Merges the given metadata with the parent metadata.
 */
internal fun mergeMetadataWithParent(meta: Meta?, parentMeta: Meta?): Meta? {
    return if (meta == null && parentMeta == null) {
        null
    } else {
        MetaImpl().apply {
            titleDefault = meta?.titleDefault ?: parentMeta?.titleDefault
            titleTemplate = meta?.titleTemplate ?: parentMeta?.titleTemplate
            if (meta != null) {
                title = meta.title
                titleAbsolute = meta.titleAbsolute
                description = meta.description
                generator = meta.generator
                author = meta.author
                creator = meta.creator
                publisher = meta.publisher
                applicationName = meta.applicationName
                referrer = meta.referrer
                keywords = meta.keywords
                themeColor = meta.themeColor
                colorScheme = meta.colorScheme
                formatDetectionEmail = meta.formatDetectionEmail
                formatDetectionTelephone = meta.formatDetectionTelephone
                formatDetectionAddress = meta.formatDetectionAddress
                icon = meta.icon
                shortcutIcon = meta.shortcutIcon
                sitemap = meta.sitemap
                openGraph = meta.openGraph
                robots = meta.robots
                customMeta.putAll(meta.customMeta)
            }
        }
    }
}
