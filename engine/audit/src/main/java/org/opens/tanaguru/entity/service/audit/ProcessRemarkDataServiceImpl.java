/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2015  Tanaguru.org
 *
 * This file is part of Tanaguru.
 *
 * Tanaguru is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */
package org.opens.tanaguru.entity.service.audit;

import java.util.Collection;
import org.opens.tanaguru.entity.audit.ProcessRemark;
import org.opens.tanaguru.entity.audit.ProcessResult;
import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.entity.dao.audit.ProcessRemarkDAO;
import org.opens.tanaguru.sdk.entity.service.AbstractGenericDataService;

/**
 * 
 * @author jkowalczyk
 */
public class ProcessRemarkDataServiceImpl extends AbstractGenericDataService<ProcessRemark, Long> implements
        ProcessRemarkDataService {

    @Override
    public Collection<ProcessRemark> findProcessRemarksFromProcessResult(
            ProcessResult processResult, 
            int limit) {
        return ((ProcessRemarkDAO) entityDao).
                retrieveProcessRemarksFromProcessResult(processResult, limit);
    }
    
    @Override
    public Collection<ProcessRemark> findProcessRemarksFromProcessResultAndTestSolution(
            ProcessResult processResult, 
            TestSolution testSolution, 
            int limit) {
        return ((ProcessRemarkDAO) entityDao).
                retrieveProcessRemarksFromProcessResultAndTestSolution(processResult, testSolution, limit);
    }

    @Override
    public int findNumberOfProcessRemarksFromProcessResultAndTestSolution(
            ProcessResult processResult, 
            TestSolution testSolution) {
        return ((ProcessRemarkDAO) entityDao).
                countProcessRemarksFromProcessResultAndTestSolution(processResult, testSolution);
    }
    
}
