package org.jxxy.debug.theme.bean

data class AiDrawPictureRes(
    /**
     * 模型参数
     */
    val addition: Map<String, Any?>? = null,

    /**
     * 消耗（单位：毫）
     */
    val cost: Long,

    /**
     * 创建时间
     */
    val create_at: String,

    /**
     * 预估时间，单位=秒
     */
    val estimate: Long,

    /**
     * 失败原因
     */
    val failReason: String? = null,

    /**
     * 是否使用禅思模式
     */
    val fill_prompt: Long,

    /**
     * 作品图片
     */
    val gen_img: String? = null,

    /**
     * 作品高度
     */
    val height: Long,

    /**
     * 16位字符ID
     */
    val id: String,

    /**
     * 模型ID：1=通用SD模型，3=动漫模型；0=无
     */
    val model_id: Long,

    val model_type: ModelType,

    /**
     * 生成文本
     */
    val prompt: String,

    /**
     * 参考图
     */
    val ref_img: String? = null,

    /**
     * 随机种子
     */
    val seed: Long? = null,

    /**
     * 状态
     */
    val state: State,

    /**
     * 状态文本
     */
    val stateText: String? = null,

    /**
     * 作品宽度
     */
    val width: Long
) {
}

 enum class State(val value: String) {
    Cancel("cancel"),
    Disabled("disabled"),
    Fail("fail"),
    InCreate("in_create"),
    InWait("in_wait"),
    Success("success");

    companion object {
        public fun fromValue(value: String): State = when (value) {
            "cancel"    -> Cancel
            "disabled"  -> Disabled
            "fail"      -> Fail
            "in_create" -> InCreate
            "in_wait"   -> InWait
            "success"   -> Success
            else        -> throw IllegalArgumentException()
        }
    }
}

enum class ModelType(val value: String) {
    Preset("preset"),
    Third("third");

    companion object {
        public fun fromValue(value: String): ModelType = when (value) {
            "preset" -> Preset
            "third"  -> Third
            else     -> throw IllegalArgumentException()
        }
    }
}