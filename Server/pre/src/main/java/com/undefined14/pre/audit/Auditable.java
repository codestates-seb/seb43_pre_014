package com.undefined14.pre.audit;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 리스너로 엔티티 추가시 현재 클래스 상속 받으면 아래 컬럼 같이 추가됩니다.
public class Auditable {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    // 현재는 created_at 만 추가했는데,
    // 추후 수정시간 등을 추가하고 싶다면
    // 테이블 명세서 수정 후 추가하면 됩니다.
}
