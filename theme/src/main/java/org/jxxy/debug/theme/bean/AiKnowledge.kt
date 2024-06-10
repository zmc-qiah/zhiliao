package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType

class AiKnowledge(
    val list : List<String>,
    name: String,
    photo: String,
    scheme: Scheme?
) : AIBean(name,photo,scheme,MyType.AI_KNOWLEDGE){

}