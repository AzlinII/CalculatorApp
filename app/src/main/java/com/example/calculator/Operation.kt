package com.example.calculator

import java.lang.ArithmeticException
import java.text.DecimalFormat

/*
* This is an example of using Factory Method for operations
*/

interface Operation {
    var text: String
    fun execute(): String
    data class LeftRight(val left: Double, val right: Double)
    fun getLeftRight(value: String, op: String): LeftRight {
        var prefix = ""
        var text = value
        if(text.startsWith("-")){
            prefix = "-"
            text = text.substring(1)
        }
        val splitValues = text.split(op)

        return LeftRight((prefix + splitValues[0]).toDouble(), splitValues[1].toDouble())
    }
    fun formatNumber(value: Double): String {
        val df: DecimalFormat = DecimalFormat("0.#")
        return df.format(value)
    }
}

class Subtract(override var text: String): Operation {
    override fun execute(): String {
        val (left, right) = getLeftRight(text, "-")
        return formatNumber(left - right)
    }
}

class Add(override var text: String): Operation {
    override fun execute(): String {
        val (left, right) = getLeftRight(text, "+")
        return formatNumber(left + right)
    }
}

class Divide(override var text: String): Operation {
    override fun execute(): String {
        val (left, right) = getLeftRight(text, "/")
        return formatNumber(left / right)
    }
}

class Multiply(override var text: String): Operation {
    override fun execute(): String {
        val (left, right) = getLeftRight(text, "*")
        return formatNumber(left * right)
    }
}

class OperationFactory {
    companion object {
        fun create(text: String): Operation {
            val op = findOperator(text)
            return when(op){
                "-" -> Subtract(text)
                "+" -> Add(text)
                "/" -> Divide(text)
                "*" -> Multiply(text)
                else -> throw ArithmeticException("Operator '$op' does not exist")
            }
        }

        private fun findOperator(text: String): String {
            val opRegex = Regex("[-+*/]")
            // we don't want to pull "-" from negative numbers when searching for operator
            val searchText = if(text.startsWith("-")) text.substring(1) else text

            // about !!: https://kotlinlang.org/docs/reference/null-safety.html#the--operator
            return opRegex.find(searchText)!!.value
        }
    }
}

//fun main(){
//    val opp: Operation = OperationFactory.create("-10 / 4")
//    print(opp.execute())
//}
