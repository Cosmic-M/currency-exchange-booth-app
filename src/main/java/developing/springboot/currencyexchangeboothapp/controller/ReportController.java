package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.service.DealService;
import developing.springboot.currencyexchangeboothapp.service.mapper.DealMapper;
import developing.springboot.currencyexchangeboothapp.util.DateTimePatternUtil;
import java.time.LocalDate;
import java.util.List;

import developing.springboot.currencyexchangeboothapp.util.SortDealsUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
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
    @NonNull
    private DealService dealService;
    @NonNull
    private DealMapper dealMapper;

    @GetMapping("/report")
    public List<ReportResponse> doReport() {
        return dealService.doReport();
    }

    @ApiOperation(value = "get deal's list")
    @GetMapping("/ccy-period")
    public List<DealResponseDto> getDeals(@RequestParam String ccySale,
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
                                          @ApiParam(value = "default value is id")
                                          String sortBy) {
        Sort sort = SortDealsUtil.getSortingDeals(sortBy);
        List<Deal> deals = dealService.findAllByCcyAndPeriod(ccySale, from, to, PageRequest.of(page, count, sort));
        return deals.stream()
                .map(dealMapper::toDto)
                .toList();
    }
}
