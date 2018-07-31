package com.example.minhquan.bflagclient.home.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R

import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_edit_info.*
import android.app.DatePickerDialog
import java.util.*
import java.text.SimpleDateFormat


class EditInfoActivity : AppCompatActivity(){
    private val myCalendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        val animation = AnimationUtils.loadAnimation(this, R.anim.bouncing)
        constraint.startAnimation(animation)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        tvCancel.setOnClickListener {
            onBackPressed()
        }

        setUpBirthday()
    }

    private fun setUpBirthday() {


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        edtBirthday.setOnClickListener {
            DatePickerDialog(this@EditInfoActivity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        edtBirthday.setText(sdf.format(myCalendar.time))
    }
}