package dev.kilua.compose.ui

import dev.kilua.html.helpers.onCombineClick
import web.dom.events.MouseEvent

/**
 * An extension function for the [Modifier] that makes a composable clickable with a mouse event.
 * This function allows you to listen to mouse click events and invoke the provided action.
 *
 * @param onClick A lambda function to be invoked when the component is clicked.
 */
public fun Modifier.clickable(onClick: (MouseEvent) -> Unit) = eventsModifier {
    onClick(onClick)
}

/**
 * An extension function for the [Modifier] that allows handling of click, double-click, and long click events.
 * This function combines multiple input events (like mouse and touch) to trigger the respective actions
 * based on the user's interaction.
 *
 * @param onClick A lambda function to be invoked on a regular single click (non-long click).
 * @param onDoubleClick An optional lambda function that will be invoked on a double-click.
 *                      If `null`, no action is taken.
 * @param onLongClick An optional lambda function that will be invoked on a long-click.
 *                    If `null`, no action is taken.
 */
public fun Modifier.combineClickable(
    onClick: () -> Unit,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) = eventsModifier {
    onCombineClick(onClick, onDoubleClick, onLongClick)
}
