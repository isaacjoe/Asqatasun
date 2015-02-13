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
package org.opens.tanaguru.rules.accessiweb21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.opens.tanaguru.entity.audit.ProcessRemark;
import org.opens.tanaguru.entity.audit.ProcessResult;
import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.processor.SSPHandler;
import org.opens.tanaguru.ruleimplementation.RuleHelper;
import org.opens.tanaguru.rules.accessiweb21.handler.link.AbstractPageRuleLinkThemeImplementation;
import org.opens.tanaguru.rules.accessiweb21.handler.link.LinkRulesHandler;

/**
 * This rule tests if the title attribute of a composite &lt;a&gt; tag
 * is pertinent. To do so, we check whether the content of the title attribute
 * is empty, is equal to the text content of the tag, and belongs to the text link
 * blacklist.
 * If one of these conditions is fulfilled, the test returns FAILED.
 * If the page doesn't contain any &lt;a&gt; tag, the test returns NOT_APPLICABLE.
 * In all the other cases, the test returns NEED_MORE_INFORMATIONS.
 * @author jkowalczyk
 */
public class Aw21Rule06024 extends AbstractPageRuleLinkThemeImplementation {

    // xpath to get unlimited descendant with not empty content
    private static final String XPATH_EXPR1 =
            "//A[@title and "+LinkRulesHandler.COMPOSITE_LINK_IMG_NODE+
            LinkRulesHandler.END_BRACKET;

    // xpath to get unlimited descendant with not empty content
    private static final String XPATH_EXPR2 =
            "//A[@title and "+LinkRulesHandler.COMPOSITE_LINK_OBJECT_NODE+
            LinkRulesHandler.END_BRACKET;

    public Aw21Rule06024() {
        super();
    }

   @Override
    protected ProcessResult processImpl(SSPHandler sspHandler) {
        super.processImpl(sspHandler);
        List<ProcessRemark> processRemarkList = new ArrayList<ProcessRemark>();
        Set<TestSolution> resultSet = new HashSet<TestSolution>();
        
        sspHandler.beginSelection().domXPathSelectNodeSet(XPATH_EXPR1);
        sspHandler.setSelectedElementList(linkRulesHandler.
                keepTagsWithContent(sspHandler.getSelectedElementList()));
        resultSet.add(linkRulesHandler.
                 checkAttributePertinence(
                     NodeAndAttributeKeyStore.TITLE_ATTR,
                     false));
        processRemarkList.addAll(sspHandler.getRemarkList());

        sspHandler.beginSelection().domXPathSelectNodeSet(XPATH_EXPR2);
        sspHandler.setSelectedElementList(
                linkRulesHandler.keepTagsWithContent(sspHandler.getSelectedElementList()));

        resultSet.add(linkRulesHandler.
                 checkAttributePertinence(
                     NodeAndAttributeKeyStore.TITLE_ATTR,
                     false));
        processRemarkList.addAll(sspHandler.getRemarkList());
        ProcessResult processResult = definiteResultFactory.create(
                test,
                sspHandler.getPage(),
                RuleHelper.synthesizeTestSolutionCollection(resultSet),
                processRemarkList);

        cleanUpRule();
        return processResult;
    }

}