package com.example.roveronmars

class Rover(orientation: Char, width: Int, height: Int) {

    //Rover class keeps infos about orientation, width and height
    private var orientation: Char = orientation
    private var width: Int = width
    private var height: Int = height

    fun getOrientation(): Char {
        return orientation
    }

    fun getHeight(): Int {
        return height
    }

    fun getWidth(): Int {
        return width
    }

    fun setOrientation(orientation: Char) {
        this.orientation = orientation
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    fun setHeight(height: Int) {
        this.height = height
    }
}