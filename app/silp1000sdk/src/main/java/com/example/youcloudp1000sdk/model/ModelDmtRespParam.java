package com.example.youcloudp1000sdk.model;

import java.io.Serializable;

public class ModelDmtRespParam implements Serializable {
	public String tranId;
    public String amount;
    public String charges;
    public String code;
    public String referenceNo;
    public String beneName;
    public String responseTimestamp;
    public String errorMessage;
    public String errorCode;
    public String rrn;
	@Override
	public String toString() {
		return "ModelDmtRespParam [tranId=" + tranId + ", amount=" + amount + ", charges=" + charges + ", code=" + code + ", referenceNo=" + referenceNo + ", beneName=" + beneName + ", responseTimestamp="
				+ responseTimestamp + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode + ", rrn=" + rrn + "]";
	}
}
