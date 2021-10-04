package com.kagg.psp_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var label: TextView
    lateinit var button: Button
    lateinit var spinner: ProgressBar

    /** vienen de la vista **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
    }

    /** Método para configurar la vista **/
    private fun setupView() {
        label = findViewById(R.id.label)
        button = findViewById(R.id.button)
        spinner =findViewById(R.id.spinner)
        button.setOnClickListener {
            //launchARN()
            // withThread()
            //withThreadAndPost()
            //withRunUIThread()
            //threadFromParam()
            //launchMultipleThreads()
            //launchInsideThread()
            //launchInsideThread2()
            //postDelayed()
            //launchProgressBar()
            launchProgressBarNum(10)
        }
    }

    private fun launchARN() {
        for (i in 1..100) {
            label.text = "Hola $i"
            Thread.sleep(2000)
        }
    }

    private fun withThread() {
        Thread(Runnable {
            for (i in 1..100) {
                label.text = "Hola $i"
                Thread.sleep(2000)
            }
        }).start() //creando un hilo para hacer otras cosas
    }

    private fun withThreadAndPost() {
        Thread(Runnable {
            for (i in 1..100) {
                label.post { //el post es un método de la vista(como el label en este caso) que hace que lo que hay dentro este en la vista
                    label.text = "Hola $i"
                }
                Thread.sleep(2000)
            }
        }).start() //creando un hilo para hacer otras cosas
    }

    private fun withRunUIThread() {
        Thread(Runnable {
            for (i in 1..100) {
                runOnUiThread() {
                    label.text = "Hola $i"
                }
                Thread.sleep(2000)
            }
        }).start() //así no hace  falta ir elemento por elemnto.post sino que ya cambiamos los elementos
    }

    private fun threadFromParam() {
        val thread = Thread(Runnable {
            for (i in 1..100) {
                runOnUiThread() {
                    label.text = "Hola $i"
                }
                Thread.sleep(2000)
            }
        })
        thread.start()
    }

    private fun launchMultipleThreads() {
        val thread1 = Thread(Runnable {
            for (i in 1..100) {
                runOnUiThread() {
                    label.text = "Thread-1 $i"
                }
                Thread.sleep(1000)
            }

        })
        val thread2 = Thread(Runnable {
            for (i in 1..100) {
                runOnUiThread() {
                    label.text = "Thread-2 $i"
                }
                Thread.sleep(1500)
            }
        })
        val thread3 = Thread(Runnable {
            for (i in 1..100) {
                runOnUiThread() {
                    label.text = "Thread3 $i"
                    Log.d("@dev", "thread3")
                }
                Thread.sleep(2000)
            }
        })
        thread3.start()
        thread2.start()
        thread1.start()

    }

    private fun launchInsideThread() { //este sujeto ejecuta el for y cuando termina ya ejecuta el otro, el padre no se muere hasta que no acaban sus hijos
        Thread(Runnable {
            for (i in 1..100) {
                Log.d("@dev", "thread1")
                Thread.sleep(1000)
            }
            Thread(Runnable {
                for (i in 1..100) {
                    Log.d("@dev", "thread2")
                    Thread.sleep(2000)
                }
            }).start()
        }).start()
    }

    private fun launchInsideThread2() { //el primer hilo(padre) hace que se ejecute el hijo y continúa con sus cosas, son independientes
        Thread(Runnable {
            Thread(Runnable {
                for (i in 1..100) {
                    Log.d("@dev", "thread1")
                    Thread.sleep(1000)
                }
            }).start()
            for (i in 1..100) {
                Log.d("@dev", "thread2")
                Thread.sleep(2000)
            }
        }).start()
    }
    private fun postDelayed(){
        Handler(Looper.getMainLooper()).postDelayed({
            label.text = "Hola!!"
        }, 3000)
        Thread(Runnable {
            Thread.sleep(3000)
            runOnUiThread {
                label.text = "Hola!!"
            }
        }).start()
    }
    //enseña num de 1 a 10, luego un spinner y luego Termina
    private fun launchProgressBar(){
        Thread(Runnable {
            for (i in 1..10) {
                Thread.sleep(1000)
                runOnUiThread {
                    label.text = "Hola!! $i"
                }
            }
            runOnUiThread {
                spinner.visibility=View.VISIBLE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                spinner.visibility=View.GONE
                label.text = ""
            }, 3000)
        }).start()
    }

    //enseña num de 1 a 10 mientras aparece el spinner, luego nada, el num puede ser x
    private fun launchProgressBarNum( num : Int ){
        Thread(Runnable {
            runOnUiThread {
                spinner.visibility=View.VISIBLE
            }
            for (i in 1..num) {
                Thread.sleep(1000)
                runOnUiThread {
                    label.text = "Hola!! $i"
                }
            }
            runOnUiThread {
                spinner.visibility = View.GONE
                label.text=""
            }
        }).start()
    }
}