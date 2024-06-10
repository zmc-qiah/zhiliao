package org.jxxy.debug.society.http.response

import com.google.common.reflect.ClassPath
import org.jxxy.debug.common.scheme.SchemeDetail

class ExcellentClassResponse(
        var classInfos: List<ClassInfo?>?
    ) {
        class ClassInfo(
            var describe: String?,
            var photo: String?,
            var scheme: SchemeDetail?,
            var title: String?
        ) {
            class Scheme(
                var resourceId: Int?,
                var route: String?,
                var type: Int?
            )
        }}

