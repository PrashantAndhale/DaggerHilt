package com.example.daggerhilt.Utils

import android.annotation.SuppressLint


class PracticeDemo {

     fun main() {
        val name: String by lazy {
            return@lazy "";
        }

        val obj = Student("Prashnt", 31).run {
            Student("Dhara", 31)
        }
        obj.run {
            "Dhara"
        }

        val obj3 = Student("Dhara", 31)
        with(obj3)
        {
            print(name + age)
        }

        val sum:(no1:Int) ->Int={no1 ->  no1*no1}
    }
}

data class Student(val name: String, val age: Int)