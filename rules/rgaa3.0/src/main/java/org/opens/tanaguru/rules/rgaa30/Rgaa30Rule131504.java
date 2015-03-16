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

import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.ruleimplementation.AbstractDetectionPageRuleImplementation;
import org.opens.tanaguru.rules.elementselector.ImageElementSelector;
import static org.opens.tanaguru.rules.keystore.AttributeStore.CODE_ATTR;
import static org.opens.tanaguru.rules.keystore.HtmlElementStore.APPLET_ELEMENT;
import static org.opens.tanaguru.rules.keystore.RemarkMessageStore.MANUAL_CHECK_ON_ELEMENTS_MSG;

/**
 * Implementation of the rule 13.15.4 of the referential Rgaa 3.0.
 * <br/>
 * For more details about the implementation, refer to <a href="https://github.com/Tanaguru/Tanaguru-rules-RGAA-3-doc/wiki/Rule-13-15-4">the rule 13.15.4 design page.</a>
 * @see <a href="https://references.modernisation.gouv.fr/sites/default/files/RGAA3/referentiel_technique.htm#test-13-15-4"> 13.15.4 rule specification</a>
 *
 * @author jkowalczyk
 */

public class Rgaa30Rule131504 extends AbstractDetectionPageRuleImplementation {

    /**
     * Default constructor
     */
    public Rgaa30Rule131504 () {
        super(
                new ImageElementSelector(APPLET_ELEMENT),
                // solution when at least one element is found
                TestSolution.NEED_MORE_INFO,
                // solution when no element is found
                TestSolution.NOT_APPLICABLE,
                // manual check message
                MANUAL_CHECK_ON_ELEMENTS_MSG,
                null,
                // evidence elements
                CODE_ATTR
            );
    }

}