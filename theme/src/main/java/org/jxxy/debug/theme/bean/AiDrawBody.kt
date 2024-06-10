package org.jxxy.debug.theme.bean

class AiDrawBody(
    val prompt: String? = null,
    val model_id : Int= 0,
    val height : Int = 0,
    val fill_prompt : Int = 0,
    val addition: AiDrawAddition? = null,
    val width : Int= 0,
    val multiply : Int = 1,
) {

}