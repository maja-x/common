package id.maja.x.common.iso8583;


/**
 * ProcessingCodeHelper
 *
 * ProcessingCode berisi 6 digit dari bit3 ISO8583
 * Digit 1-2 merupakan tipe transaksi
 * Digit 3-4 merupakan origin account type
 * Digit 4-6 merupakan destination account type
 */
public class ProcessingCodeHelper {
    public static String TRANSACTION_TYPE_REGISTER = "00";
    public static String TRANSACTION_TYPE_GET_ACCOUNT = "01";
    public static String TRANSACTION_TYPE_INQUIRY = "38";
    public static String TRANSACTION_TYPE_UPDATE_PROFILE = "03";
    public static String TRANSACTION_TYPE_DELETE_BILLING = "04";
    public static String TRANSACTION_TYPE_PAYMENT = "18";
    public static String TRANSACTION_TYPE_REVERSAL = "06";
    public static String TRANSACTION_TYPE_UPDATE_INVOICE = "07";
    public static String TRANSACTION_TYPE_RECONSILE = "08";
    public static String ACCOUNT_TYPE_UNSPECIFIED = "00";
    public static String ACCOUNT_TYPE_SAVINGS = "10";
    public static String ACCOUNT_TYPE_CHECK = "20";
    public static String ACCOUNT_TYPE_CREDIT = "30";

    /**
     * Get Transaction Type from String
     * Mendapatkan tipe transaksi dari string yang dikirim via ISO
     *
     * @param str bit3
     * @return String transaction type
     */
    public static String getTransactionTypeFromString(String str) {
        return str.substring(0, 2);
    }

    /**
     * Get Origin Account Type from String
     * Mendapatkan origin account type dari string yang dikirim ISO
     *
     * @param str bit3
     * @return String origin account type
     */
    public static String getOriginAccountTypeFromString(String str) {
        return str.substring(2, 4);
    }

    /**
     * Get Destination Account Type from String
     * Mendapatkan destination account type dari string yang dikirim ISO
     *
     * @param str bit3
     * @return String destination account type
     */
    public static String getDestinationAccountTypeFromString(String str) {
        return str.substring(4, 6);
    }
}