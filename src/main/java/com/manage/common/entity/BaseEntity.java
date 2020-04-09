package com.manage.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.search.annotations.DocumentId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EntityListeners(EntityListener.class)
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4889348620996664183L;

  /** "创建日期"属性名称 */
  public static final String CREATED_TIME_PROPERTY_NAME = "createdTime";

  /** "ID"属性名称 */
  public static final String ID_PROPERTY_NAME = "id";

  /** "修改日期"属性名称 */
  public static final String UPDATED_TIME_PROPERTY_NAME = "updatedTime";
  

  public BaseEntity() {}
  
  public BaseEntity(Long id) {
    this.id = id;
  }

  public BaseEntity(Long id, Long createdBy, Date createdTime, Long updatedBy, Date updatedTime,
                    Integer isDeleted) {
    this.id = id;
    this.createdBy = createdBy;
    this.createdTime = createdTime;
    this.updatedBy = updatedBy;
    this.updatedTime = updatedTime;
    this.isDeleted = isDeleted;
  }

  @Id
  @DocumentId
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "created_by")
  @JsonIgnore
  protected Long createdBy;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_time")
  @JsonIgnore
  @CreatedDate
  protected Date createdTime;

  @Column(name = "updated_by")
  @JsonIgnore
  protected Long updatedBy;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_time")
  @JsonIgnore
  @LastModifiedDate
  protected Date updatedTime;

  /**
   * 逻辑删除标记（0-未删除；1-已删除；）
   */
  @Column(name = "is_deleted")
  @JsonIgnore
  protected Integer isDeleted;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * @return the createdBy
   */
  public Long getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the createdTime
   */
  public Date getCreatedTime() {
    return createdTime;
  }

  /**
   * @param createdTime the createdTime to set
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * @return the updatedBy
   */
  public Long getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(Long updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the updatedTime
   */
  public Date getUpdatedTime() {
    return updatedTime;
  }

  /**
   * @param updatedTime the updatedTime to set
   */
  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }

  /**
   * @return the isDeleted
   */
  public Integer getIsDeleted() {
    return isDeleted;
  }

  /**
   * @param isDeleted the isDeleted to set
   */
  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted;
  }

}
