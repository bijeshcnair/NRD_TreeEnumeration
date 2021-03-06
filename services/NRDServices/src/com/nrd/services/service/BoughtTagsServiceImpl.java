/*Copyright (c) 2017-2018 vanenburgsoftware.com All Rights Reserved.
 This software is the confidential and proprietary information of vanenburgsoftware.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with vanenburgsoftware.com*/
package com.nrd.services.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.nrd.services.BoughtTags;
import com.nrd.services.Trees;


/**
 * ServiceImpl object for domain model class BoughtTags.
 *
 * @see BoughtTags
 */
@Service("NRDServices.BoughtTagsService")
public class BoughtTagsServiceImpl implements BoughtTagsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoughtTagsServiceImpl.class);

    @Autowired
	@Qualifier("NRDServices.TreesService")
	private TreesService treesService;

    @Autowired
    @Qualifier("NRDServices.BoughtTagsDao")
    private WMGenericDao<BoughtTags, String> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<BoughtTags, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "NRDServicesTransactionManager")
    @Override
	public BoughtTags create(BoughtTags boughtTags) {
        LOGGER.debug("Creating a new BoughtTags with information: {}", boughtTags);
        BoughtTags boughtTagsCreated = this.wmGenericDao.create(boughtTags);
        if(boughtTagsCreated.getTrees() != null) {
            Trees trees = boughtTagsCreated.getTrees();
            LOGGER.debug("Creating a new child Trees with information: {}", trees);
            trees.setBoughtTags(boughtTagsCreated);
            treesService.create(trees);
        }
        return boughtTagsCreated;
    }

	@Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public BoughtTags getById(String boughttagsId) throws EntityNotFoundException {
        LOGGER.debug("Finding BoughtTags by id: {}", boughttagsId);
        BoughtTags boughtTags = this.wmGenericDao.findById(boughttagsId);
        if (boughtTags == null){
            LOGGER.debug("No BoughtTags found with id: {}", boughttagsId);
            throw new EntityNotFoundException(String.valueOf(boughttagsId));
        }
        return boughtTags;
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public BoughtTags findById(String boughttagsId) {
        LOGGER.debug("Finding BoughtTags by id: {}", boughttagsId);
        return this.wmGenericDao.findById(boughttagsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "NRDServicesTransactionManager")
	@Override
	public BoughtTags update(BoughtTags boughtTags) throws EntityNotFoundException {
        LOGGER.debug("Updating BoughtTags with information: {}", boughtTags);
        this.wmGenericDao.update(boughtTags);

        String boughttagsId = boughtTags.getTag();

        return this.wmGenericDao.findById(boughttagsId);
    }

    @Transactional(value = "NRDServicesTransactionManager")
	@Override
	public BoughtTags delete(String boughttagsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting BoughtTags with id: {}", boughttagsId);
        BoughtTags deleted = this.wmGenericDao.findById(boughttagsId);
        if (deleted == null) {
            LOGGER.debug("No BoughtTags found with id: {}", boughttagsId);
            throw new EntityNotFoundException(String.valueOf(boughttagsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public Page<BoughtTags> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all BoughtTags");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
    @Override
    public Page<BoughtTags> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all BoughtTags");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service NRDServices for table BoughtTags to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TreesService instance
	 */
	protected void setTreesService(TreesService service) {
        this.treesService = service;
    }

}

