/*Copyright (c) 2017-2018 vanenburgsoftware.com All Rights Reserved.
 This software is the confidential and proprietary information of vanenburgsoftware.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with vanenburgsoftware.com*/
package com.nrd.services;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.wavemaker.runtime.data.annotations.ServerDefinedProperty;
import com.wavemaker.runtime.data.replacers.Scope;
import com.wavemaker.runtime.data.replacers.providers.VariableType;

/**
 * Trees generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`Trees`")
public class Trees implements Serializable {

    private String treeTag;
    private String specieCode;
    private String type;
    private String block;
    @Type(type = "DateTime")
    private LocalDateTime enumerationDatetime;
    private Long estimatedHeight;
    private Long estimatedDiameter;
    private Date createdDate;
    @ServerDefinedProperty( value = VariableType.USER_NAME, scopes = { Scope.INSERT, Scope.UPDATE })
    private String createdBy;
    private Date modifiedDate;
    @ServerDefinedProperty( value = VariableType.USER_NAME, scopes = { Scope.INSERT, Scope.UPDATE })
    private String modifiedBy;
    private BoughtTags boughtTags;
    private TreeSpecies treeSpecies;

    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", 
            parameters = @Parameter(name = "property", value = "boughtTags"))
    @GeneratedValue(generator = "generator")
    @Column(name = "`TreeTag`", nullable = false, insertable = false, updatable = false, length = 255)
    public String getTreeTag() {
        return this.treeTag;
    }

    public void setTreeTag(String treeTag) {
        this.treeTag = treeTag;
    }

    @Column(name = "`SpecieCode`", nullable = true, length = 255)
    public String getSpecieCode() {
        return this.specieCode;
    }

    public void setSpecieCode(String specieCode) {
        this.specieCode = specieCode;
    }

    @Column(name = "`Type`", nullable = true, length = 255)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "`Block`", nullable = true, length = 255)
    public String getBlock() {
        return this.block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    @Column(name = "`Enumeration datetime`", nullable = true)
    public LocalDateTime getEnumerationDatetime() {
        return this.enumerationDatetime;
    }

    public void setEnumerationDatetime(LocalDateTime enumerationDatetime) {
        this.enumerationDatetime = enumerationDatetime;
    }

    @Column(name = "`Estimated height`", nullable = true, scale = 0, precision = 10)
    public Long getEstimatedHeight() {
        return this.estimatedHeight;
    }

    public void setEstimatedHeight(Long estimatedHeight) {
        this.estimatedHeight = estimatedHeight;
    }

    @Column(name = "`Estimated diameter`", nullable = true, scale = 0, precision = 10)
    public Long getEstimatedDiameter() {
        return this.estimatedDiameter;
    }

    public void setEstimatedDiameter(Long estimatedDiameter) {
        this.estimatedDiameter = estimatedDiameter;
    }

    @Column(name = "`CreatedDate`", nullable = true)
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "`CreatedBy`", nullable = true, length = 255)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "`ModifiedDate`", nullable = true)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name = "`ModifiedBy`", nullable = true, length = 255)
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    public BoughtTags getBoughtTags() {
        return this.boughtTags;
    }

    public void setBoughtTags(BoughtTags boughtTags) {
        if(boughtTags != null) {
            this.treeTag = boughtTags.getTag();
        }

        this.boughtTags = boughtTags;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`SpecieCode`", referencedColumnName = "`SpecieCode`", insertable = false, updatable = false)
    public TreeSpecies getTreeSpecies() {
        return this.treeSpecies;
    }

    public void setTreeSpecies(TreeSpecies treeSpecies) {
        if(treeSpecies != null) {
            this.specieCode = treeSpecies.getSpecieCode();
        }

        this.treeSpecies = treeSpecies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trees)) return false;
        final Trees trees = (Trees) o;
        return Objects.equals(getTreeTag(), trees.getTreeTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTreeTag());
    }
}

