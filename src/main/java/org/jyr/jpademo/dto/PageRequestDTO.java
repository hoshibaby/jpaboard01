package org.jyr.jpademo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PageRequestDTO {
    @Builder.Default
    private int page =1; //페이지 번호는 1 넣고
    @Builder.Default
    private int size =3; // 보통 10을 넣는데 레코드 많이 넣기 힘드니까아..
    private String link; //검색어 따라다니까 많아져서?
    private String type;
    private String keyword;

    //title은 t, content는 c, 작성자는 w 로 해서 한자한자 하면 문자열 스트림이 되게 / t,c,w, tc,tw,twc
    //문자 단위로 스트링 배열하여 리턴하라
    public String[] getTypes(){
        if(type==null || type.isEmpty()){ //없으면  null
            return null;
        }
        return type.split(""); // 값이 있으면 문자열 배열로 리턴twc=>types[0]="t" 1은 w, 2는 c 배열 형태로 리턴
    }
    //pageable은 data domain 가지고 와야해
    // size는 페이지 사이즈임
    // bno는 오름차순 title은 내림차순 다양하다면 props 여러 형태로 들어간다면
    // 전체페이지는 레코드 갯수로 알아서 계산해서 반영한데
    public Pageable getPageable(String...props){
         return PageRequest.of(this.page-1, size, Sort.by(props).descending());
         //page 0으니까 1-1은 0 , Sort.by 현재 내가 전달한 props로 정렬하겠다. id로 descending 내림차순(정렬차순)하겠다
    } //jpa는 0페이지부터 인지하니까 원래 페이지보다 -1을 뺀 파라미터 값을 넣어줘.

    //string으로 하면 값을 변하지 못해 +1 하면 가비지가 생겨
    // StringBuilder 나 버퍼로 넣어서 다 넣고 나서 string으로 바꿔줘. getLink로 다른 정보 넣어
    public String getLink(){
        if(link==null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);
            if(type!=null && type.length()>0){
                builder.append("&type="+type);
            }
           if(keyword!=null && keyword.length()>0){
               builder.append("&keyword="+keyword);
           }
           link=builder.toString(); //스트링형태로 링크를 만들고
        }
        return link;
    }
}
