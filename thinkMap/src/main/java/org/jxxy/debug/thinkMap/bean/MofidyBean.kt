package org.jxxy.debug.thinkMap.bean

data class MofidyBean(
    var userToken: String,
    var userName:String,
    var userPhoto:String,
    var position:Int,
    val id:Int,
    val action:Int,
    val data:String,
    var thinkMapId:Int,
){
    override fun toString(): String {
        return "MofidyBean(userToken='$userToken', userName='$userName', userPhoto='$userPhoto', position=$position, id=$id, action=$action, data='$data', thinkMapId=$thinkMapId)"
    }
    companion object{
        val ACTION_Title_ADD = 0
        val ACTION_Title_CHANGE = 1
        val ACTION_CONTENT_CHANGE = 2
        val ACTION_CONTENT_ADD = 3
        val ACTION_PICTURE_ADD = 4
        val ACTION_PICTURE_CHANGE = 5
        val ACTION_SELECT = 6
        val ACTION_ADD_NODE = 7
        val ACTION_DELETE_NODE = 8
        val ACTION_SELECT_NOT = 9
        val ACTION_DRAW_NODE = 10
        val ACTION_CLOSE = -1
        val ID_ACTIVITY = -99
    }



}