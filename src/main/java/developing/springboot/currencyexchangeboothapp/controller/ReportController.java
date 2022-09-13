package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.service.DealService;
import developing.springboot.currencyexchangeboothapp.service.mapper.DealMapper;
import developing.springboot.currencyexchangeboothapp.service.mapper.ReportResponseParserMapper;
import developing.springboot.currencyexchangeboothapp.util.DateTimePatternUtil;
import developing.springboot.currencyexchangeboothapp.util.SortDealsUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReportController {
    private final DealService dealService;
    private final DealMapper dealMapper;
    private final ReportResponseParserMapper reportResponseParserMapper;

    @ApiOperation(value = "get daily report")
    @GetMapping("/report")
    public List<ReportResponseDto> doReport() {
        List<ReportResponse> reportResponseList = dealService.doReport();
        return reportResponseList.stream()
                .map(reportResponseParserMapper::toDto)
                .toList();
    }

    @ApiOperation(value = "get deal's list by currency and period")
    @GetMapping("/ccy-period")
    public List<DealResponseDto> getDeals(@RequestParam @ApiParam(
            value = "Please assign currency from 'USD', 'EUR' or 'UAH'") String ccySale,
                                          @RequestParam @DateTimeFormat(pattern =
                                                  DateTimePatternUtil.DATE_PATTERN) LocalDate from,
                                          @RequestParam @DateTimeFormat(pattern =
                                                  DateTimePatternUtil.DATE_PATTERN) LocalDate to,
                                          @RequestParam(defaultValue = "20")
                                          @ApiParam(value = "default value is 20")
                                          Integer count,
                                          @RequestParam(defaultValue = "0")
                                          @ApiParam(value = "default value is 0")
                                          Integer page,
                                          @RequestParam(defaultValue = "id")
                                          @ApiParam(value = "default value is id (but you can "
                                                  + "apply next relevant meanings: ccySale, "
                                                  + "ccyBuy, ccySaleAmount, ccyBuyAmount, phone, "
                                                  + "dateTime, status)")
                                          String sortBy) {
        Sort sort = SortDealsUtil.getSortingDeals(sortBy);
        List<Deal> deals = dealService
                .findAllByCcyAndPeriod(ccySale, from, to, PageRequest.of(page, count, sort));
        return deals.stream()
                .map(dealMapper::toDto)
                .toList();
    }
}
