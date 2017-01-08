package org.asqatasun.rest.dto;

import org.asqatasun.entity.audit.Audit;
import org.asqatasun.entity.audit.AuditStatus;
import org.asqatasun.entity.audit.Content;
import org.asqatasun.entity.statistics.WebResourceStatistics;
import org.asqatasun.entity.subject.WebResource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by meskoj on 20/12/16.
 */
public class AuditDto {

    private Long id;
    private WebResourceDto subject;
    private AuditStatus status;
    private String comment;
    public AuditDto() {}

    public AuditDto(Long id,
                    String comment,
                    WebResourceDto subject,
                    AuditStatus status) {
        this.id = id;
        this.comment = comment;
        this.subject = subject;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public WebResourceDto getSubject() {
        return subject;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public static AuditDtoBuilder builder(Audit audit) {
        return
                new AuditDtoBuilder()
                        .id(audit.getId())
                        .comment(audit.getComment());
    }

    public static AuditDtoBuilder builder() {
        return new AuditDtoBuilder();
    }

    public static class AuditDtoBuilder {

        private Long id;
        private String comment;
        private WebResourceDto subject;
        private AuditStatus status;

        private AuditDtoBuilder() {}

        public AuditDtoBuilder subject(WebResource subject, WebResourceStatistics statistics) {
            if (status == AuditStatus.COMPLETED) {
                this.subject =
                        WebResourceDto.builder()
                                .id(subject.getId())
                                .url(subject.getURL())
                                .statistics(statistics)
                                .type(subject)
                                .build();
            }
            return this;
        }

        public AuditDtoBuilder id (Long id) {
            this.id = id;
            return this;
        }

        public AuditDtoBuilder status (AuditStatus status) {
            this.status = status;
            return this;
        }

        public AuditDtoBuilder comment (String comment) {
            this.comment = comment;
            return this;
        }

        public AuditDto build() {
            return new AuditDto(id, comment, subject, status);
        }
    }

}
