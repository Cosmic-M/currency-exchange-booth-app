package developing.springboot.currencyexchangeboothapp.dto;

public interface ReportResponse {
    String getCcySale();
    String getCcyBuy();
    Integer getDealsCount();
    Integer getTotalCcySale();
}
