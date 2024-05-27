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

package dev.kilua.html

import dev.kilua.annotations.SimpleHtmlComponent
import web.dom.*

/**
 * HTML Abbr component.
 */
@SimpleHtmlComponent("abbr")
public interface IAbbr : ITag<HTMLElement>

/**
 * HTML Address component.
 */
@SimpleHtmlComponent("address")
public interface IAddress : ITag<HTMLElement>

/**
 * HTML Area component.
 */
@SimpleHtmlComponent("area")
public interface IArea : ITag<HTMLAreaElement>

/**
 * HTML Article component.
 */
@SimpleHtmlComponent("article")
public interface IArticle : ITag<HTMLElement>

/**
 * HTML Aside component.
 */
@SimpleHtmlComponent("aside")
public interface IAside : ITag<HTMLElement>

/**
 * HTML Audio component.
 */
@SimpleHtmlComponent("audio")
public interface IAudio : ITag<HTMLAudioElement>

/**
 * HTML B component.
 */
@SimpleHtmlComponent("b", withText = true)
public interface IB : ITag<HTMLElement>

/**
 * HTML Base component.
 */
@SimpleHtmlComponent("base")
public interface IBase : ITag<HTMLBaseElement>

/**
 * HTML Bdi component.
 */
@SimpleHtmlComponent("bdi")
public interface IBdi : ITag<HTMLElement>

/**
 * HTML Bdo component.
 */
@SimpleHtmlComponent("bdo")
public interface IBdo : ITag<HTMLElement>

/**
 * HTML Blockquote component.
 */
@SimpleHtmlComponent("blockquote")
public interface IBlockquote : ITag<HTMLQuoteElement>

/**
 * HTML Body component.
 */
@SimpleHtmlComponent("body")
public interface IBody : ITag<HTMLBodyElement>

/**
 * HTML Br component.
 */
@SimpleHtmlComponent("br")
public interface IBr : ITag<HTMLBRElement>

/**
 * HTML Caption component.
 */
@SimpleHtmlComponent("caption")
public interface ICaption : ITag<HTMLElement>

/**
 * HTML Cite component.
 */
@SimpleHtmlComponent("cite")
public interface ICite : ITag<HTMLQuoteElement>

/**
 * HTML Code component.
 */
@SimpleHtmlComponent("code")
public interface ICode : ITag<HTMLElement>

/**
 * HTML Col component.
 */
@SimpleHtmlComponent("col")
public interface ICol : ITag<HTMLElement>

/**
 * HTML Colgroup component.
 */
@SimpleHtmlComponent("colgroup")
public interface IColgroup : ITag<HTMLElement>

/**
 * HTML Data component.
 */
@SimpleHtmlComponent("data")
public interface IData : ITag<HTMLDataElement>

/**
 * HTML Datalist component.
 */
@SimpleHtmlComponent("datalist")
public interface IDatalist : ITag<HTMLDataListElement>

/**
 * HTML Dd component.
 */
@SimpleHtmlComponent("dd")
public interface IDd : ITag<HTMLElement>

/**
 * HTML Del component.
 */
@SimpleHtmlComponent("del")
public interface IDel : ITag<HTMLElement>

/**
 * HTML Details component.
 */
@SimpleHtmlComponent("details")
public interface IDetails : ITag<HTMLDetailsElement>

/**
 * HTML Dfn component.
 */
@SimpleHtmlComponent("dfn")
public interface IDfn : ITag<HTMLElement>

/**
 * HTML Dialog component.
 */
@SimpleHtmlComponent("dialog")
public interface IDialog : ITag<HTMLDialogElement>

/**
 * HTML Div component.
 */
@SimpleHtmlComponent("div", withText = true)
public interface IDiv : ITag<HTMLDivElement>

/**
 * HTML Dl component.
 */
@SimpleHtmlComponent("dl")
public interface IDl : ITag<HTMLDListElement>

/**
 * HTML Dt component.
 */
@SimpleHtmlComponent("dt")
public interface IDt : ITag<HTMLElement>

/**
 * HTML Em component.
 */
@SimpleHtmlComponent("em", withText = true)
public interface IEm : ITag<HTMLElement>

/**
 * HTML Embed component.
 */
@SimpleHtmlComponent("embed")
public interface IEmbed : ITag<HTMLEmbedElement>

/**
 * HTML Fieldset component.
 */
@SimpleHtmlComponent("fieldset")
public interface IFieldset : ITag<HTMLFieldSetElement>

/**
 * HTML Figcaption component.
 */
@SimpleHtmlComponent("figcaption")
public interface IFigcaption : ITag<HTMLElement>

/**
 * HTML Figure component.
 */
@SimpleHtmlComponent("figure")
public interface IFigure : ITag<HTMLElement>

/**
 * HTML Footer component.
 */
@SimpleHtmlComponent("footer")
public interface IFooter : ITag<HTMLElement>

/**
 * HTML H1 component.
 */
@SimpleHtmlComponent("h1", withText = true)
public interface IH1 : ITag<HTMLHeadingElement>

/**
 * HTML H2 component.
 */
@SimpleHtmlComponent("h2", withText = true)
public interface IH2 : ITag<HTMLHeadingElement>

/**
 * HTML H3 component.
 */
@SimpleHtmlComponent("h3", withText = true)
public interface IH3 : ITag<HTMLHeadingElement>

/**
 * HTML H4 component.
 */
@SimpleHtmlComponent("h4", withText = true)
public interface IH4 : ITag<HTMLHeadingElement>

/**
 * HTML H5 component.
 */
@SimpleHtmlComponent("h5", withText = true)
public interface IH5 : ITag<HTMLHeadingElement>

/**
 * HTML H6 component.
 */
@SimpleHtmlComponent("h6", withText = true)
public interface IH6 : ITag<HTMLHeadingElement>

/**
 * HTML Head component.
 */
@SimpleHtmlComponent("head")
public interface IHead : ITag<HTMLHeadElement>

/**
 * HTML Header component.
 */
@SimpleHtmlComponent("header")
public interface IHeader : ITag<HTMLElement>

/**
 * HTML Hgroup component.
 */
@SimpleHtmlComponent("hgroup")
public interface IHgroup : ITag<HTMLElement>

/**
 * HTML Hr component.
 */
@SimpleHtmlComponent("hr")
public interface IHr : ITag<HTMLHRElement>

/**
 * HTML Html component.
 */
@SimpleHtmlComponent("html")
public interface IHtml : ITag<HTMLHtmlElement>

/**
 * HTML I component.
 */
@SimpleHtmlComponent("i", withText = true)
public interface II : ITag<HTMLElement>

/**
 * HTML Ins component.
 */
@SimpleHtmlComponent("ins")
public interface IIns : ITag<HTMLElement>

/**
 * HTML Kbd component.
 */
@SimpleHtmlComponent("kbd")
public interface IKbd : ITag<HTMLElement>

/**
 * HTML Legend component.
 */
@SimpleHtmlComponent("legend")
public interface ILegend : ITag<HTMLLegendElement>

/**
 * HTML Li component.
 */
@SimpleHtmlComponent("li", withText = true)
public interface ILi : ITag<HTMLLIElement>

/**
 * HTML Link component.
 */
@SimpleHtmlComponent("link")
public interface ILinkTag : ITag<HTMLLinkElement>

/**
 * HTML Main component.
 */
@SimpleHtmlComponent("main")
public interface IMain : ITag<HTMLElement>

/**
 * HTML Map component.
 */
@SimpleHtmlComponent("map")
public interface IMapTag : ITag<HTMLMapElement>

/**
 * HTML Mark component.
 */
@SimpleHtmlComponent("mark")
public interface IMark : ITag<HTMLElement>

/**
 * HTML Menu component.
 */
@SimpleHtmlComponent("menu")
public interface IMenu : ITag<HTMLMenuElement>

/**
 * HTML Meta component.
 */
@SimpleHtmlComponent("meta")
public interface IMeta : ITag<HTMLMetaElement>

/**
 * HTML Meter component.
 */
@SimpleHtmlComponent("meter")
public interface IMeter : ITag<HTMLMeterElement>

/**
 * HTML Nav component.
 */
@SimpleHtmlComponent("nav")
public interface INav : ITag<HTMLElement>

/**
 * HTML Noscript component.
 */
@SimpleHtmlComponent("noscript")
public interface INoscript : ITag<HTMLElement>

/**
 * HTML Object tag component.
 */
@SimpleHtmlComponent("object")
public interface IObjectTag : ITag<HTMLObjectElement>

/**
 * HTML Output component.
 */
@SimpleHtmlComponent("output")
public interface IOutput : ITag<HTMLOutputElement>

/**
 * HTML P component.
 */
@SimpleHtmlComponent("p", withText = true)
public interface IP : ITag<HTMLParagraphElement>

/**
 * HTML Picture component.
 */
@SimpleHtmlComponent("picture")
public interface IPicture : ITag<HTMLPictureElement>

/**
 * HTML Portal component.
 */
@SimpleHtmlComponent("portal")
public interface IPortal : ITag<HTMLElement>

/**
 * HTML Pre component.
 */
@SimpleHtmlComponent("pre", withText = true)
public interface IPre : ITag<HTMLPreElement>

/**
 * HTML Progress component.
 */
@SimpleHtmlComponent("progress")
public interface IProgress : ITag<HTMLProgressElement>

/**
 * HTML Q component.
 */
@SimpleHtmlComponent("q")
public interface IQ : ITag<HTMLQuoteElement>

/**
 * HTML Rp component.
 */
@SimpleHtmlComponent("rp")
public interface IRp : ITag<HTMLElement>

/**
 * HTML Rt component.
 */
@SimpleHtmlComponent("rt")
public interface IRt : ITag<HTMLElement>

/**
 * HTML Ruby component.
 */
@SimpleHtmlComponent("ruby")
public interface IRuby : ITag<HTMLElement>

/**
 * HTML S component.
 */
@SimpleHtmlComponent("s", withText = true)
public interface IS : ITag<HTMLElement>

/**
 * HTML Samp component.
 */
@SimpleHtmlComponent("samp")
public interface ISamp : ITag<HTMLElement>

/**
 * HTML Script component.
 */
@SimpleHtmlComponent("script")
public interface IScript : ITag<HTMLScriptElement>

/**
 * HTML Search component.
 */
@SimpleHtmlComponent("search")
public interface ISearch : ITag<HTMLElement>

/**
 * HTML Section component.
 */
@SimpleHtmlComponent("section")
public interface ISection : ITag<HTMLElement>

/**
 * HTML Slot component.
 */
@SimpleHtmlComponent("slot")
public interface ISlot : ITag<HTMLSlotElement>

/**
 * HTML Small component.
 */
@SimpleHtmlComponent("small", withText = true)
public interface ISmall : ITag<HTMLElement>

/**
 * HTML Source component.
 */
@SimpleHtmlComponent("source")
public interface ISource : ITag<HTMLSourceElement>

/**
 * HTML Span component.
 */
@SimpleHtmlComponent("span", withText = true)
public interface ISpan : ITag<HTMLSpanElement>

/**
 * HTML Strong component.
 */
@SimpleHtmlComponent("strong", withText = true)
public interface IStrong : ITag<HTMLElement>

/**
 * HTML Sub component.
 */
@SimpleHtmlComponent("sub", withText = true)
public interface ISub : ITag<HTMLElement>

/**
 * HTML Summary component.
 */
@SimpleHtmlComponent("summary")
public interface ISummary : ITag<HTMLElement>

/**
 * HTML Sup component.
 */
@SimpleHtmlComponent("sup", withText = true)
public interface ISup : ITag<HTMLElement>

/**
 * HTML Table component.
 */
@SimpleHtmlComponent("table")
public interface ITable : ITag<HTMLTableElement>

/**
 * HTML Tbody component.
 */
@SimpleHtmlComponent("tbody")
public interface ITbody : ITag<HTMLTableSectionElement>

/**
 * HTML Template component.
 */
@SimpleHtmlComponent("template")
public interface ITemplate : ITag<HTMLTemplateElement>

/**
 * HTML Tfoot component.
 */
@SimpleHtmlComponent("tfoot")
public interface ITfoot : ITag<HTMLTableSectionElement>

/**
 * HTML Thead component.
 */
@SimpleHtmlComponent("thead")
public interface IThead : ITag<HTMLTableSectionElement>

/**
 * HTML Time component.
 */
@SimpleHtmlComponent("time")
public interface ITime : ITag<HTMLTimeElement>

/**
 * HTML Title component.
 */
@SimpleHtmlComponent("title")
public interface ITitle : ITag<HTMLTitleElement>

/**
 * HTML Tr component.
 */
@SimpleHtmlComponent("tr")
public interface ITr : ITag<HTMLTableRowElement>

/**
 * HTML Track component.
 */
@SimpleHtmlComponent("track")
public interface ITrack : ITag<HTMLTrackElement>

/**
 * HTML U component.
 */
@SimpleHtmlComponent("u", withText = true)
public interface IU : ITag<HTMLElement>

/**
 * HTML Ul component.
 */
@SimpleHtmlComponent("ul")
public interface IUl : ITag<HTMLUListElement>

/**
 * HTML Var tag component.
 */
@SimpleHtmlComponent("var")
public interface IVarTag : ITag<HTMLElement>

/**
 * HTML Video component.
 */
@SimpleHtmlComponent("video")
public interface IVideo : ITag<HTMLVideoElement>

/**
 * HTML Wbr component.
 */
@SimpleHtmlComponent("wbr")
public interface IWbr : ITag<HTMLElement>
