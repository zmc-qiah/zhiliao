package org.jxxy.debug.society.http.response

class HistoryResponse(
    var aiHistory: List<AiHistory?>?,
    var scheme: Any?,
    var type: Int?
) {
        class AiHistory(
            var historyEvent: String?,
            var historyPhoto: String?,
            var pointInTime: String?
        )
    }
