/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.math.BigDecimal;
import org.htng._2011b.HTNGHelper;
import org.htng._2011b.HTNGResponseBaseType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.SuccessType;

public class BaseHtngService {
    public HTNGResponseBaseType successResponse() {
        HTNGResponseBaseType res = new HTNGResponseBaseType();
        SuccessType returnSuccess = new SuccessType();
        res.setSuccess(returnSuccess);
        res.setEchoToken("EchoToken-2092234436");
        res.setVersion(new BigDecimal("2.0"));
        return res;
    }

    public HTNGResponseBaseType failureResponse(String message) {
        HTNGResponseBaseType res = new HTNGResponseBaseType();
        ErrorsType _returnErrors = HTNGHelper.getErrorsType(message);
        res.setErrors(_returnErrors);
        res.setEchoToken("EchoToken-2092234436");
        res.setVersion(new BigDecimal("2.0"));
        return res;
    }
}

