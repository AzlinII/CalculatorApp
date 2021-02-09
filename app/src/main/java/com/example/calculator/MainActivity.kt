package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onDigit(view: View){
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            binding.tvInput.append(".")
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !hasOperator(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var text: String = binding.tvInput.text.toString()
            val opp: Operation = OperationFactory.create(text)
            try {
                val result = opp.execute()
                binding.tvInput.text = result
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    
    private fun hasOperator(text: String): Boolean {
        // deals with negative numbers
        return if(text.startsWith("-")){
            false
        } else {
            text.contains("-") || text.contains("+")
                    || text.contains("*") || text.contains("/")
        }
    }
}