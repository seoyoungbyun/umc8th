package umc_8th.spring.apiPayload.exception.handler;

import umc_8th.spring.apiPayload.code.BaseErrorCode;
import umc_8th.spring.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
