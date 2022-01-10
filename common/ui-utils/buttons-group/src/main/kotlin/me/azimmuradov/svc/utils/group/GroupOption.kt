package me.azimmuradov.svc.utils.group

sealed interface GroupOption<out T> {

    data class Some<T>(val value: T) : GroupOption<T>

    data class Disabled<T>(val value: T) : GroupOption<T>

    object None : GroupOption<Nothing>
}