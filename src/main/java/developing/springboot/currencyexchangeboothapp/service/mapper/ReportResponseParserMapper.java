package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.Operation;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ReportResponseParserMapper {
    private static final String NATION_CURRENCY = "UAH";

    public ReportResponseDto toDto(ReportResponse reportResponse) {
        ReportResponseDto responseDto = new ReportResponseDto();
        Operation operation = reportResponse.getCcyBuy().equals(NATION_CURRENCY)
                ? Operation.SOLD : Operation.BOUGHT;
        responseDto.setOperation(operation);
        String currency = operation == Operation.SOLD
                ? reportResponse.getCcySale() : reportResponse.getCcyBuy();
        responseDto.setCurrency(currency);
        responseDto.setDealsCount(reportResponse.getDealsCount());
        responseDto.setTotalSum(reportResponse.getTotalCcySale());
        return responseDto;
    }
}
