package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.util.LogWriter
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var isNewOp = true
    var TextSoFar="";
    fun buNumberEvent(view: View) {
        if (isNewOp == true) {
            TextSoFar=""
        }
        isNewOp = false
        val buSelect = view as Button

        var buClickValue: String = etShowNumber.text.toString()
        when (buSelect.id) {
            bu0.id -> {
                TextSoFar += "0"
            }
            bu1.id -> {
                TextSoFar += "1"
            }
            bu2.id -> {
                TextSoFar += "2"
            }
            bu3.id -> {
                TextSoFar += "3"
            }
            bu4.id -> {
                TextSoFar += "4"
            }
            bu5.id -> {
                TextSoFar += "5"
            }
            bu6.id -> {
                TextSoFar += "6"
            }
            bu7.id -> {
                TextSoFar += "7"
            }
            bu8.id -> {
                TextSoFar += "8"
            }
            bu9.id -> {
                TextSoFar += "9"
            }
            buDot.id -> {
                TextSoFar += "."
            }
            buPlus.id -> {
                TextSoFar += " + "
            }
            buMin.id -> {
                TextSoFar += " - "
            }
            buMul.id -> {
                TextSoFar += " * "
            }
            buDiv.id -> {
                TextSoFar += " / "
            }

        }
        etShowNumber.setText(TextSoFar)
    }


    fun buEqualEvent(view:View)
    {
        var Infix = etShowNumber.text.toString()
        var Postfix = getPostFix(Infix);
        var stack = Stack<Double>()
        var number= 0.0
        println("abs"+Postfix)

        for(character in Postfix)
        {
            if(precedence(character) >= 1)
            {
                if(!number.equals(0.0))
                {
                    stack.push(number)
                    number=0.0
                }

                val a = stack.pop()
                val b = stack.pop()
                val final = operate(b,a,character)
                //println("final"+final)
                stack.push(final)
            }else if(character==' ')
            {
                if(!number.equals(0.0))
                {
                    stack.push(number)
                    //println(stack.peek())
                    number=0.0
                }
            }else
            {
                number = number*10 + (character-'0')
                println(number)
            }

        }
        var finalAnswer = stack.pop()
        etShowNumber.setText(finalAnswer.toString())
        isNewOp=true
    }

    private fun operate(a: Double, b: Double, character: Char): Double? {
        if(character=='+')
        {
            return a+b
        }else if(character=='-')
        {
            return a-b
        }else if(character=='*')
        {
            return a*b
        }else
        {
            return a/b
        }
        return 0.0

    }

    private fun getPostFix(infix: String): String {
        var postFix =""
        val stack = Stack<Char>()
        //var first= 0;
        for(a in infix)
        {
            if(a.isDigit())
            {
                postFix+=a

            }else if(a == ' ')
            {

            }else{
                if(postFix.get(postFix.length-1).isDigit())
                {
                    postFix+=' '
                }
                while(!stack.empty() && precedence(a)<=precedence(stack.peek()))
                {
                    postFix+=stack.peek()
                    stack.pop()
                }
                stack.push(a)
            }
        }
        while(!stack.empty())
        {
            postFix+=stack.peek()
            stack.pop()
        }
        return postFix

    }

    private fun precedence(a: Char): Int {
        if(a=='/')
        {
            return 4
        }else if(a=='*')
        {
            return 3
        }else if(a=='+')
        {
            return 2
        }else if(a=='-')
        {
            return 1
        }
        return 0

    }

    fun buPercentEvent(view:View)
    {
        var number = etShowNumber.text.toString().toDouble()/100
        etShowNumber.setText(number.toString())

    }

    fun buCleanEvent(view:View)
    {
        etShowNumber.setText("")
        isNewOp = true
    }
}
