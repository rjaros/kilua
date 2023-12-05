/*
 * Fake DOM classes for Node.js.
 */

if (typeof process !== 'undefined') {
    globalThis.MouseEvent = class MouseEvent extends Event {
    };
    globalThis.InputEvent = class InputEvent extends Event {
    };
    globalThis.FocusEvent = class FocusEvent extends Event {
    };
    globalThis.KeyboardEvent = class KeyboardEvent extends Event {
    };
    globalThis.DragEvent = class DragEvent extends Event {
    };

    globalThis.Node = class Node {
        style = {}
        setAttribute(name, value) {}
        appendChild(node) {}
        querySelector(selector) { return null; }
        insertBefore(node, referenceNode) {}
        addEventListener(name, callback) {}
    }
    globalThis.Element = class Element extends Node {
    }
    globalThis.HTMLElement = class HTMLElement extends Element {
    }
    globalThis.Text = class Text extends Node {
    }
    globalThis.Comment = class Comment extends Node {
    }
    globalThis.document = {
        kilua: true,
        documentElement: new Element(),
        head: new Element(),
        createElement: function(name) {
            return new Element()
        },
        addEventListener(name, callback) {}
    }
    globalThis.window = {
        document: globalThis.document,
        addEventListener(name, callback) {},
        setTimeout: globalThis.setTimeout
    }
    globalThis.customElements = {
        get: function (name) {},
        define: function (name, element) {}
    }
}

function nodeJsInit() {}

function nodeJsCreateElement() {
    return new Element()
}

function nodeJsCreateText() {
    return new Text()
}

function nodeJsCreateComment() {
    return new Comment()
}

module.exports = {nodeJsInit, nodeJsCreateElement, nodeJsCreateText, nodeJsCreateComment};
