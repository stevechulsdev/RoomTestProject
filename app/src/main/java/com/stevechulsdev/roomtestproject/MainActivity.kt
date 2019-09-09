package com.stevechulsdev.roomtestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.metalab.asyncawait.async
import com.stevechulsdev.roomtestproject.database.PersonDatabase
import com.stevechulsdev.roomtestproject.entity.Person
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val person = Person(0, "stevechulsdev", "Developer")

        bt_create_db.setOnClickListener {

        }

        bt_insert.setOnClickListener {
//            PersonDatabase
//                .getInstance(this)?.let {
//                    it.getPersonDao().insert(person)
//                }

            Observable.just(PersonDatabase.getInstance(this))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        it?.let {
                            it.getPersonDao().insert(person)
                        }
                    },
                    {

                    }
                )
//            PersonDatabase
//                .getInstance(this)?.let {
//                    it.getPersonDao().insert(person)
//                }

//            Observable.just(person)
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                    {
//                        PersonDatabase
//                            .getInstance(this)?.let {
//                                it.getPersonDao().insert(person)
//                            }
//                    },
//                    {
//                        Log.e("stevechulsdev", "${it.message}")
//                    }
//                )
        }

        bt_read.setOnClickListener {
//            PersonDatabase
//                .getInstance(this)?.let {
//                    it.getPersonDao().getAllPerson()
//                }
            Observable.just(PersonDatabase.getInstance(this))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        it?.let {
                            it.getPersonDao()
                                .getAllPersonRx()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    {
                                        for(index in it.indices) {
                                            tv_show_data.text = "id : ${it[index].id}, name : ${it[index].name}, job : ${it[index].job}"
//                                            tv_show_data.append("id : ${it[index].id}, name : ${it[index].name}, job : ${it[index].job}\n")
                                        }
                                    },
                                    {
                                        Log.e("stevechulsdev", "${it.message}")
                                    }
                                )
                        }
                    },
                    {
                        Log.e("stevechulsdev", "${it.message}")
                    }
                )

//            PersonDatabase
//                .getInstance(this)?.let { personDatab ->
////                    personDatab.getPersonDao().getAllPersonRx().observe(this, androidx.lifecycle.Observer {
////                        for(index in it.indices) {
////                            tv_show_data.append("id : ${it[index].id}, name : ${it[index].name}, job : ${it[index].job}\n")
////                        }
////                    })
//
//                    personDatab.getPersonDao()
//                        .getAllPersonRx()
//                        .observeOn(AndroidSchedulers.mainThread())
////                        .subscribeOn(Schedulers.io())
//                        .subscribe(
//                            {
//                                for(index in it.indices) {
//                                    tv_show_data.append("id : ${it[index].id}, name : ${it[index].name}, job : ${it[index].job}\n")
//                                }
//                            },
//                            {
//                                Log.e("stevechulsdev", "${it.message}")
//                            }
//                        )
//                }
        }

        bt_delete.setOnClickListener {

        }

        bt_delete_all.setOnClickListener {
            Observable.just(PersonDatabase.getInstance(this))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        PersonDatabase
                            .getInstance(this)?.let {
                                it.getPersonDao().clearAll()
                                tv_show_data.text = ""
                            }
                    },
                    {
                        Log.e("stevechulsdev", "${it.message}")
                    }
                )
        }
    }
}
