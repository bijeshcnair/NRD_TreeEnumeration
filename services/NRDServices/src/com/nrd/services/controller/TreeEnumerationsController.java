/*Copyright (c) 2017-2018 vanenburgsoftware.com All Rights Reserved.
 This software is the confidential and proprietary information of vanenburgsoftware.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with vanenburgsoftware.com*/
package com.nrd.services.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.nrd.services.TreeEnumerations;
import com.nrd.services.service.TreeEnumerationsService;


/**
 * Controller object for domain model class TreeEnumerations.
 * @see TreeEnumerations
 */
@RestController("NRDServices.TreeEnumerationsController")
@Api(value = "TreeEnumerationsController", description = "Exposes APIs to work with TreeEnumerations resource.")
@RequestMapping("/NRDServices/TreeEnumerations")
public class TreeEnumerationsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeEnumerationsController.class);

    @Autowired
	@Qualifier("NRDServices.TreeEnumerationsService")
	private TreeEnumerationsService treeEnumerationsService;

	@ApiOperation(value = "Creates a new TreeEnumerations instance.")
	@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public TreeEnumerations createTreeEnumerations(@RequestBody TreeEnumerations treeEnumerations) {
		LOGGER.debug("Create TreeEnumerations with information: {}" , treeEnumerations);

		treeEnumerations = treeEnumerationsService.create(treeEnumerations);
		LOGGER.debug("Created TreeEnumerations with information: {}" , treeEnumerations);

	    return treeEnumerations;
	}


    @ApiOperation(value = "Returns the TreeEnumerations instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TreeEnumerations getTreeEnumerations(@PathVariable("id") String id) throws EntityNotFoundException {
        LOGGER.debug("Getting TreeEnumerations with id: {}" , id);

        TreeEnumerations foundTreeEnumerations = treeEnumerationsService.getById(id);
        LOGGER.debug("TreeEnumerations details with id: {}" , foundTreeEnumerations);

        return foundTreeEnumerations;
    }

    @ApiOperation(value = "Updates the TreeEnumerations instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TreeEnumerations editTreeEnumerations(@PathVariable("id") String id, @RequestBody TreeEnumerations treeEnumerations) throws EntityNotFoundException {
        LOGGER.debug("Editing TreeEnumerations with id: {}" , treeEnumerations.getTreeTag());

        treeEnumerations.setTreeTag(id);
        treeEnumerations = treeEnumerationsService.update(treeEnumerations);
        LOGGER.debug("TreeEnumerations details with id: {}" , treeEnumerations);

        return treeEnumerations;
    }

    @ApiOperation(value = "Deletes the TreeEnumerations instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTreeEnumerations(@PathVariable("id") String id) throws EntityNotFoundException {
        LOGGER.debug("Deleting TreeEnumerations with id: {}" , id);

        TreeEnumerations deletedTreeEnumerations = treeEnumerationsService.delete(id);

        return deletedTreeEnumerations != null;
    }

    /**
     * @deprecated Use {@link #findTreeEnumerations(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TreeEnumerations instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TreeEnumerations> searchTreeEnumerationsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TreeEnumerations list");
        return treeEnumerationsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TreeEnumerations instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TreeEnumerations> findTreeEnumerations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TreeEnumerations list");
        return treeEnumerationsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TreeEnumerations instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TreeEnumerations> filterTreeEnumerations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TreeEnumerations list");
        return treeEnumerationsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTreeEnumerations(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return treeEnumerationsService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of TreeEnumerations instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTreeEnumerations( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TreeEnumerations");
		return treeEnumerationsService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTreeEnumerationsAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return treeEnumerationsService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TreeEnumerationsService instance
	 */
	protected void setTreeEnumerationsService(TreeEnumerationsService service) {
		this.treeEnumerationsService = service;
	}

}

