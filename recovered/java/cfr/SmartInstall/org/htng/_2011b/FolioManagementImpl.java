/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import com.tpvision.smartinstall.pms.PmsUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.htng._2011b.FolioManagement;
import org.htng._2011b.HTNGHelper;
import org.htng._2011b.HTNGHotelFolioRQ;
import org.htng._2011b.HTNGHotelFolioRS;
import org.opentravel.ota._2003._05.CostingItemType;
import org.opentravel.ota._2003._05.PkgInvoiceDetail;
import org.opentravel.ota._2003._05.SuccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName="HTNG_KioskService", portName="FolioManagement", targetNamespace="http://htng.org/2011B", wsdlLocation="classpath:/HTNG/services/HTNG_KioskService.wsdl", endpointInterface="org.htng._2011b.FolioManagement")
public class FolioManagementImpl
implements FolioManagement {
    private static final Logger LOG = LoggerFactory.getLogger(FolioManagementImpl.class.getName());

    @Override
    public HTNGHotelFolioRS retrieveFolio(HTNGHotelFolioRQ htngHotelFolioRQ) {
        LOG.info("Executing operation retrieveFolio");
        System.out.println(htngHotelFolioRQ);
        HTNGHotelFolioRS _return = new HTNGHotelFolioRS();
        try {
            String roomId = htngHotelFolioRQ.getUniqueID().getID();
            HTNGHotelFolioRS.Folios _returnFolios = new HTNGHotelFolioRS.Folios();
            ArrayList<HTNGHotelFolioRS.Folios.Folio> _returnFoliosFolio = new ArrayList<HTNGHotelFolioRS.Folios.Folio>();
            HTNGHotelFolioRS.Folios.Folio _returnFoliosFolioVal1 = new HTNGHotelFolioRS.Folios.Folio();
            PkgInvoiceDetail revenueSummary = new PkgInvoiceDetail();
            PkgInvoiceDetail.CostingItems costingItems = new PkgInvoiceDetail.CostingItems();
            List<CostingItemType> costingItemList = HTNGHelper.getBillItems(roomId);
            costingItems.getCostingItem().addAll(costingItemList);
            revenueSummary.setCostingItems(costingItems);
            PkgInvoiceDetail.GrossAmount grossAmount = new PkgInvoiceDetail.GrossAmount();
            grossAmount.setAmount(new BigDecimal(PmsUtils.getBalance(roomId)));
            grossAmount.setCurrencyCode(PmsUtils.getCurrency());
            revenueSummary.setGrossAmount(grossAmount);
            _returnFoliosFolioVal1.setRevenueSummary(revenueSummary);
            _returnFoliosFolioVal1.setFolioID(roomId);
            _returnFoliosFolio.add(_returnFoliosFolioVal1);
            _returnFolios.getFolio().addAll(_returnFoliosFolio);
            _return.setFolios(_returnFolios);
            _return.setEchoToken("EchoToken-2138837777");
            _return.setTargetName("TargetName-730997191");
            _return.setVersion(new BigDecimal("2.0"));
            _return.setSuccess(new SuccessType());
        }
        catch (Exception ex) {
            LOG.info(ex.getMessage(), ex);
            _return.setErrors(HTNGHelper.getErrorsType(ex.getMessage()));
        }
        return _return;
    }
}

