package ch.derlin.dcvizmermaid.graph

import ch.derlin.dcvizmermaid.data.VolumeBinding
import ch.derlin.dcvizmermaid.data.VolumeBinding.VolumeType

enum class GraphOrientation {
    LR, RL, TB, BT
}

enum class Shape {
    NONE, RECT_ROUNDED, CIRCLE, HEXAGON, PARALLELOGRAM, CYLINDER, STADIUM, RHOMBUS;

    fun format(id: Any, name: Any): String {
        val validName = name.toValidName()
        return when (this) {
            NONE -> if (id == name) validName else "$id[$validName]"
            RECT_ROUNDED -> "$id($validName)"
            CIRCLE -> "$id(($validName))"
            HEXAGON -> "$id{{$validName}}"
            PARALLELOGRAM -> "$id[/$validName/]"
            CYLINDER -> "$id[($validName)]"
            STADIUM -> "$id([$validName])"
            RHOMBUS -> "$id{$validName}"
        }
    }
}

enum class CONNECTOR {
    ARROW, DOT_ARROW,
    X, DOT_X, DOT_DBL_X,
    DBL_ARROW, DOT_DBL_ARROW;

    fun format(text: Any?): String {
        val validText = text?.toValidName()
        return when (this) {
            ARROW -> validText?.let { "-- $it -->" } ?: "-->"
            DOT_ARROW -> validText?.let { "-. $it .->" } ?: "-.->"

            DBL_ARROW -> validText?.let { "<-- $it -->" } ?: "<-->"
            DOT_DBL_ARROW -> validText?.let { "<-. $it .->" } ?: "<-.->"

            X -> validText?.let { "-- $it --x" } ?: "--x"
            DOT_X -> validText?.let { "-. $it .-x" } ?: "-.-x"
            DOT_DBL_X -> validText?.let { "x-. $it .-x" } ?: "x-.-x"
        }
    }
}

fun VolumeType.toShape() = when(this) {
    VolumeType.VOLUME -> Shape.HEXAGON
    VolumeType.BIND -> Shape.RECT_ROUNDED
    VolumeType.TMPFS -> Shape.RHOMBUS

}