package com.kagg.psp_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var label: TextView
    lateinit var button: Button

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
        button.setOnClickListener {
            //launchARN()
            // withThread()
            //withThreadAndPost()
            //withRunUIThread()
            //threadFromParam()
            //launchMultipleThreads()
            //launchInsideThread()
            launchInsideThread2()
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
}