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

import com.nrd.services.Employee;
import com.nrd.services.TreeEnumerations;


/**
 * ServiceImpl object for domain model class Employee.
 *
 * @see Employee
 */
@Service("NRDServices.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
	@Qualifier("NRDServices.TreeEnumerationsService")
	private TreeEnumerationsService treeEnumerationsService;

    @Autowired
    @Qualifier("NRDServices.EmployeeDao")
    private WMGenericDao<Employee, String> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Employee, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "NRDServicesTransactionManager")
    @Override
	public Employee create(Employee employee) {
        LOGGER.debug("Creating a new Employee with information: {}", employee);
        Employee employeeCreated = this.wmGenericDao.create(employee);
        if(employeeCreated.getTreeEnumerationses() != null) {
            for(TreeEnumerations treeEnumerationse : employeeCreated.getTreeEnumerationses()) {
                treeEnumerationse.setEmployee(employeeCreated);
                LOGGER.debug("Creating a new child TreeEnumerations with information: {}", treeEnumerationse);
                treeEnumerationsService.create(treeEnumerationse);
            }
        }
        return employeeCreated;
    }

	@Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public Employee getById(String employeeId) throws EntityNotFoundException {
        LOGGER.debug("Finding Employee by id: {}", employeeId);
        Employee employee = this.wmGenericDao.findById(employeeId);
        if (employee == null){
            LOGGER.debug("No Employee found with id: {}", employeeId);
            throw new EntityNotFoundException(String.valueOf(employeeId));
        }
        return employee;
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public Employee findById(String employeeId) {
        LOGGER.debug("Finding Employee by id: {}", employeeId);
        return this.wmGenericDao.findById(employeeId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "NRDServicesTransactionManager")
	@Override
	public Employee update(Employee employee) throws EntityNotFoundException {
        LOGGER.debug("Updating Employee with information: {}", employee);
        this.wmGenericDao.update(employee);

        String employeeId = employee.getEmployeeName();

        return this.wmGenericDao.findById(employeeId);
    }

    @Transactional(value = "NRDServicesTransactionManager")
	@Override
	public Employee delete(String employeeId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Employee with id: {}", employeeId);
        Employee deleted = this.wmGenericDao.findById(employeeId);
        if (deleted == null) {
            LOGGER.debug("No Employee found with id: {}", employeeId);
            throw new EntityNotFoundException(String.valueOf(employeeId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "NRDServicesTransactionManager")
	@Override
	public Page<Employee> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Employees");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
    @Override
    public Page<Employee> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Employees");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service NRDServices for table Employee to {} format", exportType);
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

    @Transactional(readOnly = true, value = "NRDServicesTransactionManager")
    @Override
    public Page<TreeEnumerations> findAssociatedTreeEnumerationses(String employeeName, Pageable pageable) {
        LOGGER.debug("Fetching all associated treeEnumerationses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employee.employeeName = '" + employeeName + "'");

        return treeEnumerationsService.findAll(queryBuilder.toString(), pageable);
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

