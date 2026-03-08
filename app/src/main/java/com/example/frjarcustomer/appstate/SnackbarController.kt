package com.example.frjarcustomer.appstate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object SnackbarController {
    private val queue = Channel<SnackbarModel>(Channel.UNLIMITED)
    private val dismissSignal = Channel<Unit>(Channel.RENDEZVOUS)
    private val _current = MutableStateFlow<SnackbarModel?>(null)

    val current: StateFlow<SnackbarModel?> = _current.asStateFlow()

    fun init(applicationScope: CoroutineScope) {
        applicationScope.launch(Dispatchers.Main.immediate) {
            while (true) {
                val model = queue.receive()
                _current.value = model
                dismissSignal.receive()
                _current.value = null
            }
        }
    }

    fun show(model: SnackbarModel) {
        queue.trySend(model)
    }

    fun dismiss() {
        dismissSignal.trySend(Unit)
    }
}



