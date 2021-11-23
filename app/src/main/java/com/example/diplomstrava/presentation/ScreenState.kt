package com.example.diplomstrava.presentation

sealed class ScreenState {
    object DefaultState : ScreenState()
    object LoadingState : ScreenState()
    object SwipeRefresh : ScreenState()
    object SuccessState : ScreenState()
    object ErrorState : ScreenState()
    object EmptyListState : ScreenState()
    object CancelState : ScreenState()
}
