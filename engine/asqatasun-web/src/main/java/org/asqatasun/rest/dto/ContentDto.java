package org.asqatasun.rest.dto;

import org.asqatasun.entity.audit.Audit;
import org.asqatasun.entity.audit.AuditStatus;
import org.asqatasun.entity.audit.Content;
import org.asqatasun.entity.subject.WebResource;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by meskoj on 20/12/16.
 */
public class ContentDto {

    private Long id;
    private String type;
    private String url;
    private int httpStatusCode;
//    private Collection<ContentDto> childContents;

    public ContentDto() {}

    public ContentDto(Long id,
                      int httpStatusCode,
                      String type,
//                      Collection<ContentDto> childContents,
                      String url) {
        this.id = id;
        this.type = type;
        this.url = url;
//        childContents = childContents;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

//    public Collection<ContentDto> getChildContents() {
//        return childContents;
//    }

    public static ContentDtoBuilder builder(Content content) {
        return
                new ContentDtoBuilder()
                        .id(content.getId())
                        .url(content.getURI())
                        .httpStatusCode(content.getHttpStatusCode())
                        .type(content);
    }

    public static ContentDtoBuilder builder() {
        return new ContentDtoBuilder();
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public static class ContentDtoBuilder {

        private Long id;
        private String type;
        private String url;
        private int httpStatusCode;
//        private List<ContentDto> childContents;

        private ContentDtoBuilder() {}

//        public ContentDtoBuilder childContents(List<Content> contents) {
//
//            this.subject =
//                    contents
//                            .stream()
//                            .map(c -> ContentDto.builder()
//                                    .id(c.getId())
//                                    .type(c))
//                            .collect(Collectors.toList());
//            return this;
//        }

        public ContentDtoBuilder type(Content content) {
            this.type = content.getClass().getName();
            return this;
        }

        public ContentDtoBuilder id (Long id) {
            this.id = id;
            return this;
        }

        public ContentDtoBuilder httpStatusCode (int httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public ContentDtoBuilder url (String uri) {
            this.url = uri.toString();
            return this;
        }

        public ContentDto build() {
            return new ContentDto(id, httpStatusCode, type, url);
        }
    }


}
