package org.jxxy.debug.member.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.member.bean.MemberGroup
import org.jxxy.debug.member.bean.TaskRespone
import org.jxxy.debug.member.http.repository.PointRepository
import org.jxxy.debug.member.http.respone.PointDetailRespone

class PointViewModel(val app: Application) : BaseViewModel(app) {
    private val repository by lazy { PointRepository() }
    val taskDataLiveData: ResLiveData<TaskRespone> by lazy { ResLiveData() }
    val rankDataLiveData: ResLiveData<MemberGroup> by lazy { ResLiveData() }
    fun getTask() {
        request(
            taskDataLiveData,
            object : BaseLiveDataCallback2<TaskRespone> {}
        ) {
            repository.getTask()
        }
    }
    fun getRank() {
        request(
            rankDataLiveData,
            object : BaseLiveDataCallback2<MemberGroup> {}
        ) {
            repository.getRank()
        }
    }

    val detailDataLiveData: ResLiveData<PointDetailRespone> by lazy { ResLiveData() }

    fun getDetail() {
        request(
            detailDataLiveData,
            object : BaseLiveDataCallback2<PointDetailRespone> {}
        ) {
            repository.getDetail()
        }
    }
}
