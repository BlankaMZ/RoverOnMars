package com.example.roveronmars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun roverOnMarsPosition( view: View){

        //Height              N
        //^                   ^
        //|                   |
        //|
        //|_ _ _ > Width

        //the drawing above shows the Width and Height directions in the program in relation to N orientation

        //we get values entered by the user
        val squareWidth: String = findViewById<EditText>(R.id.editText).text.toString()
        val squareHeight: String = findViewById<EditText>(R.id.editText2).text.toString()
        val roverOrientation: String = findViewById<EditText>(R.id.editText3).text.toString()
        val width: String = findViewById<EditText>(R.id.editText4).text.toString()
        val height: String = findViewById<EditText>(R.id.editText5).text.toString()
        val command: String = findViewById<EditText>(R.id.editText6).text.toString()

        //we check them
        if(squareWidth.isEmpty() || squareHeight.isEmpty() || roverOrientation.isEmpty() || height.isEmpty() || width.isEmpty() || command.isEmpty()){
            Toast.makeText(this, "Please enter all data", Toast.LENGTH_SHORT).show()
        }
        else if(squareWidth.toInt() <= 0 && squareHeight.toInt() <= 0){
            Toast.makeText(this, "Square width and height must be grater than 0", Toast.LENGTH_SHORT).show()
        }
        else if(roverOrientation != "N" && roverOrientation !="S" && roverOrientation != "W" && roverOrientation != "E"){
            Toast.makeText(this, "Rover orientation must be N,S,E or W", Toast.LENGTH_SHORT).show()
        }
        else if( width.toInt() >= squareWidth.toInt() || width.toInt() < 0 || height.toInt() >= squareHeight.toInt() || height.toInt() <0){
            Toast.makeText(this, "Wrong width or height", Toast.LENGTH_SHORT).show()
        }
        else{
            //we pass them to the validation program and show the result
           val result: Pair<String, Rover> =  marsRoverProgram(squareWidth.toInt(), squareHeight.toInt(), roverOrientation.single(), width.toInt(), height.toInt(), command)
            if (result.first == "True" ) {
                findViewById<TextView>(R.id.textView3).apply {
                    text = result.first + ", " + result.second.getOrientation() + ", (" + result.second.getWidth() + "," + result.second.getHeight() + ")"
                }
            }
            else {
                findViewById<TextView>(R.id.textView3).apply {
                    text = result.first
                }
            }
        }

    }

    fun marsRoverProgram(squareWidth: Int, squareHeight: Int, orientation: Char, width: Int, height: Int, commands: String): Pair<String, Rover> {

        val baseRover = Rover(orientation, width, height)
        var finalRover = baseRover
        val commandsArray = commands.toCharArray()
        for (command in commandsArray) {
            //programm goes one by one through all the commands
            when (command) {
                'A', 'a' -> {
                    val validation = validateRoverMovement(finalRover, squareWidth, squareHeight)
                    if (validation.first == false)
                        return Pair("False", baseRover)
                    else {
                        finalRover = validation.second
                    }
                }
                'R', 'r' -> finalRover = turnRover('R', finalRover)
                'L', 'l' -> finalRover = turnRover('L', finalRover)
                else -> return Pair("Unknown command", baseRover)
            }
        }
        return Pair("True", finalRover)
    }

    private fun validateRoverMovement(finalRover: Rover, squareWidth: Int, squareHeight: Int): Pair<Boolean, Rover> {
        var validate = false
        var width = finalRover.getWidth()
        var height = finalRover.getHeight()
        //we check if the rovers next move is inside the square
        when (finalRover.getOrientation()) {
            'N' -> {
                if (height + 1 < squareHeight) {
                    ++height
                    validate = true
                }
            }
            'E' -> {
                if (width + 1 < squareWidth) {
                    ++width
                    validate = true
                }
            }
            'S' -> {
                if (height - 1 >= 0) {
                    --height
                    validate = true
                }
            }
            'W' -> if (width - 1 >= 0) {
                --width
                validate = true
            }
        }
        finalRover.setWidth(width)
        finalRover.setHeight(height)
        return Pair(validate, finalRover)
    }

    private fun turnRover(direction: Char, finalRover: Rover): Rover {
        //we obtain the new orientation of the rover, after it turned
        if (direction == 'L') {
            when (finalRover.getOrientation()) {
                'N' -> finalRover.setOrientation('W')
                'E' -> {
                    finalRover.setOrientation('N')
                }
                'S' -> {
                    finalRover.setOrientation('E')
                }
                'W' -> finalRover.setOrientation('S')
            }
        } else if (direction == 'R') {
            when (finalRover.getOrientation()) {
                'N' -> finalRover.setOrientation('E')
                'E' -> {
                    finalRover.setOrientation('S')
                }
                'S' -> {
                    finalRover.setOrientation('W')
                }
                'W' -> finalRover.setOrientation('N')
            }
        }
        return finalRover
    }

}
