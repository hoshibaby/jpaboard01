package org.jyr.jpademo.domain;

// 상속만 할거고 객체는 안만들거라서 abstarct 달았어

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

// ------------- 추상클래스로 잡으면 꼭 써줘야해-------------
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
// ------------------------------------------------------
@Getter
public abstract class BaseEntity { //객체 생성하지 않는 추상클래스
    @CreationTimestamp //    저장된 시간 기준으로
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="regdate", nullable = false)

    private LocalDateTime regDate; //reg_date

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="updatedate") //업데이트 된 시간으로 정정됨.
    private LocalDateTime updateDate;
}
