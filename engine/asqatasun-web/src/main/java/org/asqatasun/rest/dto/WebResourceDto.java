package org.asqatasun.rest.dto;

import org.asqatasun.entity.audit.TestSolution;
import org.asqatasun.entity.statistics.WebResourceStatistics;
import org.asqatasun.entity.subject.Site;
import org.asqatasun.entity.subject.WebResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by meskoj on 20/12/16.
 */
public class WebResourceDto {

    private Long id;
    private String url;
    private Float rawMark;
    private Float mark;
    private int nbOfPages;
    private Map<TestSolution,Integer> repartitionBySolutionType;

    public WebResourceDto() {}

    public WebResourceDto(Long id,
                          String url,
                          Float mark,
                          Float rawMark,
                          int nbOfPages,
                          Map<TestSolution,Integer> repartitionBySolutionType) {
        this.id = id;
        this.url = url;
        this.mark = mark;
        this.rawMark = rawMark;
        this.nbOfPages = nbOfPages;
        this.repartitionBySolutionType = repartitionBySolutionType;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Float getMark() {
        return mark;
    }

    public Float geRawMark() {
        return rawMark;
    }

    public Map<TestSolution, Integer> getRepartitionBySolutionType() {
        return repartitionBySolutionType;
    }

    public static WebResourceDtoBuilder builder() {
        return new WebResourceDtoBuilder();
    }

    public static class WebResourceDtoBuilder {

        private Long id;
        private String url;
        private Float mark;
        private Float rawMark;
        private String type;
        private int nbOfPages;
        private Map<TestSolution,Integer> repartitionBySolutionType = new HashMap<>();

        private WebResourceDtoBuilder() {}

        public WebResourceDtoBuilder id (Long id) {
            this.id = id;
            return this;
        }

        public WebResourceDtoBuilder url (String url) {
            this.url = url;
            return this;
        }

        public WebResourceDtoBuilder type (WebResource webResource) {
            if (webResource instanceof Site) {
                type = "GROUP_OF_PAGE";
                nbOfPages = ((Site) webResource).getComponentList().size();
            } else {
                type = "PAGE";
                nbOfPages = 1;
            }
            return this;
        }

        public WebResourceDtoBuilder statistics (WebResourceStatistics statistics) {
            if (statistics != null) {
                this.rawMark = statistics.getRawMark();
                this.mark = statistics.getMark();
                this.repartitionBySolutionType.put(TestSolution.PASSED, statistics.getNbOfPassed());
                this.repartitionBySolutionType.put(TestSolution.FAILED, statistics.getNbOfFailed());
                this.repartitionBySolutionType.put(TestSolution.NEED_MORE_INFO, statistics.getNbOfNmi());
                this.repartitionBySolutionType.put(TestSolution.NOT_APPLICABLE, statistics.getNbOfNa());
                this.repartitionBySolutionType.put(TestSolution.NOT_TESTED, statistics.getNbOfNotTested());
            }
            return this;
        }

        public WebResourceDto build() {
            return new WebResourceDto(
                    id,
                    url,
                    mark,
                    rawMark,
                    nbOfPages,
                    repartitionBySolutionType);
        }
    }

}
