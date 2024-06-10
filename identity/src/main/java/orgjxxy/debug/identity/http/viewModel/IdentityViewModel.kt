package orgjxxy.debug.identity.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import orgjxxy.debug.identity.http.repository.IdentityRepository
import orgjxxy.debug.identity.http.response.IdentityAllGetResponse
import orgjxxy.debug.identity.http.response.IdentityGetResponse
import orgjxxy.debug.identity.http.response.OptionResponse
import orgjxxy.debug.identity.http.response.getByIdResponse

class IdentityViewModel (app: Application) : BaseViewModel(app) {
    companion object {

    }
    val repository: IdentityRepository by lazy { IdentityRepository() }
    val IdentityGetData: ResLiveData<IdentityGetResponse?> by lazy { ResLiveData() }
    val identityanswerpostData: ResLiveData<OptionResponse> by lazy { ResLiveData() }
    val identityAllGetData: ResLiveData<IdentityAllGetResponse?> by lazy { ResLiveData() }
    val getByIdData: ResLiveData<getByIdResponse?> by lazy { ResLiveData() }

    //获取搜索提示词
    fun IdentityGet() {
        request(
            IdentityGetData,
            object : BaseLiveDataCallback2<IdentityGetResponse?> {}
        ) {
            repository.IdentityGet()
        }
    }
    fun IdentityAllGet() {
        request(
            identityAllGetData,
            object : BaseLiveDataCallback2<IdentityAllGetResponse?> {}
        ) {
            repository.IdentityAllGet()
        }
    }





    fun identityanswerpost(identityId :Int,option1: Char,
                           option2: Char,
                           option3: Char,
                           option4: Char,
                           option5: Char,
                           option6: Char,
                           option7: Char,
                           option8: Char,
                           option9: Char) {
        request(
            identityanswerpostData,
            object : BaseLiveDataCallback2<OptionResponse> {}
        ) {
            repository.Optionget(identityId,
                option1,
                option2,
                option3,
                option4,
                option5,
                option6,
                option7,
                option8,
                option9)
        }
    }
    fun getById(identityId:Int) {
        request(
            getByIdData,
            object : BaseLiveDataCallback2<getByIdResponse?> {}
        ) {
            repository.getById(identityId)
        }
    }


}
