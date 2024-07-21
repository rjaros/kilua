package dev.kilua.compose

import androidx.compose.runtime.MonotonicFrameClock

internal actual val defaultMonotonicFrameClock: MonotonicFrameClock = NoDomMonotonicClockImpl()
