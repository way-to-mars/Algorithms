import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val mutexA = Mutex()
val mutexB = Mutex()
var taskACompleted = false
var taskBCompleted = false

fun main(){
    runBlocking {
        launch { taskA() }
        launch { taskB() }
    }
}

suspend fun taskA(){
    mutexA.withLock {
        delay(1000)
        taskACompleted = true
        mutexB.withLock {
            if(taskBCompleted){
                println("Task B completed")
            }
        }
    }
}

suspend fun taskB(){
    mutexB.withLock {
        delay(1000)
        taskBCompleted = true
        mutexA.withLock {
            if(taskACompleted){
                println("Task A completed")
            }
        }
    }
}