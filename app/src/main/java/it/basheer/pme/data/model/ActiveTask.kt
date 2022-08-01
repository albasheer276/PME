package it.basheer.pme.data.model

data class ActiveTask(
    val id: Long? = null,
    val name: String,
    val period: String,
    val count: Int? = null,
    val duration: Int? = null,
    val points: Int,
    val type: Int,
    val user_id: Long,
    val completed: Int? = 0,
    var status: Int? = 0
)