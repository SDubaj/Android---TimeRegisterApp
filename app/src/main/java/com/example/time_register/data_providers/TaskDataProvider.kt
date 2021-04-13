package com.example.time_register.data_providers

import com.example.time_register.enums.TaskStatuses
import com.example.time_register.models.Task
import com.google.firebase.database.*

class TaskDataProvider {
    private var database: DatabaseReference? = null

    fun TaskDataProvider() {
        database = FirebaseDatabase.getInstance().reference
    }

    fun Add(task: Task): Boolean {
        if(database == null)
            database = FirebaseDatabase.getInstance().reference
        val result = booleanArrayOf(false)
        task.Id = database!!.child("Tasks").push().key
        database!!.child("Tasks").child(task.Id).setValue(task).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result[0] = true
            }
        }
        return true
    }

    fun Update(task: Task): Boolean {
        if(database == null)
            database = FirebaseDatabase.getInstance().reference
        val result = booleanArrayOf(false)
        database!!.child("Tasks").child(task.Id).setValue(task).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result[0] = true
            }
        }
        return true
    }

    fun GetTasks(): List<Task>? {
        if(database == null)
            database = FirebaseDatabase.getInstance().reference
        val taskList = mutableListOf<Task>()
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (task in snapshot.children){
                    val tempTask = Task();
                    tempTask.Id = task.child("Id").getValue().toString();
                    tempTask.Name = task.child("Name").getValue().toString();
                    tempTask.Describe = task.child("Describe").getValue().toString();
                    tempTask.Status = TaskStatuses.valueOf(task.child("Status").getValue().toString());
                    taskList.add(tempTask);
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database?.child("Tasks")?.addValueEventListener(listener);
        return taskList
    }

    fun GetTask(taskId: String): Task  {
        var task: Task?;
        if(database == null)
            database = FirebaseDatabase.getInstance().reference
        //val task: Task = database!!.child("Tasks").child(taskId).get().getResult()?.getValue(Task::class.java)!!;
        //println(database!!.child("Tasks").child(taskId).get());
        database!!.child("Tasks").child(taskId).get().addOnSuccessListener {
            println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            task = it.getValue(Task::class.java);
            println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }
        return Task();
    }
}