package com.manage.common.entity;

import javax.persistence.PrePersist;
import java.util.Date;

public class EntityListener {

  /**
   * 保存前处理
   * 
   * @param entity 基类
   */
  @PrePersist
  public void prePersist(BaseEntity entity) {
    if (entity.getCreatedTime() == null) {
      entity.setCreatedTime(new Date());
    }
    if (entity.getIsDeleted() == null) {
      entity.setIsDeleted(0);
    }


  }

}
