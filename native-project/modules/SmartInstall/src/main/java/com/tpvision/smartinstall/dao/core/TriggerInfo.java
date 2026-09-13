package com.tpvision.smartinstall.dao.core;

import com.tpvision.smartinstall.trigger.TriggerUtils;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trigger_info")
public class TriggerInfo {
   public static final String TRIGGER_ACTIVE_YES = TriggerUtils.TriggerActiveState.Yes.name();
   public static final String TRIGGER_ACTIVE_NO = TriggerUtils.TriggerActiveState.No.name();
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "trigger_active")
   private String triggerActive = "No";
   @Column(name = "trigger_type")
   private String triggerType;
   @Column(name = "trigger_condition")
   private String triggerCondition;
   @Column(name = "target")
   private String target;
   @Column(name = "clone_type")
   private String cloneType;
   @Column(name = "clone_id")
   private Integer cloneId;
   @Column(name = "created")
   private Date created;
   @Column(name = "created_by")
   private String createdBy;
   @Column(name = "last_edit")
   private Date lastEdit;
   @Column(name = "note")
   private String note;
   @Column(name = "last_trigger")
   private Date lastTrigger;
   @Column(name = "dolist")
   private String dolist;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getTriggerActive() {
      return this.triggerActive;
   }

   public void setTriggerActive(String triggerActive) {
      this.triggerActive = triggerActive;
   }

   public String getTriggerType() {
      return this.triggerType;
   }

   public void setTriggerType(String triggerType) {
      this.triggerType = triggerType;
   }

   public String getTriggerCondition() {
      return this.triggerCondition;
   }

   public void setTriggerCondition(String triggerCondition) {
      this.triggerCondition = triggerCondition;
   }

   public String getTarget() {
      return this.target;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public String getCloneType() {
      return this.cloneType;
   }

   public void setCloneType(String cloneType) {
      this.cloneType = cloneType;
   }

   public Integer getCloneId() {
      return this.cloneId;
   }

   public void setCloneId(Integer cloneId) {
      this.cloneId = cloneId;
   }

   public Date getCreated() {
      return this.created;
   }

   public void setCreated(Date created) {
      this.created = created;
   }

   public String getCreatedBy() {
      return this.createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public Date getLastEdit() {
      return this.lastEdit;
   }

   public void setLastEdit(Date lastEdit) {
      this.lastEdit = lastEdit;
   }

   public String getNote() {
      return this.note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public Date getLastTrigger() {
      return this.lastTrigger;
   }

   public void setLastTrigger(Date lastTrigger) {
      this.lastTrigger = lastTrigger;
   }

   public void setDolist(String dolist) {
      this.dolist = dolist;
   }

   public String getDolist() {
      return this.dolist;
   }
}
