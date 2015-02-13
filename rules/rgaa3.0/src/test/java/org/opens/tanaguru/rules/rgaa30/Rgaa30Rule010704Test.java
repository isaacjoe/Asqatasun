/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2015  Tanaguru.org
 *
 * This program is free software: you can redistribute it and/or modify
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
package org.opens.tanaguru.rules.rgaa30;

import java.util.Iterator;
import java.util.LinkedHashSet;
import org.opens.tanaguru.entity.audit.EvidenceElement;
import org.opens.tanaguru.entity.audit.ProcessResult;
import org.opens.tanaguru.entity.audit.SourceCodeRemark;
import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.rules.rgaa30.test.Rgaa30RuleImplementationTestCase;
import static org.opens.tanaguru.rules.keystore.AttributeStore.ALT_ATTR;
import static org.opens.tanaguru.rules.keystore.AttributeStore.CODE_ATTR;
import org.opens.tanaguru.rules.keystore.HtmlElementStore;
import org.opens.tanaguru.rules.keystore.RemarkMessageStore;

/**
 * Unit test class for the implementation of the rule 01.07.04 of the referential Rgaa 3.0.
 *
 * @author jkowalczyk
 */
public class Rgaa30Rule010704Test extends Rgaa30RuleImplementationTestCase {

    /**
     * Default constructor
     */
    public Rgaa30Rule010704Test (String testName){
        super(testName);
    }

    @Override
    protected void setUpRuleImplementationClassName() {
        setRuleImplementationClassName(
                "org.opens.tanaguru.rules.rgaa30.Rgaa30Rule010704");
    }

    @Override
    protected void setUpWebResourceMap() {
        getWebResourceMap().put("Rgaa30.Test.01.07.04-3NMI-01",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule010704/Rgaa30.Test.01.07.04-3NMI-01.html"));
        getWebResourceMap().put("Rgaa30.Test.01.07.04-4NA-01",
              getWebResourceFactory().createPage(
              getTestcasesFilePath() + "rgaa30/Rgaa30Rule010704/Rgaa30.Test.01.07.04-4NA-01.html"));
        getWebResourceMap().put("Rgaa30.Test.01.07.04-4NA-02",
              getWebResourceFactory().createPage(
              getTestcasesFilePath() + "rgaa30/Rgaa30Rule010704/Rgaa30.Test.01.07.04-4NA-02.html"));
        getWebResourceMap().put("Rgaa30.Test.01.07.04-4NA-03",
              getWebResourceFactory().createPage(
              getTestcasesFilePath() + "rgaa30/Rgaa30Rule010704/Rgaa30.Test.01.07.04-4NA-03.html"));
    }

    @Override
    protected void setProcess() {
        //----------------------------------------------------------------------
        //-------------------------------3NMI-01--------------------------------
        //----------------------------------------------------------------------
        ProcessResult processResult = processPageTest("Rgaa30.Test.01.07.04-3NMI-01");
        // check number of elements in the page
//        assertEquals(1, processResult.getElementCounter());
        // check test result
        assertEquals(TestSolution.NOT_TESTED, processResult.getValue());
        // check number of remarks and their value
//        assertEquals(1, processResult.getRemarkSet().size());
//        SourceCodeRemark processRemark = ((SourceCodeRemark)((LinkedHashSet)processResult.getRemarkSet()).iterator().next());
//        assertEquals(TestSolution.NEED_MORE_INFO, processRemark.getIssue());
//        assertEquals(RemarkMessageStore.CHECK_NATURE_OF_IMAGE_AND_DESC_PERTINENCE_MSG, processRemark.getMessageCode());
//        assertEquals(HtmlElementStore.APPLET_ELEMENT, processRemark.getTarget());
        // check number of evidence elements and their value
//        assertEquals(3, processRemark.getElementList().size());
//        Iterator<EvidenceElement> pIter = processRemark.getElementList().iterator();
//        EvidenceElement ee = pIter.next();
//        assertEquals("", ee.getValue());
//        assertEquals(ALT_ATTR, ee.getEvidence().getCode());
//        ee = pIter.next();
//        assertEquals("Java mock applet.", ee.getValue());
//        assertEquals(HtmlElementStore.TEXT_ELEMENT2, ee.getEvidence().getCode());
//        ee = pIter.next();
//        assertEquals("mock.class",ee.getValue());
//        assertEquals(CODE_ATTR, ee.getEvidence().getCode());

        
        //----------------------------------------------------------------------
        //------------------------------4NA-01------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.01.07.04-4NA-01");
        // check test result
        assertEquals(TestSolution.NOT_TESTED, processResult.getValue());
        // check test has no remark
        assertNull(processResult.getRemarkSet());
        // check number of elements in the page
        assertEquals(0, processResult.getElementCounter());
        

        //----------------------------------------------------------------------
        //------------------------------4NA-02---------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.01.07.04-4NA-02");
        // check test result
        assertEquals(TestSolution.NOT_TESTED, processResult.getValue());
        // check test has no remark
        assertNull(processResult.getRemarkSet());
        // check number of elements in the page
        assertEquals(0, processResult.getElementCounter());
        

        //----------------------------------------------------------------------
        //------------------------------4NA-03---------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.01.07.04-4NA-03");
        // check test result
        assertEquals(TestSolution.NOT_TESTED, processResult.getValue());
        // check test has no remark
        assertNull(processResult.getRemarkSet());
        // check number of elements in the page
        assertEquals(0, processResult.getElementCounter());
    }

    @Override
    protected void setConsolidate() {
        assertEquals(TestSolution.NOT_TESTED,
                consolidate("Rgaa30.Test.01.07.04-3NMI-01").getValue());
        assertEquals(TestSolution.NOT_TESTED,
                consolidate("Rgaa30.Test.01.07.04-4NA-01").getValue());
        assertEquals(TestSolution.NOT_TESTED,
                consolidate("Rgaa30.Test.01.07.04-4NA-02").getValue());
        assertEquals(TestSolution.NOT_TESTED,
                consolidate("Rgaa30.Test.01.07.04-4NA-03").getValue());
    }

}
