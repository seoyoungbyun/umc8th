package umc_8th.spring.apiPayload.exception.handler;

import umc_8th.spring.apiPayload.code.BaseErrorCode;
import umc_8th.spring.apiPayload.exception.GeneralException;

public class MemberMissionHandler extends GeneralException {
    public MemberMissionHandler(BaseErrorCode errorCode) {super(errorCode);}
}
