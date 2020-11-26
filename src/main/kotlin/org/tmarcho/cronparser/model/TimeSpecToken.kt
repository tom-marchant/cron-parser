package org.tmarcho.cronparser.model

sealed class TimeSpecToken

data class WildcardToken(val void: Void? = null) : TimeSpecToken() // awkward use of null field to support use of data class
data class SingleValueToken(val value: Int) : TimeSpecToken()
data class RangeToken(val from: Int, val to: Int) : TimeSpecToken()
data class StepToken(val step: Int) : TimeSpecToken()