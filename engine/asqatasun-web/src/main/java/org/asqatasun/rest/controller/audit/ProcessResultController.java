/*
 * Asqatasun - Automated webpage assessment
 * Copyright (C) 2008-2015  Asqatasun.org
 *
 * This file is part of Asqatasun.
 *
 * Asqatasun is free software: you can redistribute it and/or modify
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
 * Contact us by mail: asqatasun AT asqatasun DOT org
 */
package org.asqatasun.rest.controller.audit;

import org.asqatasun.entity.audit.ProcessResult;
import org.asqatasun.entity.reference.Scope;
import org.asqatasun.entity.service.audit.ProcessResultDataService;
import org.asqatasun.entity.service.parameterization.ParameterDataService;
import org.asqatasun.entity.service.reference.ScopeDataService;
import org.asqatasun.entity.service.subject.WebResourceDataService;
import org.asqatasun.rest.controller.EntityController;
import org.asqatasun.rest.dto.AuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by meskoj on 17/05/16.
 */
@RestController
@RequestMapping(value="/api")
public class ProcessResultController {

    @Autowired
    private ProcessResultDataService processResultDataService;

    @Autowired
    private WebResourceDataService webResourceDataService;

    @Autowired
    private ScopeDataService scopeDataService;

    private Scope pageScope;
    private Scope siteScope;

    @PostConstruct
    private void initScopes() {
        scopeDataService.findAll().forEach(s -> {
                if (s.getCode().equalsIgnoreCase("PAGE")) {
                pageScope = s;
            } else if (s.getCode().equalsIgnoreCase("SITE")) {
                siteScope = s;
            }
        });
    }

    @RequestMapping(
            value = "webresource/{webResourceId}/process_results",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessResult>> getAllByWebResource(@PathVariable Long webResourceId) {
        System.out.println(webResourceId);
        return Optional.ofNullable(webResourceDataService.read(webResourceId))
                .map(wr -> wr.getProcessResultList().stream()
                        .map(p -> {
                            p.setNetResultAudit(null);
                            p.setGrossResultAudit(null);
                            p.setSubject(null);
                            p.getTest().setCriterion(null);
                            p.getRemarkSet().stream()
                                    .map(processRemark -> {
                                        processRemark.setProcessResult(null);
                                        return processRemark;
                                    })
                                    .collect(Collectors.toList());
                            return p;
                        })
                        .sorted((processResult, t1) -> processResult.getTest().getCode().compareTo(t1.getTest().getCode()))
                        .collect(Collectors.toList()))
                .map(processResults -> new ResponseEntity<>(processResults, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
