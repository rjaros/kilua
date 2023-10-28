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
    }
    globalThis.Element = class Element extends Node {
    }
    globalThis.HTMLElement = class HTMLElement extends Element {
    }
    globalThis.Text = class Text extends Node {
    }
}

function nodeJsCreateElement() {
    return new Element()
}

function nodeJsCreateText() {
    return new Text()
}

export { nodeJsCreateElement, nodeJsCreateText };
