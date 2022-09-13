package developing.springboot.currencyexchangeboothapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResponseDto {
    private String currency;
    private Operation operation;
    private int dealsCount;
    private Integer totalSum;
}
