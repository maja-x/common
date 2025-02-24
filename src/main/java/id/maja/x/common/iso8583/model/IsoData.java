package id.maja.x.common.iso8583.model;

import com.solab.iso8583.IsoMessage;
import lombok.Data;

import java.util.Date;

@Data
public class IsoData {
    private String rawData;
    private String primaryAccountNumber;
    private String processingCode;
    private Long amount;
    private Date transmissionDateTime;
    private String systemTraceAuditNumber;
    private String transactionLocalTime;
    private String transactionLocalDate;
    private Date expirationDate;
    private Date settlementDate;
    private String distributionChannel;
    private String collectingAgent;
    private String dataTrack2;
    private String retrievalReferenceNumber;
    private String responseCode;
    private String cardAcceptorTerminalId;
    private String cardAcceptorId;
    private String cardAcceptorLocation;
    private String data;
    private String currencyCode;
    private String reservedData;
    private String originalDataElement;
    private String networkManagementInformationCode;

    public static IsoData fromIsoMessage(IsoMessage isoMessage) {
        IsoData isoData = new IsoData();
        isoData.setRawData(isoMessage.debugString());
        isoData.setPrimaryAccountNumber(isoMessage.getObjectValue(2).toString());
        isoData.setProcessingCode(isoMessage.getObjectValue(3).toString());
        isoData.setAmount(isoMessage.getObjectValue(4) == null ? null : Long.parseLong(isoMessage.getObjectValue(4).toString()));
        isoData.setTransmissionDateTime(isoMessage.getObjectValue(7) == null ? null : new Date(Long.parseLong(isoMessage.getObjectValue(7).toString())));
        isoData.setSystemTraceAuditNumber(isoMessage.getObjectValue(11).toString());
        isoData.setTransactionLocalTime(isoMessage.getObjectValue(12).toString());
        isoData.setTransactionLocalDate(isoMessage.getObjectValue(13).toString());
        isoData.setExpirationDate(isoMessage.getObjectValue(14) == null ? null : new Date(Long.parseLong(isoMessage.getObjectValue(14).toString())));
        isoData.setSettlementDate(isoMessage.getObjectValue(15) == null ? null : new Date(Long.parseLong(isoMessage.getObjectValue(15).toString())));
        isoData.setDistributionChannel(isoMessage.getObjectValue(16).toString());
        isoData.setCollectingAgent(isoMessage.getObjectValue(17).toString());
        isoData.setDataTrack2(isoMessage.getObjectValue(18).toString());
        isoData.setRetrievalReferenceNumber(isoMessage.getObjectValue(22).toString());
        isoData.setResponseCode(isoMessage.getObjectValue(39).toString());
        isoData.setCardAcceptorTerminalId(isoMessage.getObjectValue(41).toString());

        return isoData;
    }
}
