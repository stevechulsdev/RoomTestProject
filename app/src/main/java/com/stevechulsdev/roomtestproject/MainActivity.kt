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
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var disposeable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val person = Person(0, "stevechulsdev", "Developer")

        var dataPersonList = arrayListOf<Person>()

        bt_create_db.setOnClickListener {

        }

        bt_insert.setOnClickListener {
            Observable.just(PersonDatabase.getInstance(this))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        it?.let {
                            it.getPersonDao().insert(person)
                            disposeable?.let {
                                it.dispose()
                            }
                        }
                    },
                    {
                        Log.e("stevechulsdev", "${it.message}")
                    }
                )
        }

        bt_read.setOnClickListener {
            disposeable = Observable.just(PersonDatabase.getInstance(this))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        it?.let {
                            it.getPersonDao()
                                .getAllPersonRx()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    {
                                        tv_show_data.text = ""
//                                        tv_show_data.clearComposingText()
                                        dataPersonList.addAll(it)

                                        for(index in dataPersonList.indices) {
                                            tv_show_data.append("id : ${dataPersonList[index].id}, name : ${dataPersonList[index].name}, job : ${dataPersonList[index].job}\n")
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
        }

        bt_delete.setOnClickListener {

        }

        bt_delete_all.setOnClickListener {
            Observable.just(PersonDatabase.getInstance(this))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        it?.let {
                            it.getPersonDao().clearAll()
                        }
                        PersonDatabase
                            .getInstance(this)?.let {
                                it.getPersonDao().clearAll()
                                tv_show_data.text = ""
                                dataPersonList.clear()
                            }
                    },
                    {
                        Log.e("stevechulsdev", "${it.message}")
                    }
                )
        }
    }
}
