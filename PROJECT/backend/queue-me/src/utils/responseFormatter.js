

function formatResponse (isSucces=true, respMsg="no message", respDataName=null ,respData=null){
    const respStatus = isSucces?"success":"fail";
    if (!respData){
        return {status: respStatus, message: respMsg};
    }
    if (!respDataName){
        return {status: respStatus, message: respMsg, data: respData};
    }
    return {status: respStatus, message: respMsg, [respDataName]: respData};
}

module.exports = formatResponse;