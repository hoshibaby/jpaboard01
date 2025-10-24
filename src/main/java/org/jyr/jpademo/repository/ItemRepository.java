package org.jyr.jpademo.repository;

import org.jyr.jpademo.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
