package id.maja.x.common.iso8583;


import com.solab.iso8583.IsoMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Getter
public class IsoMessageHelper {
    // Mapping Bit ISO 8583
    public static int PRIMARY_ACCOUNT_NUMBER = 2;
    public static int PROCESSING_CODE = 3;
    public static int AMOUNT = 4;
    public static int TRANSMISSION_DATE_TIME = 7;
    public static int STAN = 11;
    public static int TRANSACTION_LOCAL_TIME = 12;
    public static int TRANSACTION_LOCAL_DATE = 13;
    public static int EXPIRATION_DATE = 14;
    public static int SETTLEMENT_DATE = 15;
    public static int DISTRIBUTION_CHANNEL = 18;
    public static int COLLECTING_AGENT = 32;
    public static int DATA_TRACK_2 = 35;
    public static int RRN = 37;
    public static int RC = 39;
    public static int CARD_ACCEPTOR_TERMINAL_ID = 41;
    public static int CARD_ACCEPTOR_ID = 42;
    public static int CARD_ACCEPTOR_LOCATION = 43;
    public static int DATA = 48;
    public static int CURRENCY_CODE = 49;
    public static int RESERVED_DATA = 63;
    // MTI + STAN + TRANSMISSION_DATE_TIME + XXX + COLLECTING_AGENT + AMOUNT
    public static int ORIGINAL_DATA_ELEMENT = 90;
    public static int NETWORK_MANAGEMENT_INFO_CODE = 70;

    // Tipe Transaksi
    public static String TRANSACTION_TYPE_INQUIRY = "INQUIRY";
    public static String TRANSACTION_TYPE_PAYMENT = "PAYMENT";
    public static String TRANSACTION_TYPE_REVERSAL = "REVERSAL";

    // Kode MTI
    public static int FINANCIAL_REQUEST_MTI = 0x0200;
    public static int FINANCIAL_RESPONSE_MTI = 0x0210;
    public static int REVERSAL_REQUEST_MTI = 0x0400;
    public static int REVERSAL_RESPONSE_MTI = 0x0410;
    public static int NETWORK_MANAGEMENT_REQUEST_MTI = 0x0800;
    public static int NETWORK_MANAGEMENT_RESPONSE_MTI = 0x0810;

    // Variable ISO 8583 Message
    // Detail data ada di variable data
    private final String mti;
    private String primaryAccountNumber;
    private String processingCode;
    private Double amount;
    private final Date transmissionDateTime;
    private final String stan;
    private Date transactionLocalTime;
    private Date transactionLocalDate;
    private String cardExpirationDate;
    private Date settlementDate;
    private String distributionChannel;
    private String collectingAgent;
    private String dataTrack2;
    private String rrn;
    private final String rc;
    private String cardAcceptorTerminalId;
    private String cardAcceptorId;
    private String cardAcceptorLocation;
    private String data;
    private String reservedData;
    private String currencyCode;
    private String originalDataElement;
    private String networkManagementInfoCode;
    private final String raw;

    /**
     * ISOMessageHelper constructor
     * untuk konversi dari isoMessage ke variable
     *
     */
    public IsoMessageHelper(IsoMessage isoMessage) {
        // 3 data ini ada di semua request
        this.raw = isoMessage.debugString();
        this.transmissionDateTime = isoMessage.getObjectValue(TRANSMISSION_DATE_TIME);
        this.stan = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(STAN), null);
        this.rc = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(RC), null);
        this.mti = getMtiFromIsoMessage(isoMessage);

        if (isoMessage.getType() == NETWORK_MANAGEMENT_REQUEST_MTI
                || isoMessage.getType() == NETWORK_MANAGEMENT_RESPONSE_MTI) {
            // Special bit70 untuk MTI 0x0800, 0x0810
            this.networkManagementInfoCode = isoMessage.getObjectValue(NETWORK_MANAGEMENT_INFO_CODE);
        } else {
            // Data berikut ada di MTI 0x02000, 0x0210, 0x0400, 0x0410
            this.primaryAccountNumber = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(PRIMARY_ACCOUNT_NUMBER), "");
            this.processingCode = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(PROCESSING_CODE), "");
            this.amount = Double.valueOf(isoMessage.getObjectValue(AMOUNT));
            this.transactionLocalDate = isoMessage.getObjectValue(TRANSACTION_LOCAL_DATE);
            this.transactionLocalTime = isoMessage.getObjectValue(TRANSACTION_LOCAL_TIME);
            this.cardExpirationDate = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(EXPIRATION_DATE), "");
            this.settlementDate = isoMessage.getObjectValue(SETTLEMENT_DATE);
            this.distributionChannel = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(DISTRIBUTION_CHANNEL), "");
            this.collectingAgent = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(COLLECTING_AGENT), "");
            this.dataTrack2 = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(DATA_TRACK_2), "");
            this.rrn = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(RRN), "");
            this.cardAcceptorId = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(CARD_ACCEPTOR_ID), "");
            this.cardAcceptorLocation = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(CARD_ACCEPTOR_LOCATION), "");
            this.cardAcceptorTerminalId = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(CARD_ACCEPTOR_TERMINAL_ID),
                    "");
            this.data = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(DATA), "");
            this.reservedData = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(RESERVED_DATA), "");
            this.currencyCode = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(CURRENCY_CODE), "");
            // this.originalDataElement =
            // StringUtils.defaultIfEmpty(isoMessage.getObjectValue(ORIGINAL_DATA_ELEMENT),
            // null);
        }

        if (isoMessage.getType() == REVERSAL_REQUEST_MTI || isoMessage.getType() == REVERSAL_RESPONSE_MTI) {
            // Hanya ada di Reversal Request 0x0400, 0x0410
            this.originalDataElement = StringUtils.defaultIfEmpty(isoMessage.getObjectValue(ORIGINAL_DATA_ELEMENT), "");
        }
    }

    public static String getMtiFromIsoMessage(IsoMessage isoMessage) {
        return switch (isoMessage.getType()) {
            case 0x0800 -> "0800";
            case 0x0810 -> "0810";
            case 0x0200 -> "0200";
            case 0x0210 -> "0210";
            case 0x0400 -> "0400";
            case 0x0410 -> "0410";
            default -> "";
        };
    }

    public static String getProcessingCodeFromIsoMessage(IsoMessage isoMessage) {
        return StringUtils.defaultIfEmpty(isoMessage.getObjectValue(PROCESSING_CODE), "");
    }

    public static String getDistributionChannelFromIsoMessage(IsoMessage isoMessage) {
        return StringUtils.defaultIfEmpty(isoMessage.getObjectValue(DISTRIBUTION_CHANNEL), "");
    }
    /**
     * Get Transaction Type
     *
     * @return INQUIRY | PAYMENT | REVERSAL
     */
    public String getTransactionType() {
        String type = ProcessingCodeHelper.getTransactionTypeFromString(this.processingCode);
        if (type.equals(ProcessingCodeHelper.TRANSACTION_TYPE_INQUIRY)) {
            return TRANSACTION_TYPE_INQUIRY;
        }
        if (type.equals(ProcessingCodeHelper.TRANSACTION_TYPE_PAYMENT)) {
            return TRANSACTION_TYPE_PAYMENT;
        }
        if (type.equals(ProcessingCodeHelper.TRANSACTION_TYPE_REVERSAL)) {
            return TRANSACTION_TYPE_REVERSAL;
        }
        return null;
    }

    public static String generateTransmissionDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

}
