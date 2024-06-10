package orgjxxy.debug.identity.http.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
import orgjxxy.debug.identity.bean.OptionData
import orgjxxy.debug.identity.http.response.IdentityAllGetResponse
import orgjxxy.debug.identity.http.response.IdentityGetResponse
import orgjxxy.debug.identity.http.response.OptionResponse
import orgjxxy.debug.identity.http.response.getByIdResponse
import orgjxxy.debug.identity.http.service.IdentityService

class IdentityRepository {
    val TAG = "IdentityRepository"
    val apiService: IdentityService by lazy {
        HttpManager.instance.service()
    }
    suspend fun IdentityGet(): BaseResp<IdentityGetResponse?> {
        return apiService.Identityget()
    }
    suspend fun IdentityAllGet(): BaseResp<IdentityAllGetResponse?> {
        return apiService.IdentityAllGet()
    }




    suspend fun Optionget(identityId :Int,option1:Char,
                         option2: Char,
                         option3: Char,
                         option4: Char,
                         option5: Char,
                         option6: Char,
                         option7: Char,
                         option8: Char,
                         option9: Char,
                         ): BaseResp<OptionResponse> {
        return apiService.identityanswerpost(identityId,OptionData( option1,
                                                         option2,
                                                         option3,
                                                         option4,
                                                         option5,
                                                         option6,
                                                         option7,
                                                         option8,
                                                         option9,
        ))
    }
    suspend fun getById(identityId:Int): BaseResp<getByIdResponse?> {
        return apiService.getById(identityId)
    }
}