package dev.kilua.html.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * A Composable function that creates and remembers the hover state of a component.
 * It tracks whether the component is being hovered over using mouse events.
 *
 * @return [State<Boolean>] A mutable state that represents whether the component is hovered or not.
 * The state is `true` when the mouse is over the component, and `false` when the mouse is not over it.
 */
@Composable
public fun TagEvents.rememberIsHoveredAsState(): State<Boolean> {
    val state = remember { mutableStateOf(false) }
    onMouseOver { state.value = true }
    onMouseOut { state.value = false }
    return state
}

/**
 * A Composable function that creates and remembers the pressed state of a component.
 * It tracks whether the component is being pressed using pointer events.
 *
 * @return [State<Boolean>] A mutable state that represents whether the component is pressed or not.
 * The state is `true` when the pointer is down on the component, and `false` when the pointer is lifted.
 */
@Composable
public fun TagEvents.rememberIsPressedAsState(): State<Boolean> {
    val state = remember { mutableStateOf(false) }
    onPointerDown { state.value = true }
    onGlobalPointerUp { state.value = false }
    return state
}
