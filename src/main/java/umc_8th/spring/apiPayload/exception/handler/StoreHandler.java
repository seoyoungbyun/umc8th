package umc_8th.spring.apiPayload.exception.handler;

import umc_8th.spring.apiPayload.code.BaseErrorCode;
import umc_8th.spring.apiPayload.exception.GeneralException;

public class StoreHandler extends GeneralException {
    public StoreHandler(BaseErrorCode errorCode) {super(errorCode);}
}
