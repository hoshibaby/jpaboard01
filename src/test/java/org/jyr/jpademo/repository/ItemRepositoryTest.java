package org.jyr.jpademo.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.jyr.jpademo.domain.Item;
import org.jyr.jpademo.domain.ItemSellStatus;
import org.jyr.jpademo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2

public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertItem(){
        Item item = Item.builder()
                .itemNm("포토카드2")
                .itemDetail("정한 랜티큘러")
                .itemSellStatus(ItemSellStatus.판매중)
//                판매중은 자동완성으로 불러올수있어
                .price(1000)
                .stockNumber(20)
                .build();
        itemRepository.save(item);
    }

    @Test
    public void findAll(){
        List<Item> items = itemRepository.findAll();
        for (Item item : items) {
            log.info(item.toString());
        }
    }

    //ID로 찾기
    @Test
    public void findById(){
        Item item = itemRepository.findById(6L).get();
        Item item1 = itemRepository.findById(2L).orElse(null);
        Item item2 = itemRepository.findById(1L).orElse(null);
        log.info(item1);
        log.info(item2);
        log.info(item.toString());
    }

    // 표에 들어간 값을 삭제
    @Test
    public void deleteById(){
        itemRepository.deleteById(1L);
    }

    // 표에 들어간 값 수정

    @Test
    public void updateItem(){
        Item item = itemRepository.findById(2L).get();
        item.setStockNumber(20);
        item.setPrice(1500);
        item.setItemDetail("호시 키링");
        itemRepository.save(item);
    }


}
