package az.spring.springboot.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Pagination {
    private int offset;
    private int currentPage;
    private int totalPage;
    private int totalElement;
}
