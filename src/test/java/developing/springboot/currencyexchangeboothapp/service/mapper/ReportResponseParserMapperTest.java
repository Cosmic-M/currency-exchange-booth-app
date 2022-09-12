package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.Operation;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportResponseParserMapperTest {
    @InjectMocks
    private ReportResponseParserMapper reportResponseParserMapper;
    private ReportResponse reportResponse;
    private ReportResponseDto expectReportResponseDto;

    @BeforeEach
    void setUp() {
        reportResponse = new ReportResponse() {
            private String ccySale = "USD";
            private String ccyBuy = "UAH";

            @Override
            public String getCcySale() {
                return ccySale;
            }

            @Override
            public String getCcyBuy() {
                return ccyBuy;
            }

            @Override
            public Integer getDealsCount() {
                return 10;
            }

            @Override
            public Integer getTotalCcySale() {
                return 1500;
            }

            @Override
            public void setCcySale(String ccySale) {
                this.ccySale = ccySale;
            }

            @Override
            public void setCcyBuy(String ccyBuy) {
                this.ccyBuy = ccyBuy;
            }
        };

        expectReportResponseDto = new ReportResponseDto();
        expectReportResponseDto.setCurrency("USD");
        expectReportResponseDto.setOperation(Operation.SOLD);
        expectReportResponseDto.setDealsCount(10);
        expectReportResponseDto.setTotalSum(1500);
    }

    @Test
    void toDto_detectCurrencyStatusAsSold_ok() {
        ReportResponseDto result = reportResponseParserMapper.toDto(reportResponse);
        Assertions.assertEquals(expectReportResponseDto.getCurrency(), result.getCurrency());
        Assertions.assertEquals(expectReportResponseDto.getOperation(), result.getOperation());
        Assertions.assertEquals(expectReportResponseDto.getDealsCount(), result.getDealsCount());
        Assertions.assertEquals(expectReportResponseDto.getTotalSum(), result.getTotalSum());
    }

    @Test
    void toDto_detectCurrencyStatusAsBought_ok() {
        reportResponse.setCcySale("UAH");
        reportResponse.setCcyBuy("USD");
        expectReportResponseDto.setOperation(Operation.BOUGHT);
        ReportResponseDto result = reportResponseParserMapper.toDto(reportResponse);
        Assertions.assertEquals(expectReportResponseDto.getCurrency(), result.getCurrency());
        Assertions.assertEquals(expectReportResponseDto.getOperation(), result.getOperation());
        Assertions.assertEquals(expectReportResponseDto.getDealsCount(), result.getDealsCount());
        Assertions.assertEquals(expectReportResponseDto.getTotalSum(), result.getTotalSum());
    }
}
