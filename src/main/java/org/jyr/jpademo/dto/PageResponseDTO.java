package org.jyr.jpademo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString

public class PageResponseDTO<E> {
    private int page; //현재페이지
    private int size; //블록사이즈    검색 기능 없을 거면 위 두개만 있어도 됨
    private int total; //전체레코드 수

    private int start; //블록의 시작페이지 페이지 넘거 자른 거 의 시작
    private int end; //블록의 end 페이지 페이지 넘버 자르는 것
    private boolean next; //다음 페이지 여부
    private boolean prev; //이전페이지 여부
    public List<E> dtoList;  //페이징할 dtoList

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        //괄호 안에 있는 것들이 들어와서
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page/(double)size))*size; //반올림 Math.ceil
        this.start = this.end - (size-1);
        int last = (int)(Math.ceil(this.total/(double)size)); //레코드가 5개 /3 1.3 이니까 ceil을 하면 2page가 됨
        this.end=end>last?last:end; //
        this.prev=this.start>1; //1보다 크면 이전으로 돌아간다
        this.next=this.end<last; // end가 last보다 작으면 next를 누를 수 있다.
        // 지난 수업에 total > this.end*size 를 저렇게 바꾼거야.
        //지난 번에는 total>this.end*size
    }
}
