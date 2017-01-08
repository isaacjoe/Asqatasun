/*
 * Asqatasun - Automated webpage assessment
 * Copyright (C) 2008-2017  Asqatasun.org
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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.asqatasun.entity.audit.Audit;
import org.asqatasun.entity.audit.AuditStatus;
import org.asqatasun.entity.parameterization.Parameter;
import org.asqatasun.entity.service.audit.AuditDataService;
import org.asqatasun.entity.service.parameterization.ParameterDataService;
import org.asqatasun.entity.service.statistics.WebResourceStatisticsDataService;
import org.asqatasun.rest.dto.AuditDto;
import org.asqatasun.rest.request.PageAuditRequest;
import org.asqatasun.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by meskoj on 17/05/16.
 */
@RestController
@RequestMapping(value="/audits")
public class AuditController {

    @Autowired
    AuditService auditService;

    @Autowired
    AuditDataService auditDataService;

    @Autowired
    WebResourceStatisticsDataService webResourceStatisticsDataService;

    @Autowired
    ParameterDataService parameterDataService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditDto> getAll() {
        return auditDataService
                .findAll()
                .stream()
                .map(a -> AuditDto.builder()
                        .id(a.getId())
                        .status(a.getStatus())
                        .subject(a.getSubject(),
                                 webResourceStatisticsDataService.getWebResourceStatisticsByWebResource(a.getSubject()))
                        .build())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuditDto> get(@PathVariable Long id) {
        return Optional.ofNullable(auditDataService.read(id))
                .map(audit ->
                    new ResponseEntity<>(AuditDto.builder()
                            .id(audit.getId())
                            .status(audit.getStatus())
                            .subject(audit.getSubject(),
                                    webResourceStatisticsDataService.getWebResourceStatisticsByWebResource(audit.getSubject()))
                            .build(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "status/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuditStatus> getStatus(@PathVariable Long id) {
        return Optional.ofNullable(auditDataService.read((id)))
                .map(audit ->
                        new ResponseEntity<>(audit.getStatus(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/run", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long auditPage(@RequestBody final PageAuditRequest auditRequest) {
        return runAuditOnline(new String[]{auditRequest.url}, auditRequest.referential, auditRequest.level).getId();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/runS", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long auditScenario(@RequestBody final ScenarioAuditRequest auditRequest)  throws IOException {
        return runAuditScenario(auditRequest.scenarioContent, auditRequest.referential, auditRequest.level).getId();
    }

    private Audit runAuditOnline(String[] urlTab, String ref, String level) {
        Logger.getLogger(this.getClass()).info("runAuditOnline");

        Set<Parameter> paramSet = parameterDataService.getParameterSetFromAuditLevel(ref, level);

        List<String> pageUrlList = Arrays.asList(urlTab);

        if (pageUrlList.size() > 1) {
            return auditService.auditSite("site:" + pageUrlList.get(0), pageUrlList, paramSet);
        } else {
            return auditService.auditPage(pageUrlList.get(0), parameterDataService.getAuditPageParameterSet(paramSet));
        }
    }

    public Audit runAuditScenario(String scenarioFilePath, String ref, String level)  throws IOException {

        Set<Parameter> paramSet = parameterDataService.getParameterSetFromAuditLevel(ref, level);
        System.out.println(scenarioFilePath);
        File scenarioFile = new File(scenarioFilePath);
        return auditService.auditScenario(scenarioFile.getName(), readFile(scenarioFile), paramSet);
    }

    /**
     *
     * @return
     */
    private String readFile(File file) throws IOException {
        // #57 issue quick fix.......
        return FileUtils.readFileToString(file).replace("\"formatVersion\": 2", "\"formatVersion\":1")
                .replace("\"formatVersion\":2", "\"formatVersion\":1");
    }

    public class ScenarioAuditRequest {
        public String referential;
        public String level;
        public String scenarioContent;
    }
}
