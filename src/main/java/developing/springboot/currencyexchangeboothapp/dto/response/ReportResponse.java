package developing.springboot.currencyexchangeboothapp.dto.response;

public interface ReportResponse {
    String getCcySale();

    String getCcyBuy();

    Integer getDealsCount();

    Integer getTotalCcySale();

    void setCcySale(String ccySale);

    void setCcyBuy(String ccyBuy);
}
