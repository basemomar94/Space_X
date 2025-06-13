package com.mindera.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.base.models.NetworkResult
import com.mindera.base.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


/**
 * BaseViewModel provides a standard way to manage UI state, events, and side effects.
 *
 * @param UiState The UI state type. Must extend [ViewState].
 * @param Event Events like user actions or system triggers. Must extend [ViewIntent].
 * @param Effect One-time effects (e.g., navigation, toasts). Must extend [ViewSideEffect].
 */
abstract class BaseViewModel<UiState : ViewState, Event : ViewIntent, Effect : ViewSideEffect> :
    ViewModel() {

    private val logger = Logger("-- BaseViewModel")

    /**
     * Returns the initial state of the UI.
     */
    abstract fun setInitialState(): UiState

    /**
     * Handles incoming events.
     * Override this to define how each event should update state or trigger effects.
     */
    abstract suspend fun handleEvents(event: Event)
    private val initialState: UiState by lazy { setInitialState() }

    private val _viewState: MutableStateFlow<UiState> = MutableStateFlow(initialState)

    /**
     * Exposes the current UI state.
     */
    val viewState: StateFlow<UiState> = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()

    /**
     * Exposes one-time effects like navigation or showing a toast.
     */
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    /**
     * Starts collecting events and passes them to [handleEvents].
     */
    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Emits an event to the ViewModel.
     * This function is typically called from the UI layer to communicate user actions or external events.
     *
     * @param event The event to emit.
     */
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    /**
     * Updates the UI state using a reducer function.
     * The reducer takes the current state and returns a new state.
     *
     * @param reducer A lambda function that transforms the current state into a new state.
     */
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }


    /**
     * Collects a [Flow] with optional callbacks for loading, success, error, and completion.
     *
     * @param flow The flow to collect.
     * @param resultSuccess Called on successful result.
     * @param resultFailure Called if result is [NetworkResult.Failure].
     * @param onError Called if an exception is thrown.
     * @param onStart Called when the flow starts.
     * @param onComplete Called when the flow finishes (with or without error).
     * @param tag Used for logging purposes.
     */
    fun <T> launchAndCollectResult(
        flow: Flow<T>,
        resultSuccess: suspend (T) -> Unit = {},
        resultFailure: (NetworkResult.Failure) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onStart: () -> Unit = {},
        onComplete: (Throwable?) -> Unit = {},
        tag: String = "Unknown",
    ): Job {
        logger.d("launchAndCollectResult called for flow with tag: $tag")

        return viewModelScope.launch {
            flow.flowOn(
                Dispatchers.IO
            ).onStart {
                onStart()
                logger.d("Flow with tag $tag started.")
            }.onCompletion { cause ->
                onComplete(cause)
                logger.d("Flow with tag $tag completed. Cause: ${cause?.message ?: "No cause"}")
            }.catch { exception ->
                logger.e("Exception in flow with tag $tag.", exception)
                onError(exception)
            }.safeCollect { result ->
                if (result is NetworkResult.Failure) {
                    logger.e("Failure result from flow with tag $tag: $result")
                    resultFailure(result)
                } else {
                    logger.i("Success result from flow with tag $tag: $result")
                    resultSuccess(result)
                }
            }
        }
    }

    private suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
        collect {
            coroutineContext.ensureActive()
            action(it)
        }
    }



}